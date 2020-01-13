package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import model.bean.FerieBean;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet per la rimozione di periodi o giorni di ferie di un Vigile del Fuoco
 * @author Nicola Labanca
 * @author Alfredo Giuliano
 */

@WebServlet("/RimuoviFerieServlet")
public class RimuoviFerieServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public RimuoviFerieServlet() {
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
		String emailVF;
		boolean rimozione =false;
		
		//Ottenimento parametri
		emailVF = request.getParameter("email");
		String dataIniz = request.getParameter("dataIniziale");
		String dataFin = request.getParameter("dataFinale"); 
		
		int annoIniz = Integer.parseInt(dataIniz.substring(6, 10));
		int meseIniz = Integer.parseInt(dataIniz.substring(3, 5));
		int giornoIniz = Integer.parseInt(dataIniz.substring(0, 2));
		int annoFin = Integer.parseInt(dataFin.substring(6, 10));
		int meseFin = Integer.parseInt(dataFin.substring(3, 5));
		int giornoFin = Integer.parseInt(dataFin.substring(0, 2));
		LocalDate inizio = LocalDate.of(annoIniz, meseIniz, giornoIniz);
		LocalDate fine = LocalDate.of(annoFin, meseFin, giornoFin);
		dataInizio = Date.valueOf(inizio);
		dataFine = Date.valueOf(fine);
		
		
		int numeroGiorniFerie = 0;
		
		FerieBean iniziale = null;
		FerieBean finale = null;
		 
		//Controllo del periodo di Ferie mediante metodo della classe FerieDao
		List<FerieBean> ferie = FerieDao.ferieInRange(dataInizio, dataFine, emailVF);
		
		
	
		//Istanziazione FerieBean iniziale
		if(ferie.size() > 0 && !ferie.get(0).getDataInizio().toLocalDate().equals(dataInizio.toLocalDate())) {
			FerieBean f = ferie.get(0);
			iniziale = new FerieBean();
			iniziale.setId(f.getId());
			iniziale.setEmailVF(f.getEmailVF());
			iniziale.setEmailCT(f.getEmailCT());
			iniziale.setDataInizio(f.getDataInizio());
			iniziale.setDataFine(dataInizio);

		}
		
		//Istanziazione FerieBean finale
		if(ferie.size() > 0 && !ferie.get(ferie.size() - 1).getDataFine().toLocalDate().equals(dataFine.toLocalDate())) {
			FerieBean f = ferie.get(ferie.size()-1);;
			finale = new FerieBean();
			finale.setId(f.getId());
			finale.setEmailVF(f.getEmailVF());
			finale.setEmailCT(f.getEmailCT());
			finale.setDataInizio(dataFine);
			finale.setDataFine(f.getDataFine());

		}
		
		//Ferie da non rimuovere
		if(iniziale != null) {
			FerieDao.aggiungiPeriodoFerie(iniziale.getEmailCT(), iniziale.getEmailVF(), iniziale.getDataInizio(), Date.valueOf(dataInizio.toLocalDate().plusDays(-1)));
			numeroGiorniFerie -= contaNumeroGiorniFerie(iniziale.getDataInizio().toLocalDate(),dataInizio.toLocalDate().plusDays(-1));
		}
		if(finale != null) {
			FerieDao.aggiungiPeriodoFerie(finale.getEmailCT(), finale.getEmailVF(), Date.valueOf(dataFine.toLocalDate().plusDays(1)), finale.getDataFine());
			numeroGiorniFerie -= contaNumeroGiorniFerie(dataFine.toLocalDate().plusDays(1),finale.getDataFine().toLocalDate());
		}
		
		//Rimozione giorni selezionati
		for(FerieBean f : ferie) {
				rimozione = FerieDao.rimuoviPeriodoFerie(f.getEmailVF(), f.getDataInizio(), f.getDataFine());
				numeroGiorniFerie += contaNumeroGiorniFerie(f.getDataInizio().toLocalDate(),f.getDataFine().toLocalDate());
		}
		
		//Aggiornamento numero giorni di ferie, riaggiunta giorni rimossi
		aggiungiFerie(numeroGiorniFerie, emailVF);
		
		response.setContentType("application/json");
		JSONArray array = new JSONArray();
		
		if(rimozione) { 
			int feriePDb = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
			int ferieCDb = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
			array.put(true);
			array.put(feriePDb);
			array.put(ferieCDb);
		}
		else
			array.put(false);
		
			response.getWriter().append(array.toString());
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
