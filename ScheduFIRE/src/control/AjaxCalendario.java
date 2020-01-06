package control;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.VigileDelFuocoDao;

/**
 * @author Alfredo Giuliano
 */
@WebServlet("/AjaxCalendario")
public class AjaxCalendario extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String giornostr = request.getParameter("giorno").trim();
		String mesestr = request.getParameter("mese").trim();
		String annostr = request.getParameter("anno").trim();
		int giorno;
		int mese;
		int anno;

		try {
			giorno=Integer.parseInt(giornostr);
			mese=Integer.parseInt(mesestr);
			anno=Integer.parseInt(annostr);
		}
		catch (NumberFormatException e) {
			return;
		}
		Date data=Date.valueOf(LocalDate.of(anno, mese, giorno));
		
		//data per modifica composizione squadre
		request.setAttribute("data", data);
		request.setAttribute("tiposquadra",3);
		
		ArrayList<ComponenteDellaSquadraBean> componenti=ComponenteDellaSquadraDao.getComponenti(data);
		List<VigileDelFuocoBean> vigili=VigileDelFuocoDao.getDisponibili(data);
			
		
		JSONArray array = new JSONArray();
		
		for (ComponenteDellaSquadraBean componente:componenti){
			for(VigileDelFuocoBean vigile : vigili) {
				if (vigile.getEmail().equals(componente.getEmailVF())){
			        JSONObject obj = new JSONObject();
			        obj.put("nome", vigile.getNome());
			        obj.put("cognome", vigile.getCognome());
			        obj.put("tipologia", componente.getTipologiaSquadra());

			        array.put(obj);
			        
				}
		    }
		}
		response.setContentType("application/json");
		response.getWriter().append(array.toString());
		

		

	}

}

