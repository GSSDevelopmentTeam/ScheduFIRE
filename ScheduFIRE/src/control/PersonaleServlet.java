package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class PersonaleServlet
 */
@WebServlet("/PersonaleServlet")
public class PersonaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonaleServlet() {
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

		HttpSession session = request.getSession();
		
		 HashMap<VigileDelFuocoBean, String> squadra1 = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadraDiurno");	
		 HashMap<VigileDelFuocoBean, String> squadra2 = (HashMap<VigileDelFuocoBean, String>) session.getAttribute("squadraNotturno");

			

			Date giorno = Date.valueOf(request.getParameter("dataDiurno"));
			Date datanotte = Date.valueOf(request.getParameter("dataNotturno"));
			
		//Confronto se nell'ArrayList dei vigili ci sono quelli gi� inseriti nelle squadre 
		ArrayList<VigileDelFuocoBean> giorno1 = new ArrayList<VigileDelFuocoBean>();
		ArrayList<VigileDelFuocoBean> notte = new ArrayList<VigileDelFuocoBean>();
		
		ArrayList<VigileDelFuocoBean> vigili=VigileDelFuocoDao.getDisponibili(giorno);
		ArrayList<VigileDelFuocoBean> vigilinotte =VigileDelFuocoDao.getDisponibili(datanotte);
		
		for(int i=0; i<vigili.size();i++) {
		boolean trovato= false;
		Iterator it = squadra1.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry coppia = (Map.Entry) it.next();
			VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();			
			//confronto il membro nella squadra con tutta la lista di vigili disponibili			
				if(membro.getEmail().equalsIgnoreCase(vigili.get(i).getEmail())) {
					trovato = true;
				}	
				if(trovato) break;
			}
			if(trovato==false) {
				giorno1.add(vigili.get(i));				
			}
			
		}
		
		for(int i=0; i<vigilinotte.size();i++) {
			boolean trovato= false;
			Iterator it = squadra2.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry coppia = (Map.Entry) it.next();
				VigileDelFuocoBean membro = (VigileDelFuocoBean) coppia.getKey();			
				//confronto il membro nella squadra con tutta la lista di vigili disponibili			
					if(membro.getEmail().equalsIgnoreCase(vigilinotte.get(i).getEmail())) {
						trovato = true;
					}	
					if(trovato) break;
				}
				if(trovato==false) {
					notte.add(vigili.get(i));				
				}
				
			}
		

		request.setAttribute("giorno", giorno1);
		request.setAttribute("notte", notte);
		request.getRequestDispatcher("JSP/PersonaleJSP.jsp").forward(request, response);

	}


}
