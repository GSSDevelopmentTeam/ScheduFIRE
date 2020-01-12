package control;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet per la visualizzazione del personale disponibile nel turno.
 * @author Alfredo Giuliano
 */

@WebServlet("/PersonaleDisponibile")
public class PersonaleDisponibileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		
		Date giorno = null;
		
		
		//JSON per rendere non selezionabili sul calendario i giorni non lavorativi
		if(request.getParameter("JSON")!=null) {
			int mese=Integer.parseInt(request.getParameter("mese"));
			int anno=Integer.parseInt(request.getParameter("anno"));
			LocalDate dataInizio=LocalDate.of(anno, mese, 1);
			//array che contiene le date da rimuovere
			JSONArray array = new JSONArray();
			boolean cambioMese=false;
			while(!cambioMese ) {
				Date data=Date.valueOf(dataInizio);
				if(!GiornoLavorativo.isLavorativo(data)) {
					array.put(data);
				}
				cambioMese=dataInizio.getMonthValue()!=dataInizio.plusDays(1).getMonthValue();
				dataInizio=dataInizio.plusDays(1);
			}
			
			response.setContentType("application/json");
			response.getWriter().append(array.toString());
			return;
			
		}
		
		
		if(request.getParameter("data")==null) {

			LocalDateTime ora=LocalDateTime.now();
			LocalDateTime inizioDiurno=LocalDateTime.of(ora.getYear(), ora.getMonth(), ora.getDayOfMonth(), 8, 00);
			LocalDateTime fineDiurno=inizioDiurno.plusHours(12);
			LocalDateTime inizioNotturno=fineDiurno;
			LocalDateTime fineNotturno=inizioNotturno.plusHours(12);

			giorno=Date.valueOf(ora.toLocalDate());




			/*
			 * Per ricavare se nel momento in cui viene chiamata la servlet si � nel turno lavorativo o meno, prendo in considerazione
			 * 3 possibilit�: 
			 * 1) il giorno considerato � lavorativo ed � il giorno con turno diurno;
			 * 2) il giorno considerato � lavorativo ed � il giorno con turno notturno;
			 * 3) non � un giorno lavorativo.
			 * Delle prime due ho poi valutato se nell'istante in cui si chiama la servlet il turno:
			 * 1)deve ancora iniziare
			 * 2)� in corso
			 * 3)� terminato
			 * 
			 */

			LocalDate giornoLavorativo=null;
			//giorno lavorativo e turno diurno, turno finito
			String giornoLavoro=""+giorno.toLocalDate().getDayOfMonth()+" "+Mese(giorno.toLocalDate().getMonthValue())+" "+giorno.toLocalDate().getYear();
			if(GiornoLavorativo.isLavorativo(giorno) && GiornoLavorativo.isDiurno(giorno) && ora.isAfter(fineDiurno)) {
	
					giorno=GiornoLavorativo.nextLavorativo(giorno);

			}
			//giorno lavorativo e turno notturno, turno finito
			else if(GiornoLavorativo.isLavorativo(giorno) && !GiornoLavorativo.isDiurno(giorno) && ora.isAfter(fineNotturno)) {

					giorno=GiornoLavorativo.nextLavorativo(giorno);

			}
			//giorno non lavorativo
			else {
				giorno=GiornoLavorativo.nextLavorativo(giorno);

			}
		}
		

		
		else {
			String datastr = request.getParameter("data");

			int anno = Integer.parseInt(datastr.substring(6, 10));
			int mese = Integer.parseInt(datastr.substring(3, 5));
			int giorn = Integer.parseInt(datastr.substring(0, 2));
			giorno=Date.valueOf(LocalDate.of(anno, mese, giorn));

		}

		//Ottenimento parametro
		String ordinamentoStr = request.getParameter("ordinamento");
		int ordinamento=-1;
		//Se il parametro non � settato, l'ordinamento sarà quello di default
		if(ordinamentoStr == null)
			ordinamentoStr = "";
		switch(ordinamentoStr) {
		case "nome": 
			ordinamento=(VigileDelFuocoDao.ORDINA_PER_NOME);
			break;
		case "cognome": 
			ordinamento=(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			break;
		case "grado": 
			ordinamento=(VigileDelFuocoDao.ORDINA_PER_GRADO);
			break;
		case "disponibilita": 
			ordinamento=(VigileDelFuocoDao.ORDINA_PER_MANSIONE);
			break;
		default:
			ordinamento=(VigileDelFuocoDao.ORDINA_PER_MANSIONE);
			ordinamentoStr = "disponibilita";
		}
		
		
		
		//prendo i vigili del fuoco disponibili alla data odierna
		ArrayList<VigileDelFuocoBean> vigiliCompleti=new ArrayList(VigileDelFuocoDao.ottieni(ordinamento));
		ArrayList<VigileDelFuocoBean> vigiliDisponibili=VigileDelFuocoDao.getDisponibili(giorno,ordinamento);
		ArrayList<ComponenteDellaSquadraBean> componenti=ComponenteDellaSquadraDao.getComponenti(giorno);
		ArrayList<VigileDelFuocoBean> vigiliFerie=new ArrayList<VigileDelFuocoBean>
				(VigileDelFuocoDao.ottieniInFerie(ordinamento, giorno));
		ArrayList<VigileDelFuocoBean> vigiliMalattia=new ArrayList<VigileDelFuocoBean>
				(VigileDelFuocoDao.ottieniInMalattia(ordinamento, giorno));
		

		Collections.sort(componenti, new ComponenteComparator());
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dataparse = simpleDateFormat.format(giorno);
		request.setAttribute("data", dataparse);
		
		String giornoLavoro=""+giorno.toLocalDate().getDayOfMonth()+" "+Mese(giorno.toLocalDate().getMonthValue())+" "+giorno.toLocalDate().getYear();
		request.setAttribute("titolo", "Il personale del giorno "+giornoLavoro+" &egrave il seguente");

		request.setAttribute("ordinamento", ordinamentoStr);
		request.setAttribute("vigiliCompleti", vigiliCompleti);
		request.setAttribute("vigiliDisponibili", vigiliDisponibili);
		request.setAttribute("vigiliFerie", vigiliFerie);
		request.setAttribute("vigiliMalattia", vigiliMalattia);
		request.setAttribute("componenti", componenti);
		request.getRequestDispatcher("JSP/PersonaleDisponibileJSP.jsp").forward(request, response);

	}




	private String Mese (int mese) {
		String meseString="";
		switch (mese) {
		case 1: meseString="Gennaio";break;
		case 2: meseString="Febbraio";break;
		case 3: meseString="Marzo";break;
		case 4: meseString="Aprile";break;
		case 5: meseString="Maggio";break;
		case 6: meseString="Giugno";break;
		case 7: meseString="Luglio";break;
		case 8: meseString="Agosto";break;
		case 9: meseString="Settembre";break;
		case 10: meseString="Ottobre";break;
		case 11: meseString="Novembre";break;
		case 12: meseString="Dicembre";break;
		}
		return meseString;
	}

	
	class ComponenteComparator implements Comparator<ComponenteDellaSquadraBean> {

		/*
		 * Per ordinare l'array di componenti della squadra in base alla tipologia della squadra di appartenenza
		 * con priorit� a sala operativa, poi prima partenza, poi auto scala e infine auto botte.
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

}
