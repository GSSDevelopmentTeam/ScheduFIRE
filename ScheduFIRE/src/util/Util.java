package util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import control.AutenticazioneException;
import control.NotEnoughMembersException;
import control.ScheduFIREException;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.SquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.*;


/**
 * La classe Util contiene diversi metodi statici utili per essere chiamati
 * da diverse classi del sistema. 
 * @author Emanuele Bombardelli
 * @author Alfredo Giuliano
 *
 */
public class Util {

	/**
	 * Il metodo genera le squadre alla data passata come parametro
	 * @param data la data in cui generare le squadre
	 * @return la lista di ComponenteDellaSquadraBean che formano le squadre
	 * @throws NotEnoughMembersException se il personale è insufficiente
	 */
	public static List<ComponenteDellaSquadraBean> generaSquadra(Date data) throws NotEnoughMembersException {
		//Prendiamo i vigili disponibili
		List<VigileDelFuocoBean> disponibili = VigileDelFuocoDao.getDisponibili(data);
		//Li dividiamo in 3 liste
		List<VigileDelFuocoBean> caposquadra = new ArrayList<>();
		List<VigileDelFuocoBean> autista = new ArrayList<>();
		List<VigileDelFuocoBean> vigile = new ArrayList<>();

		for(VigileDelFuocoBean membro : disponibili) {
			if(membro.getMansione().toLowerCase().equals("capo squadra")) {
				caposquadra.add(membro);
			}
			else if(membro.getMansione().toLowerCase().equals("autista")) {
				autista.add(membro);
			}
			else if(membro.getMansione().toLowerCase().equals("vigile")) {
				vigile.add(membro);
			}
		}
		//Controlliamo se abbiamo abbastanza personale per fare squadra, altrimenti lanciamo l'eccezione
		if(abbastanzaPerTurno(caposquadra.size(), autista.size(), vigile.size())) {
			//Ordiniamo in ordine ascendente
			caposquadra.sort((VigileDelFuocoBean cs1, VigileDelFuocoBean cs2) -> 
			cs1.getCaricoLavoro() - cs2.getCaricoLavoro());
			autista.sort((VigileDelFuocoBean a1, VigileDelFuocoBean a2) ->
			a1.getCaricoLavoro() - a2.getCaricoLavoro());
			vigile.sort((VigileDelFuocoBean v1, VigileDelFuocoBean v2) ->
			v1.getCaricoLavoro() - v2.getCaricoLavoro());
			//Assegnamo in ordine decrescente
			List<ComponenteDellaSquadraBean> squadra = assegnaMansioni(caposquadra, autista, vigile, data);
			return squadra;
		}
		else {
			throw new NotEnoughMembersException();
		}
	}

	/**
	 * Il metodo conta il personale disponibile in caserma per vedere se � possibile creare un turno con 
	 * le persone considerate. Il numero di persone disponibili minime � considerato come un vettore
	 * (N. Capo Squadra, N. Autisti, N. Vigili del Fuoco) con due diverse configurazioni: (2, 3, 7), 
	 * (3, 3, 6) oppure (4, 3, 5).
	 * @param numCS il numero di Capo Squadra
	 * @param numAut il numero di Autisti
	 * @param numVF il numero di Vigili del Fuoco
	 * @return TRUE se � possibile creare un turno con i disponibili, FALSE altrimenti
	 */
	public static boolean abbastanzaPerTurno(int numCS, int numAut, int numVF) {
		if(numAut < 3) {
			return false;
		}
		else if((numCS >= 2) && (numCS <=4) && (numCS + numVF >= 9)) {
			return true;
		}
		else if((numCS < 2) || (numVF < 5) || ((numCS > 4) && (numVF < 5))) {
			return false;
		}
		else return true;
	}

