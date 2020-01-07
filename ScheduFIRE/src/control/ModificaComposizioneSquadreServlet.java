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

import com.sendmail.SendMail;

import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;
import model.dao.VigileDelFuocoDao;
import model.bean.ComponenteDellaSquadraBean;
import util.GiornoLavorativo;

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
		Date data = Date.valueOf(request.getParameter("data"));
		
		if(request.getAttribute("data") != null) {
			String oldVF =request.getParameter("email");
			String newVF =request.getParameter("VFNew");
			int tipo = Integer.parseInt(request.getParameter("tiposquadra"));
			Map<VigileDelFuocoBean, String> squadra = new HashMap<>();
			switch(tipo) {
			case 1: 
				squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadraDiurno");
				break;
			case 2:
				squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadraNotturno");
				break;
			default:
				throw new ScheduFIREException("C'e stato un errore. Riprova pi� tardi.");
			}
			System.out.println("squadra: "+squadra);
			Iterator i = squadra.entrySet().iterator();
			
			while(i.hasNext()) {
				Map.Entry<VigileDelFuocoBean, String> coppia = (Map.Entry<VigileDelFuocoBean, String>) i.next();
				VigileDelFuocoBean oldVigile = coppia.getKey();
				if(oldVigile.getEmail().equals(oldVF)) {
					String mansione = squadra.remove(oldVigile);
					VigileDelFuocoBean newVigile = VigileDelFuocoDao.ottieni(newVF);
					squadra.put(newVigile, mansione);
					break;
				}
			}

			request.getRequestDispatcher("JSP/GestioneSquadreJSP.jsp").forward(request, response);
			return;
		}
		
		else {
			Map<VigileDelFuocoBean, String> squadra = Util.ottieniSquadra(data);
			System.out.println(squadra);
			System.out.println(data);
			sessione.setAttribute("squadra", squadra);
			sessione.setAttribute("data", data);
			request.getRequestDispatcher("JSP/SquadraJSP.jsp").forward(request, response);
		}

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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
