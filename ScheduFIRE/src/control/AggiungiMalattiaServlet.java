package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import model.bean.CapoTurnoBean;
import model.bean.GiorniMalattiaBean;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;

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
			
		String emailCT = "mail55";
	    String emailVF = request.getParameter("emailVF");
		String dataIniz = request.getParameter("dataInizio");
		String dataFin = request.getParameter("dataFine");
		Date dataInizio = null;
	    Date dataFine = null;
		int giorniMalattia=0;

	    
	   HttpSession sessione = request.getSession();
		CapoTurnoBean capoTurno = (CapoTurnoBean) sessione.getAttribute("capoturno");;
		/*String emailCT = capoTurno.getEmail();*/
			   
				if( emailCT == null )
					throw new ScheduFIREException("Errore nella sessione");
				
				
				int annoInizio=Integer.parseInt(dataIniz.substring(6, 10));
				int meseInizio=Integer.parseInt(dataIniz.substring(3, 5));
				int giornoInizio=Integer.parseInt(dataIniz.substring(0, 2));
				int annoFine=Integer.parseInt(dataFin.substring(6, 10));
				int meseFine=Integer.parseInt(dataFin.substring(3, 5));
				int giornoFine=Integer.parseInt(dataFin.substring(0, 2));
				
				
				LocalDate inizioMalattia = LocalDate.of(annoInizio, meseInizio, giornoInizio);
				LocalDate fineMalattia = LocalDate.of(annoFine, meseFine, giornoFine);

				dataInizio = Date.valueOf(LocalDate.of(annoInizio, meseInizio, giornoInizio));
				dataFine = Date.valueOf(LocalDate.of(annoFine, meseFine, giornoFine));
				
				while( inizioMalattia.compareTo(fineMalattia )<=0) {
					if (GiornoLavorativo.isLavorativo(Date.valueOf( inizioMalattia )))
						giorniMalattia++;
					 inizioMalattia = inizioMalattia .plusDays(1);
				}
				
				if(giorniMalattia==0) {
					throw new ScheduFIREException("Selezionare un periodo che contiene solamente giorni lavorativi!");
				}

				
				 GiorniMalattiaBean malattia = new GiorniMalattiaBean();
				    
					malattia.setId(0);
					malattia.setDataInizio(dataInizio);
					malattia.setDataFine(dataFine);
					malattia.setEmailCT(emailCT);
					malattia.setEmailVF(emailVF);
					
				   if( ! GiorniMalattiaDao.addMalattia(malattia)) 
						throw new ScheduFIREException("Errore nel DataBase");
				response.setContentType("application/json");
				JSONArray array = new JSONArray();

				response.getWriter().append(array.toString());
			}
		}
	}


