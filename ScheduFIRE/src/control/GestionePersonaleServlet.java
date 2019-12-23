package control;

import java.io.IOException;
import java.util.Collection;

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
 * Servlet che si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database.
 * @author Eugenio Sottile 
 */

@WebServlet("/GestionePersonaleServlet")
public class GestionePersonaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionePersonaleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
				
		//Ottenimento credenziali dell'utente dalla sessione
		CredenzialiBean credenziali = (CredenzialiBean) session.getAttribute("credenziali"); 
				
		//Controllo credenziali
		if( credenziali == null )
			//lancio eccezione
			;
		
		if( credenziali.getRuolo() == "vigile" ) //definire bene la stringa
			//lancio eccezione
			;
		*/
		//Ottenimento della collezione di VigiliDelFuoco
		Collection<VigileDelFuocoBean> vigili = VigileDelFuocoDao.ottieni();
		
		//Controllo collezione
		if(vigili == null)
			//lancio eccezione
			;
		
		//Passaggio della collezione di VigiliDelFuoco come attributo
		request.setAttribute("vigili", vigili);
		
		// Reindirizzamento alla jsp
		request.getRequestDispatcher("/JSP/GestionePersonaleJSP.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
