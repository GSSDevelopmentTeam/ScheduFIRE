package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CapoTurnoBean;
import model.bean.CredenzialiBean;
import model.bean.VigileDelFuocoBean;
import model.dao.CapoTurnoDao;
import model.dao.VigileDelFuocoDao;

/**
 * Servlet implementation class AggiungiVFServlet
 */

/*
 *  Servlet che si occupa dell'inserimento di un nuovo VF nel database. 
 */

@WebServlet("/AggiungiVFServlet")
public class AggiungiVFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiVFServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
		
		//Ottenimento credenziali dell'utente dalla sessione
		CredenzialiBean credenziali = (CredenzialiBean) session.getAttribute("credenziali"); 
		
		//Ottenimento dati del CapoTurno
		CapoTurnoBean ct = CapoTurnoDao.ottieni(credenziali.getUsername());
	
		VigileDelFuocoBean vf = null;
		
		// Variabile email per il VF
		String email = null;
		
		//Ottenimento parametro email dalla richiesta
		Object emailVF = request.getParameter("email");
		
		//Controllo email
		if( emailVF == null )
			//lancio eccezione
			;
		
		if( emailVF.getClass().getSimpleName() != "String" )
			//lancio eccezione
			;
		
		if((vf = VigileDelFuocoDao.ottieni(email)) != null) {
			
			if(vf.isAdoperabile()) {
				//lancio eccezione
			} else {
				//Si setta il campo Adoperabile
				if(! VigileDelFuocoDao.setAdoperabile(email, true)) 
					//lancio eccezione
					;
			}
			
		} else {
		
			// Variabili VF
			String nome = null;
			String cognome = null;
			String turno = null;
			String mansione = null;
			String username = "turno" + ct.getTurno();
			String grado = null;
			Integer giorniFerieAnnoCorrente = null;
			Integer giorniFerieAnnoPrecedente = null;
			
			// Ottenimento parametri del VF dalla richiesta
			Object nomeVF = request.getParameter("nome");
			Object cognomeVF = request.getParameter("cognome");
			Object turnoVF = request.getParameter("turno");
			Object mansioneVF = request.getParameter("mansione");
			Object giorniFerieAnnoCorrenteVF = request.getParameter("giorniFerieAnnoCorrente");
			Object giorniFerieAnnoPrecedenteVF = request.getParameter("giorniFerieAnnoPrecedente");
			Object gradoVF = request.getParameter("grado");
			
			//aggiungere controlli dei parametri
			
			//Controlli
	
			if( nomeVF == null )
				//lancio eccezione
				;
			
			if( nomeVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			if( cognomeVF == null )
				//lancio eccezione
				;
			
			if( cognomeVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			if( turnoVF == null )
				//lancio eccezione
				;
			
			if( turnoVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			if( mansioneVF == null )
				//lancio eccezione
				;
			
			if( mansioneVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			if( giorniFerieAnnoCorrenteVF == null )
				//lancio eccezione
				;
			
			if( giorniFerieAnnoCorrenteVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			if( giorniFerieAnnoPrecedenteVF == null )
				//lancio eccezione
				;
			
			if( giorniFerieAnnoPrecedenteVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			if( gradoVF == null )
				//lancio eccezione
				;
			
			if( gradoVF.getClass().getSimpleName() != "String" )
				//lancio eccezione
				;
			
			nome = (String) nomeVF;
			cognome = (String) cognomeVF;
			email = (String) emailVF;
			turno = (String) turnoVF;
			mansione = (String) mansioneVF;
			giorniFerieAnnoCorrente = Integer.parseInt(giorniFerieAnnoCorrenteVF.toString()); //da controllare!!!
			giorniFerieAnnoPrecedente = Integer.parseInt(giorniFerieAnnoPrecedenteVF.toString()); //
			grado = (String) gradoVF;
			
			// Instanziazione dell'oggetto VigileDelFuocoBean
			vf = new VigileDelFuocoBean(nome, cognome, email, turno, mansione, username,
															grado, giorniFerieAnnoCorrente, giorniFerieAnnoPrecedente);
			
			// Controllo salvataggio Vigile del Fuoco nel database
			if(! VigileDelFuocoDao.salva(vf))
				// Lancio eccezione
				;
		
		}
		
		// Reindirizzamento alla jsp
		request.getRequestDispatcher("/").forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
