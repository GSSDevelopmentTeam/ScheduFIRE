package control;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import model.bean.CapoTurnoBean;
import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/*
* Servlet implementation class PeriodiDiMalattiaServlet
* @author Biagio Bruno
*/
@WebServlet("/PeriodiDiMalattiaServlet")
public class PeriodiDiMalattiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PeriodiDiMalattiaServlet() {
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
		//ottiene i giorni di malattia per un determinato VF
		if(request.getParameter("JSON")!=null && request.getParameter("visMalattia")!=null ) {
			String emailVF = request.getParameter("emailVF");
			
			if( emailVF == null )
				throw new ScheduFIREException();
					//giorni di malattia gi� concesse
					List<GiorniMalattiaBean> giorniMalattia = GiorniMalattiaDao.ottieniMalattie(emailVF);
					//giorni di ferie gi� concesse
					List<FerieBean> ferie=FerieDao.ottieniFerieConcesse(emailVF);
					
					
					JSONArray array = new JSONArray();
					
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
		//Chiamata AJAX per visualizzare la lista delle malattie di un vigile da rimuovere
		else if(request.getParameter("JSON")!=null && request.getParameter("rimozione")!=null ) {
			String emailVF = request.getParameter("emailVF");
			
			List<GiorniMalattiaBean> giorniMalattia = GiorniMalattiaDao.ottieniMalattie(emailVF);
			
			JSONArray array = new JSONArray();
			JSONArray arrayrange = new JSONArray();
			
			arrayrange.put(new Date(System.currentTimeMillis()));
			Date datafinale=null;

			for(GiorniMalattiaBean giorniMalattiaBean:giorniMalattia) {

				arrayrange.put(giorniMalattiaBean.getDataInizio());
				array.put(arrayrange);
				arrayrange=new JSONArray();
				arrayrange.put(giorniMalattiaBean.getDataFine().toLocalDate().plusDays(1));
				datafinale=giorniMalattiaBean.getDataFine();
			}
			if(datafinale!=null) 
			arrayrange.put(datafinale.toLocalDate().plusDays(1));
			else arrayrange.put(LocalDate.now().plusDays(1));

			array.put(arrayrange);
			
			response.setContentType("application/json");
			response.getWriter().append(array.toString());
		}else {
			
			//Ottenimento parametro
			String ordinamento = request.getParameter("ordinamento");
			
			//Se il parametro non � settato, l'ordinamento sar� quello di default
			if(ordinamento == null)
				ordinamento = "cognome";
			
			//ottiene la lista dei VF dal DataBase
			ArrayList<VigileDelFuocoBean> listaVigili = null;
			
			switch(ordinamento) {
			case "nome": 
				listaVigili =  new ArrayList<VigileDelFuocoBean>(VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_NOME));
				break;
			case "cognome": 
				listaVigili =  new ArrayList<VigileDelFuocoBean>(VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_COGNOME));
				break;
			case "grado": 
				listaVigili =  new ArrayList<VigileDelFuocoBean>(VigileDelFuocoDao.ottieni(VigileDelFuocoDao.ORDINA_PER_GRADO));
				break;
			}

		//Passasggio del tipo di ordinamento richiesto
		request.setAttribute("ordinamento", ordinamento);
		request.setAttribute("listaVigili", listaVigili);
		request.getRequestDispatcher("/JSP/GestioneMalattiaJSP.jsp").forward(request, response);
  }
 }
}
