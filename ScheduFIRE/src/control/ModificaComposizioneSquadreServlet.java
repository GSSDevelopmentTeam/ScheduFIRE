package control;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;
import model.bean.ComponenteDellaSquadraBean;

import util.Util;

/**
 * Servlet implementation class ModificaComposizioneSquadreServlet
 * @author Emanuele Bombardelli
 */
@WebServlet("/ModificaComposizioneSquadreServlet")
public class ModificaComposizioneSquadreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificaComposizioneSquadreServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessione = request.getSession();
		Date data = (Date) request.getAttribute("data");
		Map<VigileDelFuocoBean, String> squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadra");
		String oldVF = (String) request.getAttribute("email");
		String newVF = (String) request.getAttribute("VFnew");
		for(VigileDelFuocoBean daModificare : squadra.keySet()) {
			if(daModificare.getEmail() == oldVF) {
				VigileDelFuocoBean daAggiungere = VigileDelFuocoDao.ottieni(newVF);
				
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
