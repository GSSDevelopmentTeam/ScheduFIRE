package control;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.ComponenteDellaSquadraBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;
import util.Util;

/**
 * Servlet implementation class GeneraSquadreServlet
 */
@WebServlet(description = "Servlet per la generazione delle squadre", urlPatterns = { "/GeneraSquadreServlet" })
public class GeneraSquadreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneraSquadreServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessione = request.getSession();
		
		if(sessione.getAttribute("credenziali") != null) {
			Date data = (Date) request.getAttribute("data");
			try {
				List<ComponenteDellaSquadraBean> lista = Util.generaSquadra(data);
				
				if((!ComponenteDellaSquadraDao.setComponenti(lista)) ||
						(!SquadraDao.aggiungiSquadra(data)) || 
						(!ListaSquadreDao.aggiungiSquadre(data, (String) sessione.getAttribute("email"))) ){
					//exception
				}					
			} catch (NotEnoughMembersException e) {
				
			}
		}
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
