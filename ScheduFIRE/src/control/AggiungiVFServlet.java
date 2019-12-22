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

/**
 *  Servlet che si occupa dell'inserimento di un nuovo VigileDelFuocoBean nel database.
 *  @author Eugenio Sottile 
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
		
		//Controllo credenziali
		if( credenziali == null )
			//lancio eccezione
			;
		/*
		if( credenziali.getRuolo() == "vigile" ) //definire bene la stringa
			//lancio eccezione
			;
		
		//Ottenimento dati del CapoTurno
		CapoTurnoBean ct = CapoTurnoDao.ottieni(credenziali.getUsername());
		
		//Controllo CapoTurno
		if(ct == null)
			//lancio eccezione
			;
		 */
		VigileDelFuocoBean vf = null;
		
		//Ottenimento parametro email dalla richiesta
		String email = request.getParameter("email");;
		
		//Controllo email
		if( email == null )
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
		
			// Ottenimento parametri del VF dalla richiesta
			String nome = request.getParameter("nome");;
			String cognome = request.getParameter("cognome");;
			String turno = request.getParameter("turno");;
			String mansione = request.getParameter("mansione");;
			String username = "turno"/* + ct.getTurno()*/;
			String grado = request.getParameter("grado");
			String giorniFerieAnnoCorrenteStringa = request.getParameter("giorniFerieAnnoCorrente");
			String giorniFerieAnnoPrecedenteStringa = request.getParameter("giorniFerieAnnoPrecedente");
			
			//aggiungere controlli dei parametri
			
			//Controlli
	
			if( nome == null )
				//lancio eccezione
				;
			
			if( cognome == null )
				//lancio eccezione
				;
			
			if( turno == null )
				//lancio eccezione
				;
			
			if( mansione == null )
				//lancio eccezione
				;
			
			if( giorniFerieAnnoCorrenteStringa == null )
				//lancio eccezione
				;
			
			if( giorniFerieAnnoPrecedenteStringa == null )
				//lancio eccezione
				;
			
			if( grado == null )
				//lancio eccezione
				;
			
			Integer giorniFerieAnnoCorrente = Integer.parseInt(giorniFerieAnnoCorrenteStringa); 
			Integer giorniFerieAnnoPrecedente = Integer.parseInt(giorniFerieAnnoPrecedenteStringa); 
			
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
