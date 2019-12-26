package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CredenzialiBean;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;
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

		*/
		//Ottenimento parametro email dalla richiesta
		String emailVecchia = request.getParameter("emailVecchia");
		
		//Controllo email
		if( ! Validazione.email(emailVecchia) )
			throw new ParametroInvalidoException("Il parametro 'email' � errato!");
		
		//Ottenimento Vigile del Fuoco dal database
		VigileDelFuocoBean vf = VigileDelFuocoDao.ottieni(emailVecchia);
		
		//Controllo se � nullo
		if( vf == null ) 
			throw new GestionePersonaleException("Il vigile del fuoco non � presente nel sistema!");
		
		// Ottenimento parametri del VF dalla richiesta
		String nomeNuovo = request.getParameter("nomeNuovo");;
		String cognomeNuovo = request.getParameter("cognomeNuovo");;
		String mansioneNuova = request.getParameter("mansioneNuova");;
		String gradoNuovo = request.getParameter("gradoNuovo");
		String giorniFerieAnnoCorrenteNuoviStringa = request.getParameter("giorniFerieAnnoCorrenteNuovi");;
		String giorniFerieAnnoPrecedenteNuoviStringa = request.getParameter("giorniFerieAnnoPrecedenteNuovi");
		String emailNuova = request.getParameter("emailNuova");
		
		if(giorniFerieAnnoCorrenteNuoviStringa == null)
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Corrente' � nullo!");
		
		if(giorniFerieAnnoPrecedenteNuoviStringa == null)
			throw new ScheduFIREException("Il parametro 'Giorni Ferie Anno Precedente' � nullo!");
		
		//Conversione parametri da Stringa ad interi
		Integer giorniFerieAnnoCorrenteNuovi = Integer.parseInt(giorniFerieAnnoCorrenteNuoviStringa);
		Integer giorniFerieAnnoPrecedenteNuovi = Integer.parseInt(giorniFerieAnnoPrecedenteNuoviStringa);
	
		//Controlli

		if( ! Validazione.nome(nomeNuovo) )
			throw new ParametroInvalidoException("Il parametro 'nome' � errato!");
		
		if( ! Validazione.cognome(cognomeNuovo) )
			throw new ParametroInvalidoException("Il parametro 'cognome' � errato!");
		
		if( ! Validazione.mansione(mansioneNuova) )
			throw new ParametroInvalidoException("Il parametro 'mansione' � errato!");
		
		if( ! Validazione.giorniFerieAnnoCorrente(giorniFerieAnnoCorrenteNuovi) )
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Corrente' � errato!");
	
		if( ! Validazione.giorniFerieAnniPrecedenti(giorniFerieAnnoPrecedenteNuovi) )
			throw new ParametroInvalidoException("Il parametro 'Giorni Ferie Anno Precedente' � errato!");
		
		if( ! Validazione.grado(gradoNuovo) )
			throw new ParametroInvalidoException("Il parametro 'grado' � errato!");
		
		if( ! Validazione.email(emailNuova) )
			throw new ParametroInvalidoException("Il parametro 'email' � errato!");
		
		if( VigileDelFuocoDao.ottieni(emailNuova) != null )
			throw new GestionePersonaleException("L'email inserita � gi� in uso!");
		
		//Settaggio nuovi parametri
		vf.setNome(nomeNuovo);
		vf.setCognome(cognomeNuovo);
		vf.setMansione(mansioneNuova);
		vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrenteNuovi);
		vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedenteNuovi);
		vf.setGrado(gradoNuovo);
		vf.setEmail(emailNuova);
		
		// Controllo modifica Vigile del Fuoco nel database
		if( ! VigileDelFuocoDao.modifica(emailVecchia, vf)) {
			throw new GestionePersonaleException("La modifica del vigile del fuoco non � andata a buon fine!");
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
