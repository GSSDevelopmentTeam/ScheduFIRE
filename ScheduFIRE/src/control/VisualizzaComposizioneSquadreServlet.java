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
import javax.servlet.http.HttpSession;

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
		
		HttpSession sessione = request.getSession();
		HashMap<VigileDelFuocoBean, String> squadra;
		Date data=Date.valueOf(request.getParameter("data"));
		squadra=Util.ottieniSquadra(data);
		System.out.println("squadra: "+squadra);
		sessione.setAttribute("squadra", squadra);
		request.setAttribute("nonSalvata", false);
		request.getRequestDispatcher("JSP/GestioneSquadreJSP.jsp").forward(request, response);
		
		
		/*
			squadra = new HashMap<>();
			squadra.put(new VigileDelFuocoBean("Mario", "Rossi", "mario.rossi", "B", "Autista", "turnob", "Coordinatore", 0, 0), "Prima Partenza");
			squadra.put(new VigileDelFuocoBean("Giuseppe", "Rossi", "giuseppe.rossi", "B", "Vigile", "turnob", "Coordinatore", 0, 0), "Prima Partenza");
			squadra.put(new VigileDelFuocoBean("Luca", "Rossi", "luca.rossi", "B", "Capo Squadra", "turnob", "Esperto", 0, 0), "Prima Partenza");
			squadra.put(new VigileDelFuocoBean("Gennaro", "Rossi", "gennaro.rossi", "B", "Vigile", "turnob", "Qualificato", 0, 0), "Prima Partenza");
			
			squadra.put(new VigileDelFuocoBean("Ciro", "Rossi", "ciro.rossi", "B", "Capo Squadra", "turnob", "Esperto", 0, 0), "Sala Operativa");
			squadra.put(new VigileDelFuocoBean("Giuseppe", "Rossi", "giuseppe.rossi", "B", "Vigile", "turnob", "Coordinatore", 0, 0), "Sala Operativa");
			squadra.put(new VigileDelFuocoBean("Pia", "Rossi", "pia.rossi", "B", "Vigile", "turnob", "Coordinatore", 0, 0), "Sala Operativa");
			
			squadra.put(new VigileDelFuocoBean("Alfredo", "Rossi", "alfredo.rossi", "B", "Autista", "turnob", "Coordinatore", 0, 0), "Auto Scala");
			squadra.put(new VigileDelFuocoBean("Lorenzo", "Rossi", "lorenzo.rossi", "B", "Vigile", "turnob", "Qualificato", 0, 0), "Auto Scala");
			
			squadra.put(new VigileDelFuocoBean("Pasquale", "Rossi", "pasquale.rossi", "B", "Autista", "turnob", "Coordinatore", 0, 0), "Auto Botte");
			squadra.put(new VigileDelFuocoBean("Donna", "Rossi", "donna.rossi", "B", "Capo Squadra", "turnob", "Esperto", 0, 0), "Auto Botte");
		
*/		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}