package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Notifica;
import util.Notifiche;
import util.Util;

/**
 * Servlet implementation class RimuoviNotificheServlet
 */
@WebServlet("/RimuoviNotificheServlet")
public class RimuoviNotificheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviNotificheServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		HttpSession sessione = request.getSession();
		Notifiche notifiche = (Notifiche) sessione.getAttribute("notifiche");
		ArrayList<Notifica> nn = (ArrayList<Notifica>) notifiche.getListaNotifiche();
		String indice = (String) request.getParameter("indice");
		int id = Integer.parseInt(indice);
		for (Notifica notifica:nn) {
			if(notifica.getId() == id) {
				notifiche.rimuovi(notifica);
				break;
			}
		}
		sessione.setAttribute("notifiche",notifiche);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
