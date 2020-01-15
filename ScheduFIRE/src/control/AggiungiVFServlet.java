package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;

import model.dao.VigileDelFuocoDao;
import util.Util;
import util.Validazione;

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Controllo login
		Util.isCapoTurno(request);
		
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
	
		//Ottengo i dati del Capo Turno dalla sessione
		CapoTurnoBean ct = (CapoTurnoBean) session.getAttribute("capoturno");
		
		//Ottenimento parametro email dalla richiesta  
		String email = request.getParameter("email");

		
		//Controllo email
		if( ! Validazione.email(email) )
			throw new ParametroInvalidoException("Il parametro 'email' &egrave; errato! <br>"
					+ "L'email deve essere del formato: <br>"
					+ "nome.cognome@vigilfuoco.it <br>"
					+ "o eventualmente con un numero: <br>"
					+ "nome1.cognome@vigilfuoco.it <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");

		// Ottenimento parametri del VF dalla richiesta
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String turno = ct.getTurno();
		String mansione = request.getParameter("mansione");
		String username = "turno" + ct.getTurno();
		String grado = request.getParameter("grado");
		String giorniFerieAnnoCorrenteStringa = request.getParameter("giorniFerieAnnoCorrente");
		String giorniFerieAnnoPrecedenteStringa = request.getParameter("giorniFerieAnnoPrecedente");
		
		if(giorniFerieAnnoCorrenteStringa == null || "".equals(giorniFerieAnnoCorrenteStringa))
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Corrente' &egrave; nullo! <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale.");
		
		if(giorniFerieAnnoPrecedenteStringa == null || "".equals(giorniFerieAnnoPrecedenteStringa))
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Precedente' &egrave; nullo! <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale.");
		
		//Conversione parametri da Stringa ad interi
		Integer giorniFerieAnnoCorrente = Integer.parseInt(giorniFerieAnnoCorrenteStringa); 
		Integer giorniFerieAnnoPrecedente = Integer.parseInt(giorniFerieAnnoPrecedenteStringa);
	
		//Controlli
		if( ! Validazione.nome(nome) )
			throw new ParametroInvalidoException("Il parametro 'nome' &egrave; errato! <br>"
					+ "Verifica che il nome: <br>"
					+ "sia formato da sole lettere; <br>"
					+ "non contenga numeri o caratteri speciali; <br>"
					+ "abbia l'iniziale maiuscola; <br>"
					+ "non superi i 20 caratteri. <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		if( ! Validazione.cognome(cognome) )
			throw new ParametroInvalidoException("Il parametro 'cognome' &egrave; errato! <br>"
					+ "Verifica che il cognome: <br>"
					+ "sia formato da sole lettere; <br>"
					+ "non contenga numeri o caratteri speciali; <br>"
					+ "abbia l'iniziale maiuscola; <br>"
					+ "non superi i 20 caratteri. <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		if( ! Validazione.turno(turno) )
			throw new ParametroInvalidoException("Il parametro 'turno' &egrave; errato! <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		if( ! Validazione.mansione(mansione) )
			throw new ParametroInvalidoException("Il parametro 'mansione' &egrave; errato! <br>"
					+ "Le mansioni che puoi inserire sono: <br>"
					+ "Capo Squadra; <br>"
					+ "Autista; <br>"
					+ "Vigile. <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		//Se il grado non Ã¨ settato e la mansione Ã¨ Capo Squadra, il grado sarÃ  'Semplice'
		if( mansione.equals("Capo Squadra") && (grado == null || grado.equals(" ")) )
			grado = "Semplice";
		
		if( ! Validazione.grado(grado) )
			throw new ParametroInvalidoException("Il parametro 'grado' &egrave; errato! <br>"
					+ "I gradi che puoi inserire sono: <br>"
					+ "Esperto; <br>"
					+ "Qualificato; <br>"
					+ "Coordinatore. <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		//Controllo mansione
		if( mansione.equals("Capo Squadra") && ( grado.equals("Qualificato") 
				|| grado.equals("Coordinatore") ) ) 
			throw new ParametroInvalidoException("Un Capo Squadra pu&ograve; essere solamente di grado Esperto o Semplice! <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		if( (mansione.equals("Autista") || mansione.equals("Vigile") )  
				&&  grado.equals("Semplice") ) 
			throw new ParametroInvalidoException("Un Vigile o un Autista non pu&ograve; essere di grado Semplice! <br>"
					+ "Verrai reindirizzato alla pagina di Gestione Personale...");
		
		email += "@vigilfuoco.it";
			
		// Instanziazione dell'oggetto VigileDelFuocoBean
		VigileDelFuocoBean vf = new VigileDelFuocoBean(nome, cognome, email, turno, mansione, username, grado,
														giorniFerieAnnoCorrente, giorniFerieAnnoPrecedente);
		
		//Settaggio carico di lavoro
		int caricoLavoro = VigileDelFuocoDao.getCaricoLavoroMinimo();
		vf.setCaricoLavoro(caricoLavoro);
		
		//Controllo se il Vigile del Fuoco Ã¨ giÃ  presente nel database
		VigileDelFuocoBean vigileDb = null;
		if((vigileDb = VigileDelFuocoDao.ottieni(email)) != null) {
			
			//Se il Vigile del Fuoco Ã¨ giÃ  presente nel database ed Ã¨ adoperabile si lancia l'eccezione
			if(vigileDb.isAdoperabile()) {
				throw new GestionePersonaleException("Il vigile del fuoco &egrave; gi&agrave; presente nel sistema! <br>"
						+ "Verrai reindirizzato alla pagina di Gestione Personale...");
				
			} else {
				
				//Si effettua l'aggiornamento dei dati nel database
				if( ! VigileDelFuocoDao.modifica(email, vf)) 
					throw new GestionePersonaleException("L'inserimento del vigile del fuoco non &egrave; andato a buon fine! <br>"
							+ "Verrai reindirizzato alla pagina di Gestione Personale...");
				
				if( ! VigileDelFuocoDao.setAdoperabile(email, true)) 
					throw new GestionePersonaleException("L'inserimento del vigile del fuoco non &egrave; andato a buon fine! <br>"
							+ "Verrai reindirizzato alla pagina di Gestione Personale...");
				
			}
			
		} else {
			
			// Controllo salvataggio Vigile del Fuoco nel database
			if(! VigileDelFuocoDao.salva(vf))
				throw new GestionePersonaleException("L'inserimento del vigile del fuoco non &egrave; andato a buon fine! <br>"
						+ "Verrai reindirizzato alla pagina di Gestione Personale...");

		}
		
		session.setAttribute("risultato", "L'inserimento del Vigile del Fuoco &egrave; avvenuto con successo!");

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
