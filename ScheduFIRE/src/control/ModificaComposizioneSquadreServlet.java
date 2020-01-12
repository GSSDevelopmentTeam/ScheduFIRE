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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		HttpSession sessione = request.getSession();


		try {
			HashMap<VigileDelFuocoBean, String> squadra = new HashMap<>();
			Date data = Date.valueOf(request.getParameter("data"));
			if(request.getParameter("email")!= null) {
				squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadra");
				String oldVF = request.getParameter("email");
				String newVF = request.getParameter("VFNew");
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
			}
			else {
				squadra = Util.ottieniSquadra(data);
			}
			sessione.setAttribute("squadra", squadra);
			request.setAttribute("data", data);
			request.getRequestDispatcher("JSP/SquadraJSP.jsp").forward(request, response);
			return;

		}

		catch(IllegalArgumentException e) {
			String oldVF =request.getParameter("email");
			String newVF =request.getParameter("VFNew");
			int tipo = Integer.parseInt(request.getParameter("tiposquadra"));
			Date data = null;
			Date other = null;
			HashMap<VigileDelFuocoBean, String> squadra = new HashMap<>();
			switch(tipo) {
			case 1: 
				squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadraDiurno");
				data =  Date.valueOf(request.getParameter("dataModifica"));
				other =  Date.valueOf(request.getParameter("altroturno"));
				break;
			case 2:
				squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadraNotturno");
				data =  Date.valueOf(request.getParameter("dataModifica"));
				other =  Date.valueOf(request.getParameter("altroturno"));
				break;
			case 3:
				squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadra");
				data =  Date.valueOf(request.getParameter("dataModifica"));
				break;
			default:
				throw new ScheduFIREException("C'e stato un errore. Riprova pi� tardi.");
			}
			
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
			switch(tipo) {
			case 1: 
				sessione.setAttribute("squadraDiurno", squadra);
				request.setAttribute("dataDiurno", data);
				request.setAttribute("dataNotturno", other);
				request.getRequestDispatcher("JSP/GestioneSquadreJSP.jsp").forward(request, response);
				break;			
			case 2:
				sessione.setAttribute("squadraNotturno", squadra);
				request.setAttribute("dataNotturno", data);
				request.setAttribute("data", other);
				request.getRequestDispatcher("JSP/GestioneSquadreJSP.jsp").forward(request, response);
				break;
			case 3:
				sessione.setAttribute("squadra", squadra);
				request.setAttribute("data", Date.valueOf(request.getParameter("data")));
				request.getRequestDispatcher("JSP/SquadraJSP.jsp").forward(request, response);
				break;
			}
			return;
		}

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
