package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.PersonaleDisponibileServlet.ComponenteComparator;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class PersonaleDisponibileAJAX
 */
@WebServlet("/PersonaleDisponibileAJAX")
public class PersonaleDisponibileAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersonaleDisponibileAJAX() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		//Prendo l'email del VF da sostituire, il ruolo e il tipo di squadra
		String email=request.getParameter("email");
		String ruolo= request.getParameter("mansione");
		String tipo = request.getParameter("tiposquadra");
		int tp= Integer.parseInt(tipo);
		
		HttpSession session = request.getSession();
		HashMap<VigileDelFuocoBean, String> squadra=null;
		Date data = null;
		Date other= null;
		if(tp==1) {
		squadra = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadraDiurno");
		data = Date.valueOf( request.getParameter("dataModifica"));
		other= Date.valueOf( request.getParameter("altroturno"));
		} else {
			if(tp==2) {
				squadra = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadraNotturno");
				data = Date.valueOf( request.getParameter("dataModifica"));
				other= Date.valueOf( request.getParameter("altroturno"));
			}else {
				squadra = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadra");
				data = Date.valueOf( request.getParameter("dataModifica"));
			}
		}
		
		ArrayList<VigileDelFuocoBean> vigili=VigileDelFuocoDao.getDisponibili(data);

		//Confronto se nell'ArrayList dei vigili ci sono quelli gi� inseriti nelle squadre 
		ArrayList<VigileDelFuocoBean> nuovoelenco = new ArrayList<VigileDelFuocoBean>();
		for(int i=0; i<vigili.size();i++) {
		boolean trovato= false;
		Iterator it = squadra.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry coppia = (Map.Entry) it.next();
			VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();			
			//confronto il membro nella squadra con tutta la lista di vigili disponibili			
				if(membro.getEmail().equalsIgnoreCase(vigili.get(i).getEmail())) {
					trovato = true;
					
				}	
				if(trovato) break;
			}
			//Se il vigile non � presente nell'HashMap lo inserisco nel nuovo arrayList, controllando se � dello stesso ruolo del VF rimosso
			if(trovato==false) {
				if(ruolo!=null) {
					if(vigili.get(i).getMansione().equalsIgnoreCase(ruolo)) {
						nuovoelenco.add(vigili.get(i));
					}
				}
			}
			
		}
		
		request.setAttribute("tiposquadra",tipo);
		request.setAttribute("vigili", nuovoelenco);
		request.setAttribute("email", email);
		request.setAttribute("dataModifica", data);
		request.setAttribute("altroturno", other);
		request.getRequestDispatcher("JSP/PersonaleDisponibileAJAXJSP.jsp").forward(request, response);

	}


	





}
