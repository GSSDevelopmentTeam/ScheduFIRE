/**
 * @author Francesca Pia Perillo
 */
package control;

import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;

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

		//mese scritto in formato Stringa: Gennaio, Febbraio [...]
		int mese = Integer.parseInt(mese_stringa_numero);
		int anno = Integer.parseInt(anno_stringa_numero);
		
		String mese_stringa = month[mese-1];
	
		//array dei giorni del mese corrrente per riempire il calendario
		int[] days_month= new int[42];
		for(i=0;i<=41;i++) 
			days_month[i]=-1;
		//funzione per riempire il calendario
		riempiCalendario(mese, anno, days_month);
				
		//println per controllo calendario 
		System.out.println("Mese: ");
		for(i=0; i<days_month.length;i++) {
			System.out.println(" "+ days_month[i] + "; ");
		}
	
		//attributi passati al CalendarioJSP.jsp
		request.setAttribute("anno", anno_stringa_numero);
		request.setAttribute("mese", mese_stringa_numero);
		request.setAttribute("giorno", giorno_stringa_numero);
		request.setAttribute("meseStringa", mese_stringa);
		request.setAttribute("days_month", days_month);
		
		
		//dispatcher a calendario JSP
		request.getRequestDispatcher("JSP/CalendarioJSP.jsp").forward(request, response); 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	/**
	 * Funzione che specifica se l'anno Ã¨ o non Ã¨ bisestile.
	 * @param anno 
	 * @return boolean specifica se l'anno sia o meno bisestile
	 */
	private boolean isBisestile(int anno) {
		boolean bisestile = ( anno>1584 && 
				( (anno%400==0) || 
						(anno%4==0 && anno%100!=0) ) );

		return bisestile;
	}
	
	
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
	private void riempiCalendario (int mese, int anno, int[] days_month) {
		
			
		//per vedere qual è il primo lunedì del mese
		LocalDate local=LocalDate.of(anno,mese,1);
	    String primoGiorno = local.getDayOfWeek().toString();
		System.out.println("PRIMO GIORNO -> "+ primoGiorno);
		
		int day=-1;
		
		switch (primoGiorno) {

		case "MONDAY": day = 0; break; //lunedì
		case "TUESDAY": day = 1; break; //martedì
		case "WEDNESDAY": day = 2; break; //mercoledì
		case "THUESDAY": day = 3; break; //giovedì
		case "FRIDAY": day = 4; break; //venerdì
		case "SATURDAY": day = 5; break; //sabato
		case "SUNDAY": day = 6; break; //domenica
		default: break;
		}
		
		int i;
	
		int giorno =1;
		System.out.println("SERVLET CALENDARIO: "+giorno);
		switch (mese) {
		case 2: //mese febbraio
							
				if(isBisestile(anno)) { //anno bisestile
					for(i=day;i<=28+day;i++) {
						days_month[i] = giorno;
						giorno++;
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
	
}