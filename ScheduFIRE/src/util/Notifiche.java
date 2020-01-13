package util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import control.ScheduFIREException;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;

/**
 * La classe si occupa della generazione, della gestione e dell'aggiornamento delle
 * notifiche del sistema. 
 * @author Emanuele Bombardelli
 *
 */
public class Notifiche {
	private static List<Notifica> listaNotifiche;
	private static int id;

	public Notifiche() {
		id = 0;
		listaNotifiche = new ArrayList<>();
		update(UPDATE_PER_AVVIO);
	}

	private static int generateId() {
		id++;
		return id;
	}

	/**
	 * Esegue l'update delle notifiche nel sistema. 
	 * @param cause Causa dell'update (usare i Notifiche.UPDATE_PER_AVVIO)
	 */
	public static void update(int cause) {
		if(cause == 1) {
			firstUpdate();
		}
		Collections.sort(listaNotifiche, (Notifica n1, Notifica n2) -> 
		(n2.getSeverita() - n1.getSeverita()));
	}

	/**
	 * Esegue l'update delle notifiche del sistema.
	 * @param cause La causa dell'update (usare Notifiche.UPDATE_PER_FERIE o Notifiche.UPDATE_PER_MALATTIA)
	 * @param from L'inizio del periodo modificato
	 * @param to La fine del periodo modificato
	 * @param mail La mail del Vigile alle quali modifiche fanno riferimento
	 */
	public static void update(int cause, Date from, Date to, String mail) {
		VigileDelFuocoBean vigile = VigileDelFuocoDao.ottieni(mail);
		if(cause == 2) {
			updateFerie(from, to, vigile);
		}
		else if(cause == 3) {
			updateMalattia(from, to, vigile);
		}
		else if(cause == 4) {
			updateSquadrePerFerie(from, to, vigile);
		}
		else if(cause == 5) {
			updateSquadrePerMalattia(from, to, mail);
		}


		Collections.sort(listaNotifiche, (Notifica n1, Notifica n2) ->(n2.getSeverita() - n1.getSeverita()));
	}


	private static  void updateMalattia(Date temp, Date to, VigileDelFuocoBean vigile) {
		List<List<VigileDelFuocoBean>> disponibili = new ArrayList<>();
		Date from = (Date) temp.clone();
		while(!from.equals(to)) {
			if(GiornoLavorativo.isLavorativo(from)) {
				disponibili.add(VigileDelFuocoDao.getDisponibili(to));
			}
			from = Date.valueOf(from.toLocalDate().plusDays(1L));
		}

		for(List<VigileDelFuocoBean> disponibiliAlGiorno : disponibili) {
			if(!conta(disponibiliAlGiorno)) {
				listaNotifiche.add(new Notifica(2, "La malattia concesse a " + vigile.getCognome() + " " +
						vigile.getNome() + " non rendono possibile la creazione di un turno", "/PeriodiDiMalattiaServlet",generateId()));
				break;
			}
		}

		if(controllaRestanti(vigile)) {
			listaNotifiche.add(new Notifica(1, "" + vigile.getCognome() + " " +
					vigile.getNome() + " ha terminato le ferie a lui disponibili", "",generateId()));
		}
	}
	
	private static void updateFerie(Date temp, Date to, VigileDelFuocoBean vigile) {
		List<List<VigileDelFuocoBean>> disponibili = new ArrayList<>();
		Date from = (Date) temp.clone();
		while(!from.equals(to)) {
			if(GiornoLavorativo.isLavorativo(from)) {
				disponibili.add(VigileDelFuocoDao.getDisponibili(to));
			}
			from = Date.valueOf(from.toLocalDate().plusDays(1L));
		}

		for(List<VigileDelFuocoBean> disponibiliAlGiorno : disponibili) {
			if(!conta(disponibiliAlGiorno)) {
				listaNotifiche.add(new Notifica(2, "Le ferie concesse a " + vigile.getCognome() + " " +
						vigile.getNome() + " non rendono possibile la creazione di un turno", "/GestioneFerieServlet",generateId()));
				break;
			}
		}

		if(controllaRestanti(vigile)) {
			listaNotifiche.add(new Notifica(1, "" + vigile.getCognome() + " " +
					vigile.getNome() + " ha terminato le ferie a lui disponibili", "",generateId()));
		}

	}
	
	private static void updateSquadrePerFerie(Date temp, Date to, VigileDelFuocoBean vigile) {
		Date inizio = (Date) temp.clone();
		Date fine = (Date) to.clone();
		LocalDate fineP = fine.toLocalDate();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<String> dateAssenza = new ArrayList<String>();
		
		String notifica = vigile.getCognome() + " " + vigile.getNome() + 
				" " + "non sara' presente nella squadra a cui e' stato assegnato\n";
		
		while(!inizio.equals(Date.valueOf(fineP.plusDays(1L)))) {
			if(GiornoLavorativo.isLavorativo(inizio)) {
				if(ComponenteDellaSquadraDao.isComponente(vigile.getEmail(), inizio)) {
					dateAssenza.add(formatter.format(inizio).toString());
				}
			}
			LocalDate next = inizio.toLocalDate().plusDays(1L);
			inizio = Date.valueOf(next);
		}
		
		if(dateAssenza.size() == 1)
			notifica += " per il giorno " + dateAssenza.get(0) + " causa ferie. E' stato" + 
						" automaticamente sostituito.";
		else
			notifica += " per il periodo dal " + dateAssenza.get(0) + " al " +
						dateAssenza.get(dateAssenza.size() - 1) + " causa ferie. E' stato" + 
								" automaticamente sostituito.";
		String giornoString=dateAssenza.get(0).substring(0,2);
		listaNotifiche.add(new Notifica(3, notifica, "CalendarioServlet?giorno="+giornoString ,generateId()));
	}
	
