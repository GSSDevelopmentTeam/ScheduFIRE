/**
 * @author Francesca Pia Perillo
 */
package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.ComponenteDellaSquadraBean;
import model.bean.CredenzialiBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.UserDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;

/**
 * Servlet implementation class CalendarioServlet
 */
@WebServlet("/CalendarioServlet")
public class CalendarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public CalendarioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Date date = new Date(System.currentTimeMillis());
		int i; 
		//Array di mesi, per convertire il mese da numero a stringa
		String[] month = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", 
				"Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

		//array dei giorni del mese corrrente per riempire il calendario
		int[] days_month= new int[42];
		for(i=0;i<=41;i++) 
			days_month[i]=-1;;


			String dataCorrente = date.toString();

			//variabili contengono il numero dell'anno mese e giorno in formato stringa 
			String anno_stringa_numero = dataCorrente.substring(0, 4);
			String mese_stringa_numero = dataCorrente.substring(5, 7);
			String giorno_stringa_numero = dataCorrente.substring(8);


			//anno espresso in intero per controllare se Ã¨ bisestile o meno.
			int anno = Integer.parseInt(anno_stringa_numero);

			//mese scritto in formato Stringa: Gennaio, Febbraio [...]
			int mese = Integer.parseInt(mese_stringa_numero);
			String mese_stringa = month[mese-1];

			//attributi passati al CalendarioJSP.jsp
			request.setAttribute("anno", anno_stringa_numero);
			request.setAttribute("mese", mese_stringa_numero);
			request.setAttribute("giorno", giorno_stringa_numero);
			request.setAttribute("meseStringa", mese_stringa);


			riempiCalendario(mese, anno, days_month);

			//println per controllo calendario 
			System.out.println("Mese: ");
			for(i=0; i<days_month.length;i++) {
				System.out.println(" "+ days_month[i] + "; ");
			}	

			request.setAttribute("days_month", days_month);
			request.getRequestDispatcher("JSP/CalendarioJSP.jsp").forward(request, response); 
		
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
	
		
		String meseJSP = request.getParameter("mese");
		if(meseJSP!=null) {
			mese = Integer.parseInt(meseJSP);
		}
		
		String annoJSP = request.getParameter("anno");
		if(annoJSP!=null) {
			anno = Integer.parseInt(annoJSP);
			
		}
	
		//funzione per riempire il calendario
		riempiCalendario(mese, anno, days_month);
		//funzione per i giorni lavorativi
		riempiLavorativo(mese, anno, days_work);
		
		for(i=0; i<42; i++)
			System.out.println(days_work[i]+"\n");
		
		//INIZIO schedulazione dei vigili
		//creo i 4 array per ogni squadra
		//conterranno i nomi dei vigili del fuoco
		ArrayList<String> sala_operativa = new ArrayList<>();
		ArrayList<String> prima_partenza = new ArrayList<>();
		ArrayList<String> autoscala = new ArrayList<>();
		ArrayList<String> autobotte = new ArrayList<>();
		
		
		//return: email, tipologia e giorno lavorativo:
		ArrayList<ComponenteDellaSquadraBean> componenti_squadra = new ArrayList<>();  //bean
		componenti_squadra = ComponenteDellaSquadraDao.getComponenti(date);
		
	
		VigileDelFuocoBean vf_bean = new VigileDelFuocoBean();	//bean vf
		
		String tipologia, email, cognome_nome;
		for(ComponenteDellaSquadraBean c_s: componenti_squadra) {
			
			//tipologia -> sala_operativa, prima_partenza, autoscala, autobotte
			tipologia = c_s.getTipologiaSquadra();
			
			//email del vf in pos i
			email = c_s.getEmailVF();
			
			//ottengo il vf bean con la email data
			vf_bean = VigileDelFuocoDao.ottieni(email);
			
			cognome_nome = vf_bean.getCognome() + " " + vf_bean.getNome();
			
			switch (tipologia) {
			case "Sala Operativa":
				sala_operativa.add(cognome_nome);
				break;
			case "Prima Partenza":
				prima_partenza.add(cognome_nome);
				break;
			case "Auto Scala":
				autoscala.add(cognome_nome);
				break;
			case "Auto Botte":
				autobotte.add(cognome_nome);
				break;
			default: 
				//genera errore: nome squadra non consentito
				break;
			}
		}
		// FINE schedulazione vigili

		//attributi passati al CalendarioJSP.jsp
		//array delle squadre
		request.setAttribute("sala_operativa", sala_operativa);
		request.setAttribute("prima_partenza", prima_partenza);
		request.setAttribute("autoscala", autoscala);
		request.setAttribute("autobotte", autobotte);
		
		request.setAttribute("anno_corrente", anno_stringa_numero);
		request.setAttribute("mese_corrente", mese_stringa_numero);
		request.setAttribute("anno", anno);
		request.setAttribute("mese", mese);
		request.setAttribute("giorno", giorno);
		request.setAttribute("meseStringa", month[mese-1]);
		request.setAttribute("days_month", days_month);
		request.setAttribute("days_work", days_work);
		
		//dispatcher a calendario JSP
		request.getRequestDispatcher("JSP/CalendarioJSP.jsp").forward(request, response); 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


