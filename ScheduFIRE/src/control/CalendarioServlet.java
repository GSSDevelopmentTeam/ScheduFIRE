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

/**
 * Servlet implementation class CalendarioServlet
 * @autor Francesca Pia Perillo
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
		riempiCalendario(mese, anno, days_month);
		//funzione per i giorni lavorativi
		riempiLavorativo(mese, anno, days_work);
		//funzione per il nome del turno
		riempiTurno(mese, anno, days_turno);

		for(i=0; i<42; i++)
			System.out.println(days_turno[i]+"\n");

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
				System.out.println("Parametro nome squadra non valido."
						+ "LINEA 126 CalendarioServlet");
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
		request.setAttribute("days_turno", days_turno);

		//dispatcher a calendario JSP
		request.getRequestDispatcher("JSP/CalendarioJSP.jsp").forward(request, response); 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


	private boolean isBisestile(int anno) {
		boolean bisestile = ( anno>1584 && 
				( (anno%400==0) || 
						(anno%4==0 && anno%100!=0) ) );

		return bisestile;
	}

	private void riempiCalendario (int mese, int anno, int[] days_month) {

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

		int giorno =1;
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
