package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.Util;
import util.Validazione;

/**
 * Servlet che si occupa del rendere non adoperabili i VigileDelFuocoBean nel database. 
 * @author Eugenio Sottile 
 */

@WebServlet("/EliminaVFServlet")
public class EliminaVFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaVFServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Controllo login
		Util.isCapoTurno(request);
		
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
		
		//Ottenimento parametro email dalla richiesta
		String email = request.getParameter("email");
		
		//Controllo email
		if( ! Validazione.email(email) )
			throw new ParametroInvalidoException("Il parametro 'email' è errato!");
		
		email += "@vigilfuoco.it";
		
		//Controllo se il vf scelto è gia schedulato in delle squadre
		LocalDate inizio = LocalDate.now();
		LocalDate fine = inizio.plusDays(7);
		boolean eliminabile = true;
		while( inizio.compareTo(fine) != 0 ) {
			
			eliminabile = eliminabile && ! ComponenteDellaSquadraDao.isComponente(email, Date.valueOf(inizio) );
			
			inizio = inizio.plusDays(1);
			
		}
				
		if( ! eliminabile )
			throw new GestionePersonaleException("Non puoi modificare un Vigile del Fuoco scelto per le squadre"
					+ " dei prossimi giorni lavorativi! Sostiuiscilo prima.");
		
		if( ! VigileDelFuocoDao.setAdoperabile(email, false))
			throw new GestionePersonaleException("La cancellazione del vigile del fuoco non è andata a buon fine!");
		
		session.setAttribute("risultato", "La cancellazione del Vigile del Fuoco è avvenuto con successo!");
		
		// Reindirizzamento alla jsp
		response.sendRedirect("./GestionePersonaleServlet");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
