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
				
		//Controllo credenziali
		if( credenziali == null )
			//lancio eccezione
			;
		/*
		if( credenziali.getRuolo() == "vigile" ) //definire bene la stringa
			//lancio eccezione
			;
		*/
		//Ottenimento parametro email dalla richiesta
		String emailVecchia = request.getParameter("emailVecchia");
		
		//Controllo email
		if( emailVecchia == null )
			//lancio eccezione
			;
		
		VigileDelFuocoBean vf = VigileDelFuocoDao.ottieni(emailVecchia);
		
		if( vf == null) 
			//lancio eccezione
			;
		
		// Ottenimento parametri del VF dalla richiesta
		String nomeNuovo = request.getParameter("nomeNuovo");;
		String cognomeNuovo = request.getParameter("cognomeNuovo");;
		String mansioneNuova = request.getParameter("mansioneNuova");;
		String gradoNuovo = request.getParameter("gradoNuovo");
		String giorniFerieAnnoCorrenteNuoviStringa = request.getParameter("giorniFerieAnnoCorrenteNuovi");;
		String giorniFerieAnnoPrecedenteNuoviStringa = request.getParameter("giorniFerieAnnoPrecedenteNuovi");
		String emailNuova = request.getParameter("emailNuova");
		
		//aggiungere controlli dei parametri
		
		//Controlli

		if( nomeNuovo == null )
			//lancio eccezione
			;
		
		if( cognomeNuovo == null )
			//lancio eccezione
			;
		
		if( mansioneNuova == null )
			//lancio eccezione
			;
		
		if( giorniFerieAnnoCorrenteNuoviStringa == null )
			//lancio eccezione
			;
	
		if( giorniFerieAnnoPrecedenteNuoviStringa == null )
			//lancio eccezione
			;
		
		if( gradoNuovo == null )
			//lancio eccezione
			;
		
		if( emailNuova == null )
			//lancio eccezione
			;
		
		Integer giorniFerieAnnoCorrenteNuovi = Integer.parseInt(giorniFerieAnnoCorrenteNuoviStringa);
		Integer giorniFerieAnnoPrecedenteNuovi = Integer.parseInt(giorniFerieAnnoPrecedenteNuoviStringa);
		
		//Settaggio nuovi parametri
		vf.setNome(nomeNuovo);
		vf.setCognome(cognomeNuovo);
		vf.setMansione(mansioneNuova);
		vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrenteNuovi);
		vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedenteNuovi);
		vf.setGrado(gradoNuovo);
		vf.setEmail(emailNuova);
		
		if( ! VigileDelFuocoDao.modifica(emailVecchia, vf)) {
			//lancio eccezione
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