<<<<<<< HEAD
=======
	/**
	 * Funzione che specifica se l'anno ï¿½ o no bisestile.
	 * @param anno 
	 * @return boolean specifica se l'anno sia o meno bisestile
	 */
>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo
	private boolean isBisestile(int anno) {
		boolean bisestile = ( anno>1584 && 
				( (anno%400==0) || 
						(anno%4==0 && anno%100!=0) ) );

		return bisestile;
	}
<<<<<<< HEAD
	
	
	private void riempiCalendario (int mese, int anno, int[] days_month) {
		
			
		//per vedere qual è il primo giorno del mese
		LocalDate local=LocalDate.of(anno,mese,1);
	    String primoGiorno = local.getDayOfWeek().toString();
		
=======


	/**
	 * 
	 * PRECONDIZIONE: array dei giorni della settinama gia inizializzato con valori -1. 
	 * Questo metodo permette di modificare un array di interi 
	 * passato come parametro alla funzione stessa.
	 * 
	 * Le posizioni dell'array contenenti valore pari a '-1'
	 * sono posizioni considerate 'vuote' nel calendario.
	 * 
	 * @param mese mese dell'anno che si vuole riempire
	 * @param day_first_week primo lunedi di quel mese
	 * @param anno per la verifica del bisestile
	 * @param days_month array da modificare, nel quale verranno inseriti i giorni corretti
	 */
	public void riempiCalendario (int mese, int anno, int[] days_month) {

		//per vedere qual Ã¨ il primo lunedÃ¬ del mese
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
		cal.set(Calendar.YEAR,anno);
		cal.set(Calendar.MONTH,mese-1);	
		String primoGiorno= cal.getTime().toString().substring(0,3);
		System.out.println("primo giorno -> "+ primoGiorno);

>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo
		int day=-1;

		switch (primoGiorno) {

		case "MONDAY": day = 0; break; //lunedï¿½
		case "TUESDAY": day = 1; break; //martedï¿½
		case "WEDNESDAY": day = 2; break; //mercoledï¿½
		case "THURSDAY": day = 3; break; //giovedï¿½
		case "FRIDAY": day = 4; break; //venerdï¿½
		case "SATURDAY": day = 5; break; //sabato
		case "SUNDAY": day = 6; break; //domenica
		default: break;
		}

		int i;
<<<<<<< HEAD
		
=======

		int mese_array = mese--;	
		int giorno =0;

		switch (mese_array) {
		case 1: //mese febbraio

			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day;i++) {
	
>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo
		int giorno =1;
		switch (mese) {
		case 2: //mese febbraio
							
			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day;i++) {
					days_month[i] = giorno;
					giorno++;
<<<<<<< HEAD
				}
			} else {
				for(i=day;i<=27+day;i++) {
					days_month[i] = giorno;
					giorno++;
				}
			}			
		break;
		
	case 11: case 4: case 6: case 9: //mesi di 30 giorni
			
			for (i=day; i<=29+day; i++) {
				days_month[i] = giorno;
				giorno ++;
			}
	
		break;
		
	default: // mesi di 31 giorni
	
			for (i=day; i<=30+day; i++) {
				days_month[i] = giorno;
				giorno++;
			}
			
		break;
	}
	
}
	/*
	 * -1 -> giorno non da calendario
	 * 1 -> giorno lavorativo diurno
	 * 2 -> giorno lavorativo notturno
	 */
	private void riempiLavorativo(int mese, int anno, int[] days_work) {
		
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
	
		int day_bis = day;
		String data_stringa = "";
		Date data;
		
		
		int giorno =1;
		switch (mese) {
		case 2: //mese febbraio
							
			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day;i++) {
					data_stringa= anno+"-"+mese+"-"+giorno;
=======
				}
			} else {
				for(i=day;i<=27+day;i++) {
					days_month[i] = giorno;
>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo
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
<<<<<<< HEAD
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
=======
			}			
			break;

		case 10: case 3: case 5: case 8: //mesi di 30 giorni

			for (i=day; i<=29+day; i++) {
				days_month[i] = giorno;
				giorno ++;
			}

			break;

		default: // mesi di 31 giorni

			for (i=day; i<=30+day; i++) {
				days_month[i] = giorno;
				giorno++;
			}

			break;
		}
		for(i=0; i<days_month.length-1;i++);
		System.out.println("\nGiorno: "+days_month[i]);
>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo
		
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
<<<<<<< HEAD
	
	}
	
	
=======
>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo

}