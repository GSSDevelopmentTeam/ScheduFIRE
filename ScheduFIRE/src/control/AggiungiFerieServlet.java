package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import model.bean.CapoTurnoBean;
import model.bean.FerieBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Notifiche;
import util.Util;

/**
 * Servlet per la concessione e salvataggio delle ferie di un Vigile del Fuoco
 * @author Nicola Labanca 
 * @author Alfredo Giuliano
 */

@WebServlet("/AggiungiFerieServlet")
public class AggiungiFerieServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AggiungiFerieServlet() {
		super();
	} 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	} 

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		//Istanziazione ed inizializzazione variabili
		Date dataInizio = null;
		Date dataFine = null;
		LocalDate inizio;
		LocalDate fine;
		String emailVF;
		boolean aggiunta = false;
		int numeroGiorniFerie=0;
		boolean componente = false;

		//Ottenimento oggetto capoturnoBean dalla sessione in modo da ricavare l'email
		HttpSession sessione = request.getSession();
		CapoTurnoBean capoTurno = (CapoTurnoBean) sessione.getAttribute("capoturno");;
		String emailCT = capoTurno.getEmail();

		emailVF = request.getParameter("email");

		//Prelevamento data di inizio e di fine del periodo di ferie
		String dataIniziale = request.getParameter("dataIniziale");
		String dataFinale = request.getParameter("dataFinale");

		int annoIniziale = Integer.parseInt(dataIniziale.substring(6, 10));
		int meseIniziale = Integer.parseInt(dataIniziale.substring(3, 5));
		int giornoIniziale = Integer.parseInt(dataIniziale.substring(0, 2));
		int annoFinale = Integer.parseInt(dataFinale.substring(6, 10));
		int meseFinale = Integer.parseInt(dataFinale.substring(3, 5));
		int giornoFinale = Integer.parseInt(dataFinale.substring(0, 2)); 
		inizio=LocalDate.of(annoIniziale, meseIniziale, giornoIniziale);
		fine=LocalDate.of(annoFinale, meseFinale, giornoFinale);
		dataInizio = Date.valueOf(inizio);
		dataFine = Date.valueOf(fine);

		//Aggiornamento notifiche
		Notifiche.update(Notifiche.UPDATE_PER_FERIE, dataInizio, dataFine, emailVF);

		//
		//Rimozione ferie nel periodo con arraylist e aggiornamento ferie residue
		//
		List<FerieBean> ferie = FerieDao.ferieInRange(dataInizio, dataFine, emailVF);
		//Rimozione giorni selezionati
		int numeroGiorniRimossi=0;
		for(FerieBean f : ferie) {
			FerieDao.rimuoviPeriodoFerie(f.getEmailVF(), f.getDataInizio(), f.getDataFine());
			numeroGiorniRimossi += contaNumeroGiorniFerie(f.getDataInizio().toLocalDate(),f.getDataFine().toLocalDate());
		}
		//Aggiornamento numero giorni di ferie, riaggiunta giorni rimossi
		aggiungiFerie(numeroGiorniRimossi, emailVF);


		int numeroGiorniPeriodo = 0;

		//Conteggio del numero di giorni lavorativi presenti nel periodo di ferie  
		while(inizio.compareTo(fine)<=0) {
			if (GiornoLavorativo.isLavorativo(Date.valueOf(inizio)))
				numeroGiorniFerie++;
			inizio=inizio.plusDays(1);
			numeroGiorniPeriodo++;
		}


		/**
		 * Controlli necessari prima di concedere le ferie:
		 * 1) Se il periodo contiene dei giorni di ferie già concessi, si lancia un'eccezione
		 * 2) Se il numero dei giorni è uguale a zero, si lancia un'eccezione
		 * Superati questi due controlli si entra nel blocco else dove si effettuano le operazioni
		 * necessarie alla concessione dei giorni di ferie. 
		 */

		if(numeroGiorniFerie == 0) {
			//
			//Reinserire ferie tolte e aggiornare carichi lavorativi
			//
			numeroGiorniRimossi=0;
			for(FerieBean f : ferie) {
				FerieDao.aggiungiPeriodoFerie(emailCT,f.getEmailVF(), f.getDataInizio(), f.getDataFine());
				numeroGiorniRimossi += contaNumeroGiorniFerie(f.getDataInizio().toLocalDate(),f.getDataFine().toLocalDate());
			}
			//Aggiornamento numero giorni di ferie, riaggiunta giorni rimossi
			aggiungiFerie(numeroGiorniRimossi, emailVF);
			throw new ScheduFIREException("Selezionato un periodo contenente giorni non lavorativi!");
		}
		else {
			//Ottenimento mansione vigile selezionato
			String mansioneVF = VigileDelFuocoDao.ottieni(emailVF).getMansione();

			/**
			 * Controllo se si raggiunge il numero minimo di vigile in caserma
			 * ALtrimenti si lancia un'eccezione
			 */
			if(!isPresentiNumeroMinimo(dataInizio, dataFine,mansioneVF)) {
				//
				//Reinserire ferie tolte e aggiornare carichi lavorativi
				//
				numeroGiorniRimossi=0;
				for(FerieBean f : ferie) {
					FerieDao.aggiungiPeriodoFerie(emailCT,f.getEmailVF(), f.getDataInizio(), f.getDataFine());
					numeroGiorniRimossi += contaNumeroGiorniFerie(f.getDataInizio().toLocalDate(),f.getDataFine().toLocalDate());
				}
				//Aggiornamento numero giorni di ferie, riaggiunta giorni rimossi
				aggiungiFerie(numeroGiorniRimossi, emailVF);
				throw new ScheduFIREException("Personale insufficiente! Impossibile inserire ferie.");
			}

			/**
			 * Controllo il vigile è già stato schedulato. In questo caso, si concedono
			 * le ferie e si aggiorna il CT mediante una notifica che lo avvisa 
			 * di dover sostituire dalla squadra il vigile a cui sono state concesse le ferie
			 */
			int i=0;
			List<Date> dateSostituzione = new ArrayList<Date>();
			Date dataInizioClone=(Date) dataInizio.clone();

			while(i < numeroGiorniPeriodo) {
				if(ComponenteDellaSquadraDao.isComponente(emailVF, dataInizioClone)) { 
					componente = true;
					//sostituzione = (Date) dataInizio.clone();
					dateSostituzione.add(dataInizioClone);
					dataInizioClone = Date.valueOf(dataInizioClone.toLocalDate().plusDays(1L));
					i++;
				}
				else{
					dataInizioClone = Date.valueOf(dataInizioClone.toLocalDate().plusDays(1L));
					i++;
				}
			}


			if(componente) {
				Notifiche.update(Notifiche.UPDATE_SQUADRE_PER_FERIE, dataInizio, dataFine, emailVF);
			
				for(int j=0; j< dateSostituzione.size(); j++) {
					Util.sostituisciVigile(dateSostituzione.get(j), emailVF);
				}

			}


			//Ottenimento numero totale giorni di ferie a disposizione del VF
			int feriePrecedenti = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
			int ferieCorrenti = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
			int totaleFerie = feriePrecedenti + ferieCorrenti;

			/**
			 * Sottrazione delle ferie da concedere, dal numero di ferie a disposizione del VF
			 * Se ha a disposizione ferie accumulate degli anni precedenti si scalano i giorni prima
			 * da li e successivamente dai giorni di ferie dell'anno in corso
			 */
			if(totaleFerie < numeroGiorniFerie) {
				//
				//Reinserire ferie tolte e aggiornare carichi lavorativi
				//
				numeroGiorniRimossi=0;
				for(FerieBean f : ferie) {
					FerieDao.aggiungiPeriodoFerie(emailCT,f.getEmailVF(), f.getDataInizio(), f.getDataFine());
					numeroGiorniRimossi += contaNumeroGiorniFerie(f.getDataInizio().toLocalDate(),f.getDataFine().toLocalDate());
				}
				//Aggiornamento numero giorni di ferie, riaggiunta giorni rimossi
				aggiungiFerie(numeroGiorniRimossi, emailVF);
				throw new ScheduFIREException("Giorni di ferie insufficienti");
			}
			else {
				if(feriePrecedenti >= numeroGiorniFerie)
					VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, feriePrecedenti-numeroGiorniFerie);
				else {
					VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, 0);
					numeroGiorniFerie -= feriePrecedenti;
					VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieCorrenti-numeroGiorniFerie);
				}
				//Concessione Ferie. Salvataggio del periodo nel DataBase 

				aggiunta = FerieDao.aggiungiPeriodoFerie(emailCT, emailVF, dataInizio, dataFine);

			}
		}

		Notifiche.update(Notifiche.UPDATE_PER_AVVIO);

		if(componente) {
			sessione.removeAttribute("squadraDiurno");
			sessione.removeAttribute("squadraNotturno");
		}

		response.setContentType("application/json");
		JSONArray array = new JSONArray();

		if(aggiunta) {
			int feriePDb = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
			int ferieCDb = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
			array.put(true);
			array.put(feriePDb);
			array.put(ferieCDb);
			array.put(componente);
		}
		else
			array.put(false);

		response.getWriter().append(array.toString());
	}


	//Metodo che verifica se nel periodo di ferie richiesto sono preseti almeno il numero minimo di VF
	private boolean isPresentiNumeroMinimo(Date dataIniziale, Date dataFinale, String mansioneVF) {

		boolean sufficienti = true;
		ArrayList<VigileDelFuocoBean> presenti = new ArrayList<VigileDelFuocoBean>();

		LocalDate inizio = dataIniziale.toLocalDate();
		LocalDate fine = dataFinale.toLocalDate();


		//per tutto il periodo considerato
		while(inizio.compareTo(fine) <= 0) {

			//se e' un giorno lavorativo
			if(GiornoLavorativo.isLavorativo(Date.valueOf(inizio))) {
				int capiSquadra = 0; 
				int autisti = 0;
				int vigili = 0;

				presenti = VigileDelFuocoDao.getDisponibili(Date.valueOf(inizio));

				//conto quanti capi squadra, autisti e vigili sono disponibili quel giorno
				for(int i=0; i<presenti.size(); i++) {
					String mansione = presenti.get(i).getMansione();
					if(mansione.equals("Capo Squadra"))
						capiSquadra += 1;
					else
						if(mansione.equals("Autista"))
							autisti += 1;
						else
							if(mansione.equals("Vigile"))
								vigili += 1;
				}

				//sottraggo il vigile che vuole andare in ferie
				if(mansioneVF.equals("Capo Squadra"))
					capiSquadra -= 1;
				else
					if(mansioneVF.equals("Autista"))
						autisti -= 1;
					else vigili -= 1;

				sufficienti = Util.abbastanzaPerTurno(capiSquadra, autisti, vigili);

				if(!sufficienti) {
					sufficienti=false;
					return sufficienti;
				}

			}
			inizio=inizio.plusDays(1);

		}
		return sufficienti;
	}


	//metodo che conteggiq i giorni di ferie selezionati
	private int contaNumeroGiorniFerie(LocalDate inizio, LocalDate fine) {
		int numeroGiorniFerie = 0;

		while(inizio.compareTo(fine)<=0) {
			if (GiornoLavorativo.isLavorativo(Date.valueOf(inizio)))
				numeroGiorniFerie++;
			inizio=inizio.plusDays(1);
		}
		
		return numeroGiorniFerie;

	}

	//metodo che aggiunge il numero di giorni di ferie rimossi al VF in questione 
	private void aggiungiFerie(int numeroFerie, String emailVF) {

		int feriePrecedenti = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
		int ferieCorrenti = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);

		if(ferieCorrenti + numeroFerie <= 22){
			VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieCorrenti + numeroFerie);
		}
		else {
			VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieCorrenti + (22 - ferieCorrenti));
			VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, feriePrecedenti + (numeroFerie - (22 - ferieCorrenti)));
		}
	}

}
