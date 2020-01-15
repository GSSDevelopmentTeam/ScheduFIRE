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

import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.Util;
import util.Validazione;

/**
 * Servlet che si occupa della modifica dei VigileDelFuocoBean nel database. 
 * @author Eugenio Sottile 
 */

@WebServlet("/ModificaVFServlet")
public class ModificaVFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaVFServlet() {
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
		String emailVecchia = request.getParameter("emailVecchia");

		//Controllo email
		if( ! Validazione.email(emailVecchia) )
			throw new ParametroInvalidoException("Il parametro 'email' &egrave errato!");
		
		emailVecchia += "@vigilfuoco.it";
		
		//Controllo se il vf scelto � gia schedulato in delle squadre
		LocalDate inizio = LocalDate.now();
		LocalDate fine = inizio.plusDays(7);
		boolean eliminabile = false;
		while( inizio.compareTo(fine) != 0 ) {
			
			eliminabile = eliminabile || ComponenteDellaSquadraDao.isComponente(emailVecchia, Date.valueOf(inizio) );
			
			inizio = inizio.plusDays(1);

			
		}

		if( eliminabile )
			throw new GestionePersonaleException("Non puoi modificare un Vigile del Fuoco scelto per le squadre"
					+ " dei prossimi giorni lavorativi! Sostiuiscilo prima.");
		
		//Ottenimento Vigile del Fuoco dal database
		VigileDelFuocoBean vf = VigileDelFuocoDao.ottieni(emailVecchia);
		
		//Controllo se Ã¨ nullo
		if( vf == null ) 
			throw new GestionePersonaleException("Il vigile del fuoco non &egrave presente nel sistema!");
		
		// Ottenimento parametri del VF dalla richiesta
		String nomeNuovo = request.getParameter("nomeNuovo");;
		String cognomeNuovo = request.getParameter("cognomeNuovo");;
		String mansioneNuova = request.getParameter("mansioneNuova");;
		String gradoNuovo = request.getParameter("gradoNuovo");
		String giorniFerieAnnoCorrenteNuoviStringa = request.getParameter("giorniFerieAnnoCorrenteNuovi");;
		String giorniFerieAnnoPrecedenteNuoviStringa = request.getParameter("giorniFerieAnnoPrecedenteNuovi");
		String emailNuova = request.getParameter("emailNuova");
		
		if(giorniFerieAnnoCorrenteNuoviStringa == null ||
			"".equals(giorniFerieAnnoCorrenteNuoviStringa))
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Corrente' &egrave nullo!");
		
		if(giorniFerieAnnoPrecedenteNuoviStringa == null ||
			"".equals(giorniFerieAnnoPrecedenteNuoviStringa))
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Precedente' &egrave nullo!");
		
		//Conversione parametri da Stringa ad interi
		Integer giorniFerieAnnoCorrenteNuovi = Integer.parseInt(giorniFerieAnnoCorrenteNuoviStringa);
		Integer giorniFerieAnnoPrecedenteNuovi = Integer.parseInt(giorniFerieAnnoPrecedenteNuoviStringa);
	
		//Controlli

		if( ! Validazione.nome(nomeNuovo) )
			throw new ParametroInvalidoException("Il parametro 'nome' &egrave errato!");
		
		if( ! Validazione.cognome(cognomeNuovo) )
			throw new ParametroInvalidoException("Il parametro 'cognome' &egrave errato!");
		
		if( ! Validazione.mansione(mansioneNuova) )
			throw new ParametroInvalidoException("Il parametro 'mansione' &egrave errato!");
		
		if( ! Validazione.giorniFerieAnnoCorrente(giorniFerieAnnoCorrenteNuovi) )
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Corrente' &egrave errato!");
	
		if( ! Validazione.giorniFerieAnniPrecedenti(giorniFerieAnnoPrecedenteNuovi) )
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Precedente' &egrave errato!");
		
		//Se il grado non è settato e la mansione è Capo Squadra, il grado sarà 'Semplice'
		if( mansioneNuova.equals("Capo Squadra") && gradoNuovo == null )
			gradoNuovo = "Semplice";
		
		if( ! Validazione.grado(gradoNuovo) )
			throw new ParametroInvalidoException("Il parametro 'grado' &egrave errato!");
		
		if( ! Validazione.email(emailNuova) )
			throw new ParametroInvalidoException("Il parametro 'email' &egrave errato!");
		
		//Controllo mansione
		if( mansioneNuova.equals("Capo Squadra") && ( gradoNuovo.equals("Qualificato") 
				|| gradoNuovo.equals("Coordinatore") ) ) 
			throw new ParametroInvalidoException("Un Capo Squadra può essere solamente Esperto o Semplice!");
		
		if( (mansioneNuova.equals("Autista") || mansioneNuova.equals("Vigile") )  
				&&  gradoNuovo.equals("Semplice") ) 
			throw new ParametroInvalidoException("Il parametro 'grado'&egrave errato!");
		
		emailNuova += "@vigilfuoco.it";
	
		//Settaggio nuovi parametri
		vf.setNome(nomeNuovo);
		vf.setCognome(cognomeNuovo);
		vf.setMansione(mansioneNuova);
		vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrenteNuovi);
		vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedenteNuovi);
		vf.setGrado(gradoNuovo);
		
		VigileDelFuocoBean vfDb =  VigileDelFuocoDao.ottieni(emailNuova);
		if( ! vf.equals(vfDb) ) {
		
			//Controllo email giÃ  in uso
			if( (vfDb != null) && ( ! emailVecchia.equals(emailNuova) ) )
				throw new GestionePersonaleException("L'email inserita &egrave gi&agrave� in uso!");
			
			vf.setEmail(emailNuova);
			
			// Controllo modifica Vigile del Fuoco nel database
			if( ! VigileDelFuocoDao.modifica(emailVecchia, vf)) 
				throw new GestionePersonaleException("La modifica del vigile del fuoco non &egrave andata a buon fine!");
		
		}
		
		session.setAttribute("risultato", "Modifica del Vigile del Fuoco avvenuta con successo!");
		
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
