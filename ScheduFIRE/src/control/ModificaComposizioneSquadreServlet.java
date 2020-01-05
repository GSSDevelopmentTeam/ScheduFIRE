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
		String oldVF =request.getParameter("email");
		String newVF =request.getParameter("VFnew");

		if(request.getParameter("salva")!=null) {
			@SuppressWarnings("unchecked")
			HashMap<VigileDelFuocoBean, String> squadraDiurno = (HashMap<VigileDelFuocoBean, String>) 
			sessione.getAttribute("squadraDiurno");
			@SuppressWarnings("unchecked")
			HashMap<VigileDelFuocoBean, String> squadraNotturno = (HashMap<VigileDelFuocoBean, String>) 
			sessione.getAttribute("squadraNotturno");
			List<ComponenteDellaSquadraBean> listaDiurno = vigileToComponente(squadraDiurno, data);	
			List<ComponenteDellaSquadraBean> listaNotturno = vigileToComponente(squadraNotturno, giornoSuccessivo);			

			if((!ListaSquadreDao.aggiungiSquadre(data, (String) sessione.getAttribute("email"))) ||
					(!SquadraDao.aggiungiSquadra(data)) ||
					(!ComponenteDellaSquadraDao.setComponenti(listaDiurno)) ||
					(!VigileDelFuocoDao.caricoLavorativo(squadraDiurno))){
				throw new ScheduFIREException("Errore nelle Query SQL");
			}	
			if((!ListaSquadreDao.aggiungiSquadre(giornoSuccessivo, (String) sessione.getAttribute("email"))) ||
					(!SquadraDao.aggiungiSquadra(giornoSuccessivo)) ||
					(!ComponenteDellaSquadraDao.setComponenti(listaNotturno)) ||
					(!VigileDelFuocoDao.caricoLavorativo(squadraNotturno))){
				throw new ScheduFIREException("Errore nelle Query SQL");
			}	
			SendMail.sendMail(data);
			sessione.removeAttribute("squadraDiurno");
			sessione.removeAttribute("squadraNotturno");
			request.getRequestDispatcher("JSP/HomeCT_JSP.jsp").forward(request, response);


		}



		if(sessione.getAttribute("squadra")!=null) {
			Map<VigileDelFuocoBean, String> squadra = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadra");
			for(VigileDelFuocoBean daModificare : squadra.keySet()) {
				if(daModificare.getEmail().equals(oldVF)) {
					VigileDelFuocoBean daAggiungere = VigileDelFuocoDao.ottieni(newVF);
					String mansione = squadra.remove(daModificare);
					squadra.put(daAggiungere, mansione);
				}
			}
		}
		else {
			Map<VigileDelFuocoBean, String> squadraDiurno = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadraDiurno");
			Map<VigileDelFuocoBean, String> squadraNotturno = (HashMap<VigileDelFuocoBean, String>) sessione.getAttribute("squadraNotturno");
			if(GiornoLavorativo.isDiurno(data)) {
				for(VigileDelFuocoBean daModificare : squadraDiurno.keySet()) {
					if(daModificare.getEmail().equals(oldVF)) {
						VigileDelFuocoBean daAggiungere = VigileDelFuocoDao.ottieni(newVF);
						String mansione = squadraDiurno.remove(daModificare);
						squadraDiurno.put(daAggiungere, mansione);
					}
				}
			}else {
				for(VigileDelFuocoBean daModificare : squadraNotturno.keySet()) {
					if(daModificare.getEmail().equals(oldVF)) {
						VigileDelFuocoBean daAggiungere = VigileDelFuocoDao.ottieni(newVF);
						String mansione = squadraNotturno.remove(daModificare);
						squadraNotturno.put(daAggiungere, mansione);
					}
				}

			}

		}



		request.getRequestDispatcher("JSP/GestioneSquadreJSP.jsp").forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
