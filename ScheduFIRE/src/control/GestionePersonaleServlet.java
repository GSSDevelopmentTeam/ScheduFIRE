package control;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CredenzialiBean;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Util.isCapoTurno(request);
		/*
		//Ottenimento oggetto sessione dalla richiesta
		HttpSession session = request.getSession();
				
		//Ottenimento credenziali dell'utente dalla sessione
		CredenzialiBean credenziali = (CredenzialiBean) session.getAttribute("credenziali"); 
				
		//Controllo credenziali
		if( credenziali == null )
			//lancio eccezione
			;
		
		if( credenziali.getRuolo() == "vigile" ) //definire bene la stringa
			//lancio eccezione
			;
		*/
		
		//Ottenimento parametro
		String ordinamento = request.getParameter("ordinamento");
		
		//Se il parametro non è settato, l'ordinamento sarà quello di default
		if(ordinamento == null)
			ordinamento = "";
		
		//Ottenimento della collezione di VigiliDelFuoco
		
		Collection<VigileDelFuocoBean> vigili = null;
		
		switch(ordinamento) {
		case "nome": 
			vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_NOME);
			break;
		case "cognome": 
			vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			break;
		case "caricoLavoro": 
			vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_CARICO_LAVORO);
			break;
		case "giorniFerieAnnoCorrente": 
			vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_GIORNI_FERIE_ANNO_CORRENTE);
			break;
		case "giorniFerieAnnoPrecedente": 
			vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_GIORNI_FERIE_ANNI_PRECEDENTI);
			break;
		default:
			vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_COGNOME);
			ordinamento = "cognome";
		}
		
		//Controllo collezione
		if(vigili == null)
			throw new GestionePersonaleException("Non è stato possibile ottenere"
												+ " la lista di Vigile del Fuoco");
		
		//Passaggio della collezione di VigiliDelFuoco come attributo
		request.setAttribute("vigili", vigili);
		
		//Passasggio del tipo di ordinamento ottenuto
		request.setAttribute("ordinamento", ordinamento);
		
		// Reindirizzamento alla jsp
		request.getRequestDispatcher("/JSP/GestionePersonaleJSP.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
