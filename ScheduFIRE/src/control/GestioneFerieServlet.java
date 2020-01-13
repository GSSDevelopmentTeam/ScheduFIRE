package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class GestioneFerieServlet
 * 
 * @author Nicola Labanca
 * @author Alfredo Giuliano
 */
@WebServlet("/GestioneFerieServlet")
public class GestioneFerieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestioneFerieServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		//nel caso di chiamata AJAX che gestisce il datepicker per aggiungere ferie ad un vigile
		if(request.getParameter("JSON")!=null && request.getParameter("aggiunta")!=null ) {
			String email=request.getParameter("email");
			
			//giorni di malattia gi� concesse
			List<GiorniMalattiaBean> giorniMalattia = GiorniMalattiaDao.ottieniMalattie(email);
			//giorni di ferie gi� concesse
			List<FerieBean> ferie=FerieDao.ottieniFerieConcesse(email);
			
			
			JSONArray array = new JSONArray();

			
			LocalDate dataOra=LocalDate.now();
			//array che contiene le date da rimuovere
			int annoInizio=dataOra.getYear();
			int annoFine=dataOra.getYear()+10;
			while(annoInizio<annoFine ) {
				Date data=Date.valueOf(dataOra);
				if(!GiornoLavorativo.isLavorativo(data)) {
					array.put(data);
				}
				dataOra=dataOra.plusDays(1);
				annoInizio=dataOra.getYear();
				
			}
			
			
			
			
			//inserisco nell'array i giorni di ferie gi� concesse 
			for(FerieBean ferieBean:ferie) {

				JSONArray arrayrange = new JSONArray();
				arrayrange.put(ferieBean.getDataInizio());
				arrayrange.put(ferieBean.getDataFine().toLocalDate().plusDays(1));
				array.put(arrayrange);
			}
			//inserisco nell'array i giorni di malattia gi� concesse 
			for(GiorniMalattiaBean giorniMalattiaBean:giorniMalattia) {
		
				JSONArray arrayrange = new JSONArray();
				arrayrange.put(giorniMalattiaBean.getDataInizio());
				arrayrange.put(giorniMalattiaBean.getDataFine().toLocalDate().plusDays(1));
				array.put(arrayrange);
			}
			response.setContentType("application/json");
			response.getWriter().append(array.toString());
				}		
		//nel caso di chiamata AJAX che fa visualizzare la lista delle ferie di un vigile per poterne selezionare una da rimuovere
		else if(request.getParameter("JSON")!=null && request.getParameter("rimozione")!=null ) {
			String email=request.getParameter("email");
			List<FerieBean> ferie=FerieDao.ottieniFerieConcesse(email);
			JSONArray array = new JSONArray();
			JSONArray arrayrange = new JSONArray();
			arrayrange.put(new Date(System.currentTimeMillis()));
			Date datafinale=null;
			for(FerieBean ferieBean:ferie) {

				arrayrange.put(ferieBean.getDataInizio());
				array.put(arrayrange);
				arrayrange=new JSONArray();
				arrayrange.put(ferieBean.getDataFine().toLocalDate().plusDays(1));
				datafinale=ferieBean.getDataFine();
			}
			if(datafinale!=null) 
			arrayrange.put(datafinale.toLocalDate().plusDays(1));
			else arrayrange.put(LocalDate.now().plusDays(1));

			array.put(arrayrange);
			
			response.setContentType("application/json");
			response.getWriter().append(array.toString());
		}
		else {
			
			//Ottenimento parametro
			String ordinamento = request.getParameter("ordinamento");
			
			//Se il parametro non � settato, l'ordinamento sarà quello di default
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
			case "mansione": 
				vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_MANSIONE);
				break;
			case "grado": 
				vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_GRADO);
				break;
			case "giorniFerie": 
				vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_FERIE_TOTALI);
				break;
			case "giorniFerieAnnoPrecedente": 
				vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_GIORNI_FERIE_ANNI_PRECEDENTI);
				break;
			default:
				vigili = VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_COGNOME);
				ordinamento = "cognome";
			}
			
			
			List<VigileDelFuocoBean> listaVigili = new ArrayList<VigileDelFuocoBean>(vigili);
			
			//Passasggio del tipo di ordinamento ottenuto
			request.setAttribute("ordinamento", ordinamento);
			request.setAttribute("listaVigili", listaVigili);
			request.getRequestDispatcher("JSP/GestioneFerieJSP.jsp").forward(request, response);
		}
	}


}