	private static List<ComponenteDellaSquadraBean> assegnaMansioni(List<VigileDelFuocoBean> caposquadra,
			List<VigileDelFuocoBean> autista, List<VigileDelFuocoBean> vigile, Date data) {
		List<ComponenteDellaSquadraBean> toReturn = new ArrayList<>();
		SquadraBean salaOp = new SquadraBean("Sala Operativa", 3, data);
		SquadraBean primaP = new SquadraBean("Prima Partenza", 3, data);
		SquadraBean autoSc = new SquadraBean("Auto Scala", 2, data);
		SquadraBean autoBo = new SquadraBean("Auto Botte", 1, data);
		boolean vigileAutoSc=false;
		boolean vigileAutoBo=false;

		//Aggiungo gli autisti
		toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), autista.get(0).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), autista.get(1).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), autista.get(2).getEmail(), data));

		//Aggiungo i vigili
		toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), vigile.get(0).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), vigile.get(1).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), vigile.get(2).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), vigile.get(3).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), vigile.get(4).getEmail(), data));
		if(vigile.size()>5) {
			toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), vigile.get(5).getEmail(), data));
			vigileAutoSc=true;
		}
		if(vigile.size()>6) {
			toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), vigile.get(6).getEmail(), data));
			vigileAutoBo=true;
		}

		//Aggiungo i caposquadra
		toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), caposquadra.get(0).getEmail(), data));
		toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), caposquadra.get(1).getEmail(), data));
		if(!vigileAutoSc) {
			toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), caposquadra.get(2).getEmail(), data));
			caposquadra.remove(2);
		}
		if(!vigileAutoBo) {
			toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), caposquadra.get(2).getEmail(), data));
		}

		return toReturn;
	}
	
	
	/**
	 * Il metodo sostituisce il vigile che si trova in una squadra alla data passata come parametro
	 * @param data la data in cui sostituire il vigile
	 * @param mailVFDaSostituire la mail del vigile da sostituire
	 * @throws ScheduFIREException in caso ci siano problemi a rimuovere il vigile viene tirata un eccezione
	 */
	public static void sostituisciVigile(Date data, String mailVFDaSostituire) throws ScheduFIREException {
		//Prendo componenti e mappa delle squadre per la data in questione e li rimuovo 
		//dal DB prima di modificarli
		String squadraVF = ComponenteDellaSquadraDao.getSquadra(mailVFDaSostituire, data);
		List<ComponenteDellaSquadraBean> lista = ComponenteDellaSquadraDao.getComponenti(data);
		HashMap<VigileDelFuocoBean, String> squadra = ottieniSquadra(data);

		//Prendo la lista dei disponibili del giorno
		List<VigileDelFuocoBean> disponibili = VigileDelFuocoDao.getDisponibili(data);

		if(!ComponenteDellaSquadraDao.removeComponenti(lista) || 

				!VigileDelFuocoDao.removeCaricoLavorativo(squadra)) {
			throw new ScheduFIREException("Errore nelle query di sostituzione ferie");
		}

		//Rimuovo da lista, disponibili e squadra il VF da sostituire
		for(ComponenteDellaSquadraBean rimuovere : lista) {
			if(rimuovere.getEmailVF().equals(mailVFDaSostituire)) {
				lista.remove(rimuovere);
				break;
			}
		}

		for(VigileDelFuocoBean rimuovere : disponibili) {
			if(rimuovere.getEmail().equals(mailVFDaSostituire)) {
				disponibili.remove(rimuovere);
				break;
			}
		}

		Iterator i = squadra.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<VigileDelFuocoBean, String> coppia = (Map.Entry<VigileDelFuocoBean, String>) i.next();
			if(coppia.getKey().getEmail().equals(mailVFDaSostituire)) {
				i.remove();
				break;
			}
		}

		//Ordino i disponibili per carico lavorativo
		Collections.sort(disponibili, (VigileDelFuocoBean v1, VigileDelFuocoBean v2) -> 
		(v1.getCaricoLavoro() - v2.getCaricoLavoro()));

		//Aggiungo il sostituto a lista e squadra se gi� non ne fa parte e se la sua mansione � la stessa di colui da sostituire
		for(VigileDelFuocoBean sostituto : disponibili) {
			if(sostituto.getMansione().equals(VigileDelFuocoDao.ottieni(mailVFDaSostituire).getMansione()) &&
					!lista.contains(new ComponenteDellaSquadraBean(squadraVF, sostituto.getEmail(), data))) {
				lista.add(new ComponenteDellaSquadraBean(squadraVF, sostituto.getEmail(), data));

				squadra.put(sostituto, squadraVF);
				break;
			}
		}


		//Salvo nel DB i cambiamenti effettuati
		if(!ComponenteDellaSquadraDao.setComponenti(lista) ||
				!VigileDelFuocoDao.caricoLavorativo(squadra)) {
			throw new ScheduFIREException("Errore nelle query di sostituzione ferie");
		}

	}

	/**
	 * Il metodo restituisce la squadra alla data passata come parametro
	 * @param data la data di cui si vuole ottenere le squadre
	 * @return un HashMap di squadre 
	 */
	public static HashMap<VigileDelFuocoBean, String> ottieniSquadra(Date data) {
		List<ComponenteDellaSquadraBean> lista = ComponenteDellaSquadraDao.getComponenti(data);
		HashMap<VigileDelFuocoBean, String>  squadra = new HashMap<>();
		for(ComponenteDellaSquadraBean membro : lista) {
			squadra.put(VigileDelFuocoDao.ottieni(membro.getEmailVF()), membro.getTipologiaSquadra());
		}
		return squadra;
	}


	/**
	 * @param componenti Una lista di ComponentiDellaSquadra disordinata
	 * @return Un arrayList di ComponentiDellaSquadra ordinati per squadra e per cognome, con priorit� alla squadra.
	 */
	public static ArrayList<ComponenteDellaSquadraBean> ordinaComponenti(ArrayList<ComponenteDellaSquadraBean> componenti){
		Collections.sort(componenti, new ComponenteComparator());
		return componenti;
	}

	/**
	 * Il metodo controlla se l utente in sessione è loggato
	 * @param request la richiesta passata dalla servlet
	 * @throws AutenticazioneException se l utente non è autenticato 
	 */
	public static void isAutenticato(HttpServletRequest request) throws AutenticazioneException {
		if(request.getSession(false)==null) {
			request.getSession().invalidate();
			throw new AutenticazioneException("Richiesta l'autenticazione per poter accedere alle funzionalit&agrave; del sito.");

		}
		if(request.getSession().getAttribute("ruolo")==null)
			throw new AutenticazioneException("&Egrave; richiesta l'autenticazione per poter accedere alle funzionalit&agrave; del sito.");
		
	}

	/**
	 * Il metodo controlla se l utente in sessione è loggato ed è capoturno
	 * @param request la richiesta passata dalla servlet
	 * @throws AutenticazioneException se l utente non è capoturno 
	 */
	public static void isCapoTurno(HttpServletRequest request) throws AutenticazioneException {
		if(request.getSession(false)==null) {
			request.getSession().invalidate();
			throw new AutenticazioneException("Richiesta l'autenticazione per poter accedere alle funzionalit&agrave; del sito.");

		}
		if(request.getSession().getAttribute("ruolo")==null) {
			request.getSession().invalidate();
			throw new AutenticazioneException("Richiesta l'autenticazione per poter accedere alle funzionalit&agrave; del sito.");

		}
		String ruolo=(String)request.getSession().getAttribute("ruolo");
		if(!ruolo.equals("capoturno")) {
			request.getSession().invalidate();
			throw new AutenticazioneException("Devi essere capoturno per poter accedere a questa funzionalit&agrave;");
		}
		if(request.getSession().getAttribute("notifiche")==null) {
			request.getSession().invalidate();
			throw new AutenticazioneException("Richiesta l'autenticazione per poter accedere alle funzionalit&agrave; del sito.");
		
		}

	}




	/**
	 * Verifica se una delle due date passate è il primo giorno lavorativo dell'anno, se lo è cancella dal database
	 * il calendario dell'anno precedente e aggiorna le ferie ai vigili 
	 * @param diurnoDate il giorno lavorativo diurno
	 * @param notturnoDate il giorno lavorativo notturno
	 */
	public static void aggiornaDB(Date diurnoDate,Date notturnoDate) {
		LocalDate diurno=diurnoDate.toLocalDate();
		LocalDate notturno=notturnoDate.toLocalDate();
		boolean modifica=false;
		int annoNuovo=0;
		LocalDate precLavorativo=GiornoLavorativo.precLavorativo(diurnoDate).toLocalDate();

		//Se l'anno del diurno è diverso da quello del notturno, sto cambiando anno
		if(diurno.getYear()!=notturno.getYear()) {
			modifica=true;
			annoNuovo=notturno.getYear();
		}

		//Se il diurno ha un anno diverso dal precedente giorno lavorativo, sto in un nuovo anno
		else if(diurno.getYear()!= precLavorativo.getYear()) {
			modifica=true;	
			annoNuovo=diurno.getYear();
		}


		if(modifica==true) {
			setFerie();
			Date inizioAnnoNuovo=Date.valueOf(LocalDate.of(annoNuovo-1, 1, 1));
			ComponenteDellaSquadraDao.rimuoviTutti(inizioAnnoNuovo);
			SquadraDao.rimuoviTutti(inizioAnnoNuovo);
			ListaSquadreDao.rimuoviTutte(inizioAnnoNuovo);
		}

	}

	/**
	 * Prende una lista di vigili e la riordina per mansione
	 * @param lista	la lista di vigili da riordinare
	 * @return la lista di vigili ordinata per mansione
	 */
	public static List<VigileDelFuocoBean> compareVigile(List<VigileDelFuocoBean> lista) {

		List<VigileDelFuocoBean> listaC = new ArrayList<VigileDelFuocoBean>(lista);

		Collections.sort(listaC, new VigileComparator());

		return listaC;
	}


	private static void setFerie() {
		List<VigileDelFuocoBean> vigili=VigileDelFuocoDao.ottieni();
		for(VigileDelFuocoBean vigile:vigili) {
			int ferieAnnoPrecedente=vigile.getGiorniFerieAnnoPrecedente();
			int ferieAnnoCorrente=vigile.getGiorniFerieAnnoCorrente();
			ferieAnnoPrecedente+=ferieAnnoCorrente;
			VigileDelFuocoDao.aggiornaFeriePrecedenti(vigile.getEmail(), ferieAnnoPrecedente);
			VigileDelFuocoDao.aggiornaFerieCorrenti(vigile.getEmail(), 22);
		}
	}

}	





