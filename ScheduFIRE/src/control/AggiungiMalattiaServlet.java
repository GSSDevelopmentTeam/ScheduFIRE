package control;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import model.bean.CapoTurnoBean;
import model.bean.GiorniMalattiaBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Notifiche;

/**
 * Servlet implementation class AggiungiMalattiaServlet
 * 
 */
@WebServlet("/AggiungiMalattiaServlet")
public class AggiungiMalattiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiMalattiaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("JSON")!=null && request.getParameter("inserisci") != null) {
			
	    String emailVF = request.getParameter("emailVF");
		String dataIniz = request.getParameter("dataInizio");
		String dataFin = request.getParameter("dataFine");
		Date dataInizio = null;
	    Date dataFine = null;
		int giorniMalattia=0;
		
		
		HttpSession sessione = request.getSession();
		CapoTurnoBean capoTurno = (CapoTurnoBean) sessione.getAttribute("capoturno");
		String emailCT = capoTurno.getEmail();
					
				int annoInizio=Integer.parseInt(dataIniz.substring(6, 10));
				int meseInizio=Integer.parseInt(dataIniz.substring(3, 5));
				int giornoInizio=Integer.parseInt(dataIniz.substring(0, 2));
				int annoFine=Integer.parseInt(dataFin.substring(6, 10));
				int meseFine=Integer.parseInt(dataFin.substring(3, 5));
				int giornoFine=Integer.parseInt(dataFin.substring(0, 2));
				
				//ottiene un'istanza di LocalDate dalle stringhe relative a giorno, mese ed anno
				LocalDate inizioMalattia = LocalDate.of(annoInizio, meseInizio, giornoInizio);
				LocalDate fineMalattia = LocalDate.of(annoFine, meseFine, giornoFine);

				dataInizio = Date.valueOf(inizioMalattia);
				dataFine = Date.valueOf(fineMalattia);
				
				 GiorniMalattiaBean malattia = new GiorniMalattiaBean();
				    
					malattia.setId(0);
					malattia.setDataInizio(dataInizio);
					malattia.setDataFine(dataFine);
					malattia.setEmailCT(emailCT);
					malattia.setEmailVF(emailVF);
					JSONArray array = new JSONArray();
					
					
					
					//notifica il CT se il Vigile è gia schedulato al momento dell'aggiunta della malattia
					int numeroGiorniLavorativi = 0;
					
					while(inizioMalattia.compareTo(fineMalattia)<=0) {
						if (GiornoLavorativo.isLavorativo(Date.valueOf(inizioMalattia)))
							numeroGiorniLavorativi++;
						inizioMalattia=((LocalDate) inizioMalattia).plusDays(1);
					}
					
					for(int i = 0;i<numeroGiorniLavorativi; i++) {
						if(ComponenteDellaSquadraDao.isComponente(emailVF, dataInizio)) 
						Notifiche.update(Notifiche.UPDATE_SQUADRE_PER_MALATTIA, dataInizio, dataFine, emailVF);
						}
					
				   if(GiorniMalattiaDao.addMalattia(malattia) == true) 
					  array.put(true);
				   else {
					   array.put(false);
				   }
				response.setContentType("application/json");
				
				response.getWriter().append(array.toString());
			}
	    }	
	}


