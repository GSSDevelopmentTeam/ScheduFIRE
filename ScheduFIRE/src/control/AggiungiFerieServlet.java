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
import model.bean.CredenzialiBean;
import model.dao.CapoTurnoDao;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class AggiungiFerieServlet
 */
@WebServlet("/AggiungiFerieServlet")
public class AggiungiFerieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiFerieServlet() {
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
		
		Date dataInizio = null;
		Date dataFine = null;
		String emailVF;
		boolean aggiunta = false;
		boolean periodoLavorativo = true;
		int numeroGiorniFerie;
		
		HttpSession sessione = request.getSession();
		CredenzialiBean credenziali = (CredenzialiBean) sessione.getAttribute("credenziali");
		String username = credenziali.getUsername();
		CapoTurnoBean capoTurno = (CapoTurnoBean) CapoTurnoDao.ottieni(username);
		String emailCT = capoTurno.getEmail();
		
		emailVF = request.getParameter("email");
		
		String dataIniziale = request.getParameter("dataIniziale");
		String dataFinale = request.getParameter("dataFinale");
		
		int annoIniziale = Integer.parseInt(dataIniziale.substring(0, 4));
		int meseIniziale = Integer.parseInt(dataIniziale.substring(5, 7));
		int giornoIniziale = Integer.parseInt(dataIniziale.substring(8, 10));
		
		int annoFinale = Integer.parseInt(dataFinale.substring(0, 4));
		int meseFinale = Integer.parseInt(dataFinale.substring(5, 7));
		int giornoFinale = Integer.parseInt(dataFinale.substring(8, 10));
		
		numeroGiorniFerie = giornoFinale - giornoIniziale;
		
		dataInizio = Date.valueOf(LocalDate.of(annoIniziale, meseIniziale, giornoIniziale));
		dataFine = Date.valueOf(LocalDate.of(annoFinale, meseFinale, giornoFinale));
		
		periodoLavorativo = this.isPeriodoLavorativo(dataInizio, dataFine);
		
		if(!periodoLavorativo)
			throw new ScheduFIREException("Selezionato un periodo contenente giorni non lavorativi!");
		else {
			
			if((!Util.abbastanzaPerTurno(2, 3, 7)) && (!Util.abbastanzaPerTurno(3, 3, 6)) 
					&& (!Util.abbastanzaPerTurno(4, 3, 5))) {
				
				throw new ScheduFIREException("Personale insufficiente.\nImpossibile inserire ferie");
			}
			else {
				//controllo se schedulato da inserire
				
				if(VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF) != 0) {
					if(VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF) >= numeroGiorniFerie) {
						aggiunta = FerieDao.aggiungiPeriodoFerie(emailCT, emailVF, dataInizio, dataFine);
						
						int ferieDb = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
						VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, (ferieDb - numeroGiorniFerie));
					}
					else {
						aggiunta = FerieDao.aggiungiPeriodoFerie(emailCT, emailVF, dataInizio, dataFine);
						
						int feriePDb = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
						int ferieCDb = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
						int ferieDaScalareC = ferieCDb - (numeroGiorniFerie - feriePDb);
						
						VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, 0);
						VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieDaScalareC);
					}
				}
				else {
					if(VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF) >= numeroGiorniFerie) {
						aggiunta = FerieDao.aggiungiPeriodoFerie(emailCT, emailVF, dataInizio, dataFine);
						
						int ferieCDb = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
						VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, (ferieCDb - numeroGiorniFerie));
					}
					else
						throw new ScheduFIREException("Giorni di ferie insufficienti");
				}
			}
		}
		
		response.setContentType("application/json");
		JSONArray array = new JSONArray();
		
		if(aggiunta) 
			array.put(true);
		else
			array.put(false);
		
		response.getWriter().append(array.toString());
	}
	
	private boolean isPeriodoLavorativo(Date dataIniziale, Date dataFinale) {
		boolean lavorativo = true;
		
		LocalDate inizio = dataIniziale.toLocalDate();
		LocalDate fine = dataFinale.toLocalDate();
		
		while(inizio.compareTo(fine) != 0) {
			
			if(!GiornoLavorativo.isLavorativo(Date.valueOf(inizio))) {
				lavorativo = false;
				return lavorativo;
			}
			
			inizio.plusDays(1);
		}
		
		lavorativo = GiornoLavorativo.isLavorativo(Date.valueOf(fine));
		return lavorativo;
	}

}