class ComponenteComparator implements Comparator<ComponenteDellaSquadraBean> {

	/*
	 * Per ordinare l'array di componenti della squadra in base alla tipologia della squadra di appartenenza
	 * con priorità a sala operativa, poi prima partenza, poi auto scala e infine auto botte.
	 * In caso di tipologia uguale, ordina in base al cognome che ricava dalla mail
	 * essendo la mail composta sempre da nome<numero>.cognome
	 * 
	 */
	@Override
	public int compare(ComponenteDellaSquadraBean o1, ComponenteDellaSquadraBean o2) {
		String tipologia1=o1.getTipologiaSquadra();
		String tipologia2=o2.getTipologiaSquadra();
		int comparazione=tipologia1.compareTo(tipologia2);
		if (comparazione==0) {
			String cognome1=o1.getEmailVF().substring(o1.getEmailVF().indexOf(".")+1);
			String cognome2=o2.getEmailVF().substring(o2.getEmailVF().indexOf(".")+1);
			comparazione=cognome1.compareTo(cognome2);
			return comparazione;
		}
		return -comparazione;
	}
}

class VigileComparator implements Comparator<VigileDelFuocoBean> {

	@Override
	public int compare(VigileDelFuocoBean o1, VigileDelFuocoBean o2) {
		String mansione1=o1.getMansione();
		String mansione2=o2.getMansione();
		if (mansione1.equals("Capo Squadra") && mansione2.equals("Capo Squadra"))
			return o1.getCognome().compareTo(o2.getCognome());
		if(mansione1.equals("Capo Squadra"))
			return -1;
		if(mansione2.equals("Capo Squadra"))
			return 1;
		return o1.getMansione().compareTo(o2.getMansione());
	}
}

