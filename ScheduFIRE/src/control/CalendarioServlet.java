package control;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class CalendarioServlet
 * @author Francesca Pia Perillo
 */
@WebServlet("/CalendarioServlet")
public class CalendarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public CalendarioServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isAutenticato(request);
		if(request.getSession().getAttribute("ruolo").equals("capoturno"))
				Util.isCapoTurno(request);
		Date date = new Date(System.currentTimeMillis());
		String dataCorrente = date.toString();
		
		//variabili contengono il numero dell'anno mese e giorno in formato stringa 
		String anno_stringa_numero = dataCorrente.substring(0, 4);
		String mese_stringa_numero = dataCorrente.substring(5, 7);
		String giorno_stringa_numero = dataCorrente.substring(8);

		//Array di mesi, per convertire il mese da numero a stringa
		String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", 
				"Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
		int i; 

		//giorno mese e anno espresso in intero.
		int giorno = Integer.parseInt(giorno_stringa_numero);
		int mese = Integer.parseInt(mese_stringa_numero);
		int anno = Integer.parseInt(anno_stringa_numero);

		//array dei giorni del mese corrrente per riempire il calendario
		int[] days_month= new int[42];
		for(i=0;i<=41;i++) 
			days_month[i]=-1;

		//array dei giorni lavorativi
		int [] days_work = new int [42];
		for(i=0; i<=41; i++)
			days_work[i]=-1;
		
		//array dei turni lavorativi
		String[] days_turno = new String [42];
		for(i=0; i<=41; i++)
			days_turno[i]="";


		String meseJSP = request.getParameter("mese");
		if(meseJSP!=null) {
			mese = Integer.parseInt(meseJSP);
		}

		String annoJSP = request.getParameter("anno");
		if(annoJSP!=null) {
			anno = Integer.parseInt(annoJSP);

		}

		//funzione per riempire il calendario
		int len = riempiCalendario(mese, anno, days_month);
		//funzione per i giorni lavorativi
		riempiLavorativo(mese, anno, days_work);
		//funzione per il nome del turno
		riempiTurno(mese, anno, days_turno);
		
		
		
		request.setAttribute("date", date);
		request.setAttribute("anno_corrente", anno_stringa_numero);
		request.setAttribute("mese_corrente", mese_stringa_numero);
		request.setAttribute("anno", anno);
		request.setAttribute("mese", mese);
		request.setAttribute("giorno", giorno);
		request.setAttribute("meseStringa", month[mese-1]);
		request.setAttribute("days_month", days_month);
		request.setAttribute("days_work", days_work);
		request.setAttribute("days_turno", days_turno);
		request.setAttribute("len", len);

		//dispatcher a calendario JSP
		request.getRequestDispatcher("JSP/CalendarioJSP.jsp").forward(request, response); 
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


	private boolean isBisestile(int anno) {
		boolean bisestile = ( anno>1584 && 
				( (anno%400==0) || 
						(anno%4==0 && anno%100!=0) ) );

		return bisestile;
	}

	private int riempiCalendario (int mese, int anno, int[] days_month) {

		//per vedere qual è il primo giorno del mese
		LocalDate local=LocalDate.of(anno,mese,1);
		String primoGiorno = local.getDayOfWeek().toString();

		int day=-1;

		switch (primoGiorno) {

		case "MONDAY": day = 0; break; //lunedì
		case "TUESDAY": day = 1; break; //martedì
		case "WEDNESDAY": day = 2; break; //mercoledì
		case "THURSDAY": day = 3; break; //giovedì
		case "FRIDAY": day = 4; break; //venerdì
		case "SATURDAY": day = 5; break; //sabato
		case "SUNDAY": day = 6; break; //domenica
		default: break;
		}
		
		int len;
		


		int i;

		int giorno =1;
		switch (mese) {
		case 2: //mese febbraio

			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day;i++) {
					days_month[i] = giorno;
					giorno++;
				}
				
			switch (day) {
			case 0 : len = 8; break; 
			case 1 : len = 8; break; 
			case 2 : len = 8; break; 
			case 3 : len = 8; break;
			case 4 : len = 8; break; 
			default: len = 0;
			}

			}
			else {
				for(i=day;i<=27+day;i++) {
					days_month[i] = giorno;
					giorno++;
				}
			}
			
			switch (day) {
			case 0 : len = 8; break; 
			case 1 : len = 8; break; 
			case 2 : len = 8; break; 
			case 3 : len = 8; break;
			case 4 : len = 8; break;
			case 5 : len = 8; break; 
			default: len = 0;
			}
			
			
			break;

		case 11: case 4: case 6: case 9: //mesi di 30 giorni

			for (i=day; i<=29+day; i++) {
				days_month[i] = giorno;
				giorno ++;
			}
			
			switch (day) {
			case 0 : len = 8; break; 
			case 1 : len = 8; break; 
			case 2 : len = 8; break;
			default: len = 0;
			}

			break;

		default: // mesi di 31 giorni

			for (i=day; i<=30+day; i++) {
				days_month[i] = giorno;
				giorno++;
			}
			
			switch (day) {
			case 0 : len = 8; break; 
			case 1 : len = 8; break; 
			case 2 : len = 8; break; 
			case 3 : len = 7; break;
			case 4 : len = 7; break;
			default: len = 0;
			}

			break;
		}
		

		return len;

	}
	
	private void riempiLavorativo(int mese, int anno, int[] days_work) {
		
		 // -1 -> giorno non da calendario
		 // 1 -> giorno lavorativo diurno
		 //2 -> giorno lavorativo notturno
	
		//per vedere qual è il primo giorno del mese
		LocalDate local=LocalDate.of(anno,mese,1);
		String primoGiorno = local.getDayOfWeek().toString();

		int day=-1;

		switch (primoGiorno) {

		case "MONDAY": day = 0; break; //lunedì
		case "TUESDAY": day = 1; break; //martedì
		case "WEDNESDAY": day = 2; break; //mercoledì
		case "THURSDAY": day = 3; break; //giovedì
		case "FRIDAY": day = 4; break; //venerdì
		case "SATURDAY": day = 5; break; //sabato
		case "SUNDAY": day = 6; break; //domenica
		default: break;
		}

		int i;
		String data_stringa = "";
		Date data;


		int giorno =1;
		switch (mese) {
		case 2: //mese febbraio

			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day;i++) {
					data_stringa= anno+"-"+mese+"-"+giorno;
					giorno++;
					data = Date.valueOf(data_stringa);
					if(GiornoLavorativo.isLavorativo(data)) {

						if(GiornoLavorativo.isDiurno(data)) {
							days_work[i] = 1;
						}else {
							days_work[i] = 2;
						}	
					}
				}
			} else {
				for(i=day;i<=27+day;i++) {
					data_stringa= anno+"-"+mese+"-"+giorno;
					giorno++;
					data = Date.valueOf(data_stringa);
					if(GiornoLavorativo.isLavorativo(data)) {

						if(GiornoLavorativo.isDiurno(data)) {
							days_work[i] = 1;
						}else {
							days_work[i] = 2;
						}	
					}
				}
			}			
			break;

		case 11: case 4: case 6: case 9: //mesi di 30 giorni

			for (i=day; i<=29+day; i++) {
				data_stringa= anno+"-"+mese+"-"+giorno;
				giorno++;
				data = Date.valueOf(data_stringa);
				if(GiornoLavorativo.isLavorativo(data)) {

					if(GiornoLavorativo.isDiurno(data)) {
						days_work[i] = 1;
					}else {
						days_work[i] = 2;
					}	
				}
			}

			break;

		default: // mesi di 31 giorni

			for (i=day; i<=30+day; i++) {
				data_stringa= anno+"-"+mese+"-"+giorno;
				giorno++;
				data = Date.valueOf(data_stringa);
				if(GiornoLavorativo.isLavorativo(data)) {

					if(GiornoLavorativo.isDiurno(data)) {
						days_work[i] = 1;
					}else {
						days_work[i] = 2;
					}	
				}	
			}

			break;
		}

	}

	private void riempiTurno(int mese, int anno, String[] days_turno) {
		
		LocalDate local=LocalDate.of(anno,mese,1);
		String primoGiorno = local.getDayOfWeek().toString();

		int day=-1;

		switch (primoGiorno) {

		case "MONDAY": day = 0; break; //lunedì
		case "TUESDAY": day = 1; break; //martedì
		case "WEDNESDAY": day = 2; break; //mercoledì
		case "THURSDAY": day = 3; break; //giovedì
		case "FRIDAY": day = 4; break; //venerdì
		case "SATURDAY": day = 5; break; //sabato
		case "SUNDAY": day = 6; break; //domenica
		default: break;
		}

		int i;
		String data_stringa = "";
		Date data;


		int giorno =1;
		switch (mese) {
		case 2: //mese febbraio

			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day;i++) {
					data_stringa= anno+"-"+mese+"-"+giorno;
					giorno++;
					data = Date.valueOf(data_stringa);
					if(GiornoLavorativo.isLavorativo(data))
						days_turno[i] = GiornoLavorativo.nomeTurnoB(data);
				}
			} else {
				for(i=day;i<=27+day;i++) {
					data_stringa= anno+"-"+mese+"-"+giorno;
					giorno++;
					data = Date.valueOf(data_stringa);
					if(GiornoLavorativo.isLavorativo(data))
						days_turno[i] = GiornoLavorativo.nomeTurnoB(data);
				}
			}			
			break;

		case 11: case 4: case 6: case 9: //mesi di 30 giorni

			for (i=day; i<=29+day; i++) {
				data_stringa= anno+"-"+mese+"-"+giorno;
				giorno++;
				data = Date.valueOf(data_stringa);
				if(GiornoLavorativo.isLavorativo(data))
					days_turno[i] = GiornoLavorativo.nomeTurnoB(data);			
			}

			break;

		default: // mesi di 31 giorni

			for (i=day; i<=30+day; i++) {
				data_stringa= anno+"-"+mese+"-"+giorno;
				giorno++;
				data = Date.valueOf(data_stringa);
				if(GiornoLavorativo.isLavorativo(data))
					days_turno[i] = GiornoLavorativo.nomeTurnoB(data);			
			}

			break;
		}
	}

}
