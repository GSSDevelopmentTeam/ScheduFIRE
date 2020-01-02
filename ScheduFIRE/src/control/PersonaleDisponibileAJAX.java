package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.PersonaleDisponibileServlet.ComponenteComparator;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class PersonaleDisponibileAJAX
 */
@WebServlet("/PersonaleDisponibileAJAX")
public class PersonaleDisponibileAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonaleDisponibileAJAX() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		LocalDateTime ora=LocalDateTime.now();
		LocalDateTime inizioDiurno=LocalDateTime.of(ora.getYear(), ora.getMonth(), ora.getDayOfMonth(), 8, 00);
		LocalDateTime fineDiurno=inizioDiurno.plusHours(12);
		LocalDateTime inizioNotturno=fineDiurno;
		LocalDateTime fineNotturno=inizioNotturno.plusHours(12);

		Date giorno=Date.valueOf(ora.toLocalDate());
		
		
		
		
		/*
		 * Per ricavare se nel momento in cui viene chiamata la servlet si è nel turno lavorativo o meno, prendo in considerazione
		 * 3 possibilità: 
		 * 1) il giorno considerato è lavorativo ed è il giorno con turno diurno;
		 * 2) il giorno considerato è lavorativo ed è il giorno con turno notturno;
		 * 3) non è un giorno lavorativo.
		 * Delle prime due ho poi valutato se nell'istante in cui si chiama la servlet il turno:
		 * 1)deve ancora iniziare
		 * 2)è in corso
		 * 3)è terminato
		 * 
		 */
		
		
		//giorno lavorativo e turno diurno
		if(GiornoLavorativo.isLavorativo(giorno) && GiornoLavorativo.isDiurno(giorno)) {
			if(ora.isBefore(inizioDiurno)) {
				request.setAttribute("titolo", "Il turno lavorativo diurno inizierà tra poco, il personale disponibile sarà il seguente");
			}
			else if(ora.isAfter(inizioDiurno) && ora.isBefore(fineDiurno)) {
				request.setAttribute("titolo", "Il personale disponibile è il seguente");
			}
			else {
				giorno=GiornoLavorativo.nextLavorativo(giorno);
				String giornoLavoro=""+giorno.toLocalDate().getDayOfMonth()+" "+Mese(giorno.toLocalDate().getMonthValue())+" "+giorno.toLocalDate().getYear();
				request.setAttribute("titolo", "Il personale disponibile per domani "+giornoLavoro+" sarà il seguente");

			}
		}
		//giorno lavorativo e turno notturno
		else if(GiornoLavorativo.isLavorativo(giorno) && !GiornoLavorativo.isDiurno(giorno)) {
			if(ora.isBefore(inizioNotturno)) {
				request.setAttribute("titolo", "Il turno lavorativo notturno inizierà tra poco, il personale disponibile sarà il seguente");
			}
			else if(ora.isAfter(inizioNotturno) && ora.isBefore(fineNotturno)) {
				request.setAttribute("titolo", "Il personale disponibile è il seguente");
			}
			else {
				giorno=GiornoLavorativo.nextLavorativo(giorno);
				String giornoLavoro=""+giorno.toLocalDate().getDayOfMonth()+" "+Mese(giorno.toLocalDate().getMonthValue())+" "+giorno.toLocalDate().getYear();
				request.setAttribute("titolo", "Il personale disponibile per il giorno "+ giornoLavoro +" sarà il seguente");
			}

		}
		//giorno non lavorativo
		else {
			giorno=GiornoLavorativo.nextLavorativo(giorno);
			String giornoLavoro=""+giorno.toLocalDate().getDayOfMonth()+" "+Mese(giorno.toLocalDate().getMonthValue())+" "+giorno.toLocalDate().getYear();
			request.setAttribute("titolo", "Il personale disponibile per il giorno "+ giornoLavoro +" sarà il seguente");
			
		}
		

		//prendo i vigili del fuoco disponibili alla data odierna
		ArrayList<VigileDelFuocoBean> vigili=VigileDelFuocoDao.getDisponibili(giorno);
		ArrayList<ComponenteDellaSquadraBean> componenti=ComponenteDellaSquadraDao.getComponenti(giorno);
		//Collections.sort(vigili, new VigileComparator());
		Collections.sort(componenti, new ComponenteComparator());
		String email=request.getParameter("email");
		request.setAttribute("vigili", vigili);
		request.setAttribute("componenti", componenti);
		request.setAttribute("email", email);		
		request.getRequestDispatcher("JSP/PersonaleDisponibileAJAXJSP.jsp").forward(request, response);
		
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
	
	
	
	
	class VigileComparator implements Comparator<VigileDelFuocoBean> {

		@Override
		public int compare(VigileDelFuocoBean o1, VigileDelFuocoBean o2) {
			String mansione1=o1.getMansione();
			String mansione2=o2.getMansione();
			if (mansione1.equals("Capo Squadra") && mansione2.equals("Capo Squadra"))
				return o1.getCognome().compareTo(o2.getCognome());
			if(mansione1.equals("Capo Squadra"))
				return -1;
			if(mansione2.equals("Capo Squadra"))
				return 1;
			return o1.getMansione().compareTo(o2.getMansione());
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
	
}
