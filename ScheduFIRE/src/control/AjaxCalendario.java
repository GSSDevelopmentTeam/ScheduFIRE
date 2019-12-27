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
		System.out.println("Sono AjaxCalendario e sono stato chiamato");
		String giornostr = request.getParameter("giorno");
		String mesestr = request.getParameter("mese");
		String annostr = request.getParameter("anno");
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