	private static void updateSquadrePerMalattia(Date inizio, Date fine, String email) {
		Date inizioMalattia = (Date) inizio.clone();
		Date fineMalattia = (Date) fine.clone();
		int schedu = 0;
		
		VigileDelFuocoBean vigile = VigileDelFuocoDao.ottieni(email);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		ArrayList<String> giorniMalattiaSchedulato = new ArrayList<String>();
		
			String notificaMalattia = "Il vigile "+vigile.getCognome()+" "+vigile.getNome()+

			" " + "non sara' presente nella squadra a cui e' stato assegnato\n";

			
			LocalDate inMalattia = inizioMalattia.toLocalDate();
			LocalDate outMalattia = fineMalattia.toLocalDate();
			
			while(!inizioMalattia.equals(Date.valueOf((outMalattia.plusDays(1L))))) {
				if(GiornoLavorativo.isLavorativo(inizioMalattia)) {
					if(ComponenteDellaSquadraDao.isComponente(vigile.getEmail(), inizioMalattia)) {
					giorniMalattiaSchedulato.add(formatter.format(inizioMalattia));
					}
				}
				LocalDate next = inizioMalattia.toLocalDate().plusDays(1L);
				inizioMalattia = Date.valueOf(next);
			}
			
			
			if(giorniMalattiaSchedulato.size() == 1)
				notificaMalattia += " per il giorno " + giorniMalattiaSchedulato.get(0) + " causa malattia. E' stato" + 
						" automaticamente sostituito.";
			else
				notificaMalattia += " per il periodo dal " + giorniMalattiaSchedulato.get(0) + " al " +
						giorniMalattiaSchedulato.get(giorniMalattiaSchedulato.size() - 1) + " causa malattia. E' stato" + 
						" automaticamente sostituito.";
			//"GeneraSquadreServlet""ModificaComposizioneSquadreServlet?tipologia=3&data="+inizio
			String giornoString=giorniMalattiaSchedulato.get(0).substring(0,2);
			listaNotifiche.add(new Notifica(3, notificaMalattia, "CalendarioServlet?giorno="+giornoString,generateId()));
		}
	
	/**
	 * Rimuove la notifica passata come parametro dalla lista di notifiche
	 * @param toRemove la notifica da rimuovere
	 */
	public void rimuovi(Notifica toRemove) {
		listaNotifiche.remove(toRemove);
	}

	private static boolean controllaRestanti(VigileDelFuocoBean vigile) {
		return (vigile.getGiorniFerieAnnoCorrente() == 0);	
	}

	private static void firstUpdate() {
		List<VigileDelFuocoBean> disponibili = VigileDelFuocoDao.getDisponibili
				(GiornoLavorativo.nextLavorativo(new Date(System.currentTimeMillis())));
		if(!conta(disponibili)) {
			Date data = new Date(System.currentTimeMillis());
			listaNotifiche.add(new Notifica(3, "Il personale disponibile il " + 
					data.toString() + " non e' sufficiente per creare il turno.", "/GestionePersonaleServlet",generateId()));
		}
	}

	private static boolean conta(List<VigileDelFuocoBean> disponibili) {
		int contaCS = 0, contaAU = 0, contaVF = 0;

		for(VigileDelFuocoBean vigile : disponibili) {
			switch(vigile.getMansione()) {
			case "Vigile":
				contaVF++;
				break;
			case "Capo Squadra":
				contaCS++;
				break;
			case "Autista":
				contaAU++;
				break;
			}
		}

		if(contaAU < 3) {
			return false;
		}
		else if((contaCS >= 2) && (contaCS <=4) && (contaCS + contaVF >= 9)) {
			return true;
		}
		else if((contaCS < 2) || (contaVF < 5) || ((contaCS > 4) && (contaVF < 5))) {
			return false;
		}
		else return true;
	}
	
	/**
	 * Restituisce la lista di notifiche
	 * @return la lista di notifiche
	 */
	public List<Notifica> getListaNotifiche() {
		return listaNotifiche;
	}
	
	/**
	 * Aggiunge una notifica nella lista di notifiche
	 * @param notifica la notifica da aggiungere
	 */
	public void addNotifica(Notifica notifica) {
		listaNotifiche.add(notifica);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		else {
			Notifiche otherNotifiche = (Notifiche) obj;
			return listaNotifiche.equals(otherNotifiche.getListaNotifiche());
		}
	}

	/**
	 * Utilizzare solo nella fase di avvio
	 */
	public static final int UPDATE_PER_AVVIO = 1;

	/**
	 * Utilizzare quando viene modificato un periodo di ferie
	 */
	public static final int UPDATE_PER_FERIE = 2;

	/**
	 * Utilizzare quando viene modificato un periodo di malattia
	 */
	public static final int UPDATE_PER_MALATTIA = 3;
	
	/**
	 * Utilizzare quando vengono concesse ferie ad un vigile giÃ  schedulato
	 */
	public static final int UPDATE_SQUADRE_PER_FERIE = 4;
	/**
	 * Utilizzare quando vengono concesse malattie ad un vigile giÃ  schedulato
	 */
	public static final int UPDATE_SQUADRE_PER_MALATTIA = 5;
}
