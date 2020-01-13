package control;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.VigileDelFuocoBean;

import model.dao.VigileDelFuocoDao;
import util.Util;

/**
 * Servlet che si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database.
 * @author Eugenio Sottile 
 */

@WebServlet("/GestionePersonaleServlet")
public class GestionePersonaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionePersonaleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Controllo login
		Util.isCapoTurno(request);
		
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
		
		//Ottenimento parametri
		String ordinamento = request.getParameter("ordinamento");
		
		//Se il parametro non è settato, l'ordinamento sarà quello di default
		if(ordinamento == null)
			ordinamento = (String) session.getAttribute("ordinamento");
		
		if(ordinamento == null)
			ordinamento = "";
	
		//Ottenimento della collezione di VigiliDelFuoco
		
		Collection<VigileDelFuocoBean> capiSquadra = null;
		Collection<VigileDelFuocoBean> autisti = null;
		Collection<VigileDelFuocoBean> vigili = null;
		
		switch(ordinamento) {
		case "nome": 
			capiSquadra = VigileDelFuocoDao.ottieniCapiSquadra(VigileDelFuocoDao.ORDINA_PER_NOME);
			autisti = VigileDelFuocoDao.ottieniAutisti(VigileDelFuocoDao.ORDINA_PER_NOME);
			vigili = VigileDelFuocoDao.ottieniVigili(VigileDelFuocoDao.ORDINA_PER_NOME);
			break;
		case "cognome": 
			capiSquadra = VigileDelFuocoDao.ottieniCapiSquadra(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			autisti = VigileDelFuocoDao.ottieniAutisti(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			vigili = VigileDelFuocoDao.ottieniVigili(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			break;
		case "caricoLavoro": 
			capiSquadra = VigileDelFuocoDao.ottieniCapiSquadra(VigileDelFuocoDao.ORDINA_PER_CARICO_LAVORO);
			autisti = VigileDelFuocoDao.ottieniAutisti(VigileDelFuocoDao.ORDINA_PER_CARICO_LAVORO);
			vigili = VigileDelFuocoDao.ottieniVigili(VigileDelFuocoDao.ORDINA_PER_CARICO_LAVORO);
			break;
		case "ferie": 
			capiSquadra = VigileDelFuocoDao.ottieniCapiSquadra(VigileDelFuocoDao.ORDINA_PER_FERIE_TOTALI);
			autisti = VigileDelFuocoDao.ottieniAutisti(VigileDelFuocoDao.ORDINA_PER_FERIE_TOTALI);
			vigili = VigileDelFuocoDao.ottieniVigili(VigileDelFuocoDao.ORDINA_PER_FERIE_TOTALI);
			break;
		default:
			capiSquadra = VigileDelFuocoDao.ottieniCapiSquadra(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			autisti = VigileDelFuocoDao.ottieniAutisti(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			vigili = VigileDelFuocoDao.ottieniVigili(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			ordinamento = "cognome";
		}
		
		//Controllo collezione
		if(capiSquadra == null || autisti == null || vigili == null)
			throw new GestionePersonaleException("Non è stato possibile ottenere"
												+ " la lista del personale di Vigili del Fuoco");
		
		//Passaggio delle collezioni di VigiliDelFuoco come attributo
		request.setAttribute("capiSquadra", capiSquadra);
		request.setAttribute("autisti", autisti);
		request.setAttribute("vigili", vigili);
		
		//Passasggio dei tipi di ordinamento ottenuti
		session.setAttribute("ordinamento", ordinamento);
		
		// Reindirizzamento alla jsp
		request.getRequestDispatcher("/JSP/GestionePersonaleJSP.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
