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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
		
		//Ottenimento credenziali dell'utente dalla sessione
		CredenzialiBean credenziali = (CredenzialiBean) session.getAttribute("credenziali"); 
		/*
		//Controllo credenziali
		if( credenziali == null )
			throw new ScheduFIREException();
		
		
		if( credenziali.getRuolo() == "vigile" ) //definire bene la stringa
			throw new ScheduFIREException();
		
		//Ottenimento dati del CapoTurno
		CapoTurnoBean ct = CapoTurnoDao.ottieni(credenziali.getUsername());
		
		//Controllo CapoTurno
		if(ct == null)
			throw new ScheduFIREException();

		 */
		
		//Ottenimento parametro email dalla richiesta
		String email = request.getParameter("email");;
		
		//Controllo email
		if( ! Validazione.email(email) )
			throw new ParametroInvalidoException("Il parametro 'email' � errato!");

		// Ottenimento parametri del VF dalla richiesta
		String nome = request.getParameter("nome");;
		String cognome = request.getParameter("cognome");;
		String turno = /*ct.getTurno()*/ "B";
		String mansione = request.getParameter("mansione");;
		String username = "turno"/* + ct.getTurno()*/;
		String grado = request.getParameter("grado");
		String giorniFerieAnnoCorrenteStringa = request.getParameter("giorniFerieAnnoCorrente");
		String giorniFerieAnnoPrecedenteStringa = request.getParameter("giorniFerieAnnoPrecedente");
		
		if(giorniFerieAnnoCorrenteStringa == null || "".equals(giorniFerieAnnoCorrenteStringa))
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Corrente' � nullo!");
		
		if(giorniFerieAnnoPrecedenteStringa == null || "".equals(giorniFerieAnnoPrecedenteStringa))
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Precedente' � nullo!");
		
		//Conversione parametri da Stringa ad interi
		Integer giorniFerieAnnoCorrente = Integer.parseInt(giorniFerieAnnoCorrenteStringa); 
		Integer giorniFerieAnnoPrecedente = Integer.parseInt(giorniFerieAnnoPrecedenteStringa);

		//Controlli
		if( ! Validazione.nome(nome) )
			throw new ParametroInvalidoException("Il parametro 'nome' � errato!");
		
		if( ! Validazione.cognome(cognome) )
			throw new ParametroInvalidoException("Il parametro 'cognome' � errato!");
		
		if( ! Validazione.turno(turno) )
			throw new ParametroInvalidoException("Il parametro 'turno' � errato!");
		
		if( ! Validazione.mansione(mansione) )
			throw new ParametroInvalidoException("Il parametro 'mansione' � errato!");
		
		if( ! Validazione.giorniFerieAnnoCorrente(giorniFerieAnnoCorrente) )
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Corrente' � errato!");
		
		if( ! Validazione.giorniFerieAnniPrecedenti(giorniFerieAnnoPrecedente) )
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Precedente' � errato!");
		
		if( ! Validazione.grado(grado) )
			throw new ParametroInvalidoException("Il parametro 'grado' � errato!");

		// Instanziazione dell'oggetto VigileDelFuocoBean
		VigileDelFuocoBean vf = new VigileDelFuocoBean(nome, cognome, email, turno, mansione, username, grado,
														giorniFerieAnnoCorrente, giorniFerieAnnoPrecedente);
		
		//Controllo se il Vigile del Fuoco � gi� presente nel database
		VigileDelFuocoBean vigileDb = null;
		if((vigileDb = VigileDelFuocoDao.ottieni(email)) != null) {
			
			//Se il Vigile del Fuoco � gi� presente nel database ed � adoperabile si lancia l'eccezione
			if(vigileDb.isAdoperabile()) {
				
				throw new GestionePersonaleException("Il vigile del fuoco � gi� presente nel sistema!");
				
			} else {
				
				//Si effettua l'aggiornamento dei dati nel database
				if( ! VigileDelFuocoDao.modifica(email, vf)) 
					throw new GestionePersonaleException("L'inserimento del vigile del fuoco non � andato a buon fine!");
				
				if( ! VigileDelFuocoDao.setAdoperabile(email, true)) 
					throw new GestionePersonaleException("L'inserimento del vigile del fuoco non � andato a buon fine!");
				
			}
			
		} else {
			
			// Controllo salvataggio Vigile del Fuoco nel database
			if(! VigileDelFuocoDao.salva(vf))
				throw new GestionePersonaleException("L'inserimento del vigile del fuoco non � andato a buon fine!");

		}

		// Reindirizzamento alla jsp
		request.getRequestDispatcher("/GestionePersonaleServlet").forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
