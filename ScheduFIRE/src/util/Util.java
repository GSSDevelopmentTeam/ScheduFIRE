package util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
 *
 */
public class Util {

	/**
	 * Il metodo codifica la stringa passata come parametro in Base64.
	 * @param pwd la stringa da codificare
	 * @return la stringa codificata
	 * @see Base64.Encoder
	 */
	public static String codificaPwd(String pwd){

		String pwdCodificata = Base64.getEncoder().encodeToString(pwd.getBytes());
		return pwdCodificata;
	} 

	/**
	 * Il metodo decodifica una stringa codificata in Base64.
	 * @param pwd_Cod la stringa da decodificare
	 * @return la stringa decidificata
	 * @see Base64.Decoder
	 */
	public static String decodificaPwd(String pwd_Cod){

		byte[] ByteDecodificati = Base64.getDecoder().decode(pwd_Cod);
		String pwdDecodificata = new String(ByteDecodificati);
		return pwdDecodificata;
	} 

	public static List<ComponenteDellaSquadraBean> generaSquadra(Date data) throws NotEnoughMembersException {
		//Prendiamo i vigili disponibili
		List<VigileDelFuocoBean> disponibili = VigileDelFuocoDao.getDisponibili(data);
		//Li dividiamo in 3 liste
		List<VigileDelFuocoBean> caposquadra = new ArrayList<>();
		List<VigileDelFuocoBean> autista = new ArrayList<>();
		List<VigileDelFuocoBean> vigile = new ArrayList<>();

		for(VigileDelFuocoBean membro : disponibili) {
			if(membro.getMansione().toLowerCase() == "capo squadra") {
				caposquadra.add(membro);
			}
			else if(membro.getMansione().toLowerCase() == "autista") {
				autista.add(membro);
			}
			else if(membro.getMansione().toLowerCase() == "vigile") {
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
		SquadraBean autoSc = new SquadraBean("Autoscala", 2, data);
		SquadraBean autoBo = new SquadraBean("Autobotte", 1, data);
		int contaSala = 0;
		int contaPrim = 0;
		int contaAutS = 0;
		for(VigileDelFuocoBean vf : vigile) {
			if(contaSala <= 2) {
				toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), vf.getEmail(), data));
				contaSala++;
			}
			else if(contaPrim <= 3) {
				toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), vf.getEmail(), data));
				contaPrim++;
			}
			else if(contaAutS <= 1) {
				toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), vf.getEmail(), data));
				contaAutS++;
			}
			else {
				toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), vf.getEmail(), data));
			}
		}

		for(VigileDelFuocoBean cs : caposquadra) {
			if(contaSala <= 3) {
				toReturn.add(new ComponenteDellaSquadraBean(salaOp.getTipologia(), cs.getEmail(), data));
				contaSala++;
			}
			else if(contaPrim <= 4) {
				toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), cs.getEmail(), data));
				contaPrim++;
			}
			else if(contaAutS <= 2) {
				toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), cs.getEmail(), data));
				contaAutS++;
			}
			else {
				toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), cs.getEmail(), data));
				break;
			}
		}

		int i = 0;
		for(VigileDelFuocoBean au : autista) {
			if(i == 0) {
				toReturn.add(new ComponenteDellaSquadraBean(primaP.getTipologia(), au.getEmail(), data));
				i++;
			}
			else if(i == 1) {
				toReturn.add(new ComponenteDellaSquadraBean(autoSc.getTipologia(), au.getEmail(), data));
				i++;
			}
			else {
				toReturn.add(new ComponenteDellaSquadraBean(autoBo.getTipologia(), au.getEmail(), data));
			}
		}

		return toReturn;
	}

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


	public static void isAutenticato(HttpServletRequest request) throws ScheduFIREException {
		if(request.getSession().getAttribute("ruolo")==null)
			throw new ScheduFIREException("� richiesta l'autenticazione per poter accedere alle funzionalit� del sito");

	}

	public static void isCapoTurno(HttpServletRequest request) throws ScheduFIREException {
		if(request.getSession().getAttribute("ruolo")==null)
			throw new ScheduFIREException("� richiesta l'autenticazione per poter accedere alle funzionalit� del sito");
		else {
			String ruolo=(String)request.getSession().getAttribute("ruolo");
			if(!ruolo.equals("capoturno"))
				throw new ScheduFIREException("Devi essere capoturno per poter accedere a questa funzionalit�");
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

