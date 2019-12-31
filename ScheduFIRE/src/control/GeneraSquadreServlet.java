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

import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;
import util.Util;

/**
 * Servlet implementation class GeneraSquadreServlet
 * @author Emanuele Bombardelli
 */
@WebServlet(description = "Servlet per la generazione delle squadre", urlPatterns = { "/GeneraSquadreServlet" })
public class GeneraSquadreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneraSquadreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessione = request.getSession();
		Date data = (Date) request.getAttribute("data");

		if(sessione.getAttribute("credenziali") != null) {
			if(sessione.getAttribute("squadra") != null) {
				@SuppressWarnings("unchecked")
				HashMap<VigileDelFuocoBean, String> squadra = (HashMap<VigileDelFuocoBean, String>) 
						sessione.getAttribute("squadra");
				List<ComponenteDellaSquadraBean> lista = vigileToComponente(squadra, data);
				if((!ComponenteDellaSquadraDao.setComponenti(lista)) ||
						(!SquadraDao.aggiungiSquadra(data)) || 
						(!ListaSquadreDao.aggiungiSquadre(data, (String) sessione.getAttribute("email"))) ){
					throw new ScheduFIREException("Errore nelle Query SQL");
				}	
				sessione.removeAttribute("squadra");
				SendMail.sendMail(data);
			}
			else {
				try {
					List<ComponenteDellaSquadraBean> lista = Util.generaSquadra(data);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("/VisualizzaComposizioneSquadreServlet").forward(request, response);				
				} catch (NotEnoughMembersException e) {

				}
			}
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
			i.remove();
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
