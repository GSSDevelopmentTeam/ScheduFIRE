/**
 * @author Francesca Pia Perillo
 */
package control;

import java.io.IOException;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		if(request.getSession().getAttribute("ruolo")==null ) {
			response.sendRedirect("Login");
		}

		else {





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


				//anno espresso in intero per controllare se è bisestile o meno.
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
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


	/**
	 * Funzione che specifica se l'anno è o non è bisestile.
	 * @param anno 
	 * @return boolean specifica se l'anno sia o meno bisestile
	 */
	public boolean isBisestile(int anno) {
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
	public void riempiCalendario (int mese, int anno, int[] days_month) {

		//per vedere qual è il primo lunedì del mese
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
		cal.set(Calendar.YEAR,anno);
		cal.set(Calendar.MONTH,mese-1);	
		String primoGiorno= cal.getTime().toString().substring(0,3);
System.out.println("primo giorno -> "+ primoGiorno);
		
		int day=-1;

		switch (primoGiorno) {
		case "Mon": day = 0; break; //lunedì
		case "Tue": day = 1; break; //martedì
		case "Wed": day = 2; break; //mercoledì
		case "Thu": day = 3; break; //giovedì
		case "Fri": day = 4; break; //venerdì
		case "Sat": day = 5; break; //sabato
		case "Sun": day = 6; break; //domenica
		default: break;
		}

		int i;

		int mese_array = mese--;	
		int giorno =0;

		switch (mese_array) {
		case 1: //mese febbraio

			if(isBisestile(anno)) { //anno bisestile
				for(i=day;i<=28+day+1;i++) {
					days_month[i] = giorno;
					giorno++;
				}
			} else {
				for(i=day;i<=27+day+1;i++) {
					days_month[i] = giorno;
					giorno++;
				}
			}			
			break;

		case 10: case 3: case 5: case 8: //mesi di 30 giorni

			for (i=day; i<=29+day+1; i++) {
				days_month[i] = giorno;
				giorno ++;
			}

			break;

		default: // mesi di 31 giorni

			for (i=day; i<=30+day+1; i++) {
				days_month[i] = giorno;
				giorno++;
			}

			break;
		}
	}

}
