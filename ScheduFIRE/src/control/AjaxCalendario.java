package control;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import model.dao.ListaSquadreDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * @author Alfredo Giuliano
 */
@WebServlet("/AjaxCalendario")
public class AjaxCalendario extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isAutenticato(request);
		
		
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
		
		ArrayList<ComponenteDellaSquadraBean> componenti=ComponenteDellaSquadraDao.getComponenti(data);
		List<VigileDelFuocoBean> vigili=VigileDelFuocoDao.getDisponibili(data);
		
		
		//array che contiene la schedulazione delle squadre
		JSONArray array = new JSONArray();
		array.put(isModificabile(data));
		array.put(isGenerabile(data));

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
	
	
	private boolean isModificabile(Date giornoCliccato) {
        LocalDateTime ora=LocalDateTime.now();
        LocalDate giornoCliccatoLD=giornoCliccato.toLocalDate();
        boolean isModificabile=false;
        Date oraDate=Date.valueOf(ora.toLocalDate());
        if(GiornoLavorativo.isDiurno(oraDate))
            oraDate=Date.valueOf(LocalDate.now().plusDays(1));
        LocalDate prossimoDiurno=GiornoLavorativo.nextLavorativo(oraDate).toLocalDate();
        LocalDate prossimoNotturno=prossimoDiurno.plusDays(1);
 
 
 
        //Se ho cliccato un giorno diurno
        if(GiornoLavorativo.isDiurno(giornoCliccato)) {
 
 
            //Se il giorno cliccato � proprio oggi, e sono meno delle 19 59, quindi ancora durante il turno diurno, posso modificarlo
            if(giornoCliccatoLD.compareTo(ora.toLocalDate())==0 && ora.getHour()<=19 && ora.getMinute()<=59) {
                isModificabile=true;
            }
 
            
            //Se ho cliccato il giorno diurno del turno successivo e gi� esiste nel database, posso modificarlo
            else if(giornoCliccatoLD.compareTo(prossimoDiurno)==0 && ListaSquadreDao.isEsistente(giornoCliccato)) {
                isModificabile=true;
            }
 
        }
 
        //Se ho cliccato un giorno notturno
        else if(!GiornoLavorativo.isDiurno(giornoCliccato) && GiornoLavorativo.isLavorativo(giornoCliccato)) {
 
            //Se � ieri, quindi il giorno cliccato precede di 1 giorno oggi e sono meno delle 7 59, 
            // quindi ancora durante il turno notturno, posso modificarlo
            if(giornoCliccatoLD.compareTo(ora.toLocalDate().plusDays(-1))==0 && ora.getHour()<=7 && ora.getMinute()<=59) {
 
                isModificabile=true;
            }
 
            //Se il giorno cliccato � proprio oggi
            else if(giornoCliccatoLD.compareTo(ora.toLocalDate())==0) {
                isModificabile=true;
 
            }
 
            //Se ho cliccato il giorno notturno del turno successivo e gi� esiste nel database, posso modificarlo
            else if(giornoCliccatoLD.compareTo(prossimoNotturno)==0 && ListaSquadreDao.isEsistente(giornoCliccato)) {
                isModificabile=true;
            }
 
        }
        
        if(!ListaSquadreDao.isEsistente(giornoCliccato)) {
        	isModificabile=false;
        }
 
        return isModificabile;
    }
    
    
    private boolean isGenerabile(Date giornoCliccato) {
        LocalDateTime ora=LocalDateTime.now();
        LocalDate giornoCliccatoLD=giornoCliccato.toLocalDate();
        boolean isGenerabile=false;
        Date oraDate=Date.valueOf(ora.toLocalDate());
        if(GiornoLavorativo.isDiurno(oraDate))
            oraDate=Date.valueOf(LocalDate.now().plusDays(1));
        LocalDate prossimoDiurno=GiornoLavorativo.nextLavorativo(oraDate).toLocalDate();
        LocalDate prossimoNotturno=prossimoDiurno.plusDays(1);
 
        //Se ho cliccato un giorno diurno
        if(GiornoLavorativo.isDiurno(giornoCliccato)) {
 
 
            //Se ho cliccato il giorno diurno del turno successivo e non esiste nel database, posso generarlo
            if(giornoCliccatoLD.compareTo(prossimoDiurno)==0 && !ListaSquadreDao.isEsistente(giornoCliccato)) {
                isGenerabile=true;
            }
 
        }
 
        //Se ho cliccato un giorno notturno
        else if(!GiornoLavorativo.isDiurno(giornoCliccato) && GiornoLavorativo.isLavorativo(giornoCliccato)) {
 
            
 
            //Se ho cliccato il giorno diurno del turno successivo e non esiste nel database, posso generarlo
             if(giornoCliccatoLD.compareTo(prossimoNotturno)==0 && !ListaSquadreDao.isEsistente(giornoCliccato)) {
                isGenerabile=true;
            }
 
        }
        

 
        return isGenerabile;
    }

}

