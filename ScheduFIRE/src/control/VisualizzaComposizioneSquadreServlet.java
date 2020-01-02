package control;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.Util;

/**
 * Servlet implementation class VisualizzaComposizioneSquadreServlet
 */
@WebServlet(description = "Servlet per la visualizzazione di una squadra esistente", urlPatterns = { "/VisualizzaComposizioneSquadreServlet" })
public class VisualizzaComposizioneSquadreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VisualizzaComposizioneSquadreServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		HashMap<VigileDelFuocoBean, String> squadra;
		if(request.getParameter("lista") == null) {
			Date data = (Date) request.getAttribute("data");
			squadra = Util.ottieniSquadra(data);
			request.setAttribute("nonSalvata", false);
		}
		else {
			squadra = (HashMap<VigileDelFuocoBean, String>) request.getAttribute("lista");
			request.setAttribute("nonSalvata", true);
		}
		request.setAttribute("squadra", squadra);
		request.getRequestDispatcher("JSP/GestioneSquadreJSP").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}