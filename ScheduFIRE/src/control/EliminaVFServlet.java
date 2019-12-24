package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CredenzialiBean;
import model.dao.VigileDelFuocoDao;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
				
		//Ottenimento credenziali dell'utente dalla sessione
		CredenzialiBean credenziali = (CredenzialiBean) session.getAttribute("credenziali"); 
				
		//Controllo credenziali
		if( credenziali == null )
			throw new ScheduFIREException();

		/*
		if( credenziali.getRuolo() == "vigile" ) //definire bene la stringa
			throw new ScheduFIREException();
			
		*/
		//Ottenimento parametro email dalla richiesta
		String email = request.getParameter("email");
		
		//Controllo email
		if( email == null )
			throw new ScheduFIREException();
		
		if( ! VigileDelFuocoDao.setAdoperabile(email, false))
			throw new ScheduFIREException();
		
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
