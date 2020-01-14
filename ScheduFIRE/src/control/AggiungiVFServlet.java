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
			throw new ParametroInvalidoException("Il parametro 'email' è errato!");

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
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Corrente' è nullo!");
		
		if(giorniFerieAnnoPrecedenteStringa == null || "".equals(giorniFerieAnnoPrecedenteStringa))
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Precedente' è nullo!");
		
		//Conversione parametri da Stringa ad interi
		Integer giorniFerieAnnoCorrente = Integer.parseInt(giorniFerieAnnoCorrenteStringa); 
		Integer giorniFerieAnnoPrecedente = Integer.parseInt(giorniFerieAnnoPrecedenteStringa);
	
		//Controlli
		if( ! Validazione.nome(nome) )
			throw new ParametroInvalidoException("Il parametro 'nome' è errato!");
		
		if( ! Validazione.cognome(cognome) )
			throw new ParametroInvalidoException("Il parametro 'cognome' è errato!");
		
		if( ! Validazione.turno(turno) )
			throw new ParametroInvalidoException("Il parametro 'turno' è errato!");
		
		if( ! Validazione.mansione(mansione) )
			throw new ParametroInvalidoException("Il parametro 'mansione' è errato!");
		
		//Se il grado non è settato e la mansione è Capo Squadra, il grado sarà 'Semplice'
		if( mansione.equals("Capo Squadra") && (grado == null || grado.equals(" ")) )
			grado = "Semplice";
		
		if( ! Validazione.grado(grado) )
			throw new ParametroInvalidoException("Il parametro 'grado' è errato!");
		
		//Controllo mansione
		if( mansione.equals("Capo Squadra") && ( grado.equals("Qualificato") 
				|| grado.equals("Coordinatore") ) ) 
			throw new ParametroInvalidoException("Un Capo Squadra può essere solamente Esperto o Semplice!");
		
		if( (mansione.equals("Autista") || mansione.equals("Vigile") )  
				&&  grado.equals("Semplice") ) 
			throw new ParametroInvalidoException("Il parametro 'grado' è errato!");
		
		email += "@vigilfuoco.it";
			
		// Instanziazione dell'oggetto VigileDelFuocoBean
		VigileDelFuocoBean vf = new VigileDelFuocoBean(nome, cognome, email, turno, mansione, username, grado,
														giorniFerieAnnoCorrente, giorniFerieAnnoPrecedente);
		
		//Settaggio carico di lavoro
		int caricoLavoro = VigileDelFuocoDao.getCaricoLavoroMinimo();
		vf.setCaricoLavoro(caricoLavoro);
		
		//Controllo se il Vigile del Fuoco è già presente nel database
		VigileDelFuocoBean vigileDb = null;
		if((vigileDb = VigileDelFuocoDao.ottieni(email)) != null) {
			
			//Se il Vigile del Fuoco è già presente nel database ed è adoperabile si lancia l'eccezione
			if(vigileDb.isAdoperabile()) {
				throw new GestionePersonaleException("Il vigile del fuoco è già presente nel sistema!");
				
			} else {
				
				//Si effettua l'aggiornamento dei dati nel database
				if( ! VigileDelFuocoDao.modifica(email, vf)) 
					throw new GestionePersonaleException("L'inserimento del vigile del fuoco non è andato a buon fine!");
				
				if( ! VigileDelFuocoDao.setAdoperabile(email, true)) 
					throw new GestionePersonaleException("L'inserimento del vigile del fuoco non è andato a buon fine!");
				
			}
			
		} else {
			
			// Controllo salvataggio Vigile del Fuoco nel database
			if(! VigileDelFuocoDao.salva(vf))
				throw new GestionePersonaleException("L'inserimento del vigile del fuoco non è andato a buon fine!");

		}
		
		session.setAttribute("risultato", "L'inserimento del Vigile del Fuoco è avvenuto con successo!");

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
