package control;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CapoTurnoBean;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;
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
		HttpSession sessione = request.getSession();
		Date data=Date.valueOf(request.getParameter("data"));

		if(request.getAttribute("nonSalvata") != null) {
			@SuppressWarnings("unchecked")
			HashMap<VigileDelFuocoBean, String> squadra=(HashMap<VigileDelFuocoBean, String>)sessione.getAttribute("squadra");
			CapoTurnoBean capoturno=(CapoTurnoBean)sessione.getAttribute("capoturno");
			//Se le squadre da salvare sono sul db, le rimuovo dal db restituendo i carichi di lavoro
			if(ListaSquadreDao.isEsistente(data)) {
				ArrayList<ComponenteDellaSquadraBean> componentiDaRimuovere=ComponenteDellaSquadraDao.getComponenti(data);
				HashMap<VigileDelFuocoBean, String> squadraDaRimuovere=Util.ottieniSquadra(data);
				List<ComponenteDellaSquadraBean> lista = vigileToComponente(squadra, data);
				if(!ComponenteDellaSquadraDao.removeComponenti(componentiDaRimuovere)) {
					throw new ScheduFIREException("Errore nelle Query SQL");
				}	
				if(!VigileDelFuocoDao.removeCaricoLavorativo(squadraDaRimuovere)) {
					throw new ScheduFIREException("Errore nelle Query SQL");
				}	


				//
				if((!ComponenteDellaSquadraDao.setComponenti(lista)) ||
						(!VigileDelFuocoDao.caricoLavorativo(squadra))){
					throw new ScheduFIREException("Errore nelle Query SQL");
				}	
			} 
			response.sendRedirect("HomeCTServlet");
			return;
		}
		request.setAttribute("nonSalvata", false);
		request.getRequestDispatcher("JSP/GestioneSquadreJSP.jsp").forward(request, response);	
	}
	
	private List<ComponenteDellaSquadraBean> vigileToComponente(HashMap<VigileDelFuocoBean, String> squadra, Date data) {
		List<ComponenteDellaSquadraBean> toReturn = new ArrayList<>();
		@SuppressWarnings("rawtypes")
		Iterator i = squadra.entrySet().iterator();
		while(i.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<VigileDelFuocoBean, String> coppia = (Entry<VigileDelFuocoBean, String>) i.next();
			toReturn.add(new ComponenteDellaSquadraBean(coppia.getValue(), coppia.getKey().getEmail(), data));
		}
		return toReturn;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}