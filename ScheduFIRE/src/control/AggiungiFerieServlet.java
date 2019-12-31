package control;

import java.io.IOException;
import java.sql.Date;
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
import model.bean.CredenzialiBean;
import model.bean.VigileDelFuocoBean;
import model.dao.CapoTurnoDao;
import model.dao.ComponenteDellaSquadraDao;
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
		CapoTurnoBean capoTurno = (CapoTurnoBean) sessione.getAttribute("capoturno");;
		String emailCT = capoTurno.getEmail();
		
		emailVF = request.getParameter("email");
		
		String dataIniziale = request.getParameter("dataIniziale");
		String dataFinale = request.getParameter("dataFinale");
		
		int annoIniziale = Integer.parseInt(dataIniziale.substring(6, 10));
		int meseIniziale = Integer.parseInt(dataIniziale.substring(3, 5));
		int giornoIniziale = Integer.parseInt(dataIniziale.substring(0, 2));
		
		int annoFinale = Integer.parseInt(dataFinale.substring(6, 10));
		int meseFinale = Integer.parseInt(dataFinale.substring(3, 5));
		int giornoFinale = Integer.parseInt(dataFinale.substring(0, 2));
		System.out.println(""+giornoIniziale+" "+meseIniziale+" "+annoIniziale+" -- "+giornoFinale+" "+meseFinale+" "+annoFinale);
		numeroGiorniFerie = giornoFinale - giornoIniziale;
		
		dataInizio = Date.valueOf(LocalDate.of(annoIniziale, meseIniziale, giornoIniziale));
		dataFine = Date.valueOf(LocalDate.of(annoFinale, meseFinale, giornoFinale));
		
		periodoLavorativo = this.isPeriodoLavorativo(dataInizio, dataFine);
		
		if(!periodoLavorativo)
			throw new ScheduFIREException("Selezionato un periodo contenente giorni non lavorativi!");
		else {
			
			if(isPresentiNumeroMinimo(dataInizio, dataFine)) {
				
				throw new ScheduFIREException("Personale insufficiente.\nImpossibile inserire ferie");
			}
			else {
				
				if(ComponenteDellaSquadraDao.isComponente(emailVF, dataInizio))
					throw new ScheduFIREException("Impossibile inserire ferie. Vigile già inserito in squadra");
				
				else {
				
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
		}
		
		response.setContentType("application/json");
		JSONArray array = new JSONArray();
		
		if(aggiunta) {
			int feriePDb = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
			int ferieCDb = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
			array.put(true);
			array.put(feriePDb);
			array.put(ferieCDb);
		}
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
	
	private boolean isPresentiNumeroMinimo(Date dataIniziale, Date dataFinale) {
		int capiSquadra = 0; 
		int autisti = 0;
		int vigili = 0;
		boolean presentiNumero = true;
		ArrayList<VigileDelFuocoBean> presenti = new ArrayList<VigileDelFuocoBean>();
		
		LocalDate inizio = dataIniziale.toLocalDate();
		LocalDate fine = dataFinale.toLocalDate();

		while(inizio.compareTo(fine) != 0) {
			presenti = VigileDelFuocoDao.getDisponibili(Date.valueOf(inizio));
			
			for(int i=0; i<presenti.size(); i++) {
				String mansione = presenti.get(i).getMansione().toString().toLowerCase();
				
				if(mansione.equals("Capo Squadra"))
					capiSquadra += 1;
				else
					if(mansione.equals("Autista"))
						autisti += 1;
					else
						if(mansione.equals("Vigile"))
							vigili += 1;
			}
			
			presentiNumero = Util.abbastanzaPerTurno(capiSquadra, autisti, vigili);
			
			if(!presentiNumero) {
				presentiNumero = false;
				return presentiNumero;
			}
			else
				inizio.plusDays(1);	
		}
		
		presenti = VigileDelFuocoDao.getDisponibili(Date.valueOf(fine));
		
		for(int i=0; i < presenti.size(); i++) {
			String mansione = presenti.get(i).getMansione().toString().toLowerCase();
			
			if(mansione.equals("Capo Squadra"))
				capiSquadra += 1;
			else
				if(mansione.equals("Autista"))
					autisti += 1;
				else
					if(mansione.equals("Vigile"))
						vigili += 1;
		}
		
		presentiNumero = Util.abbastanzaPerTurno(capiSquadra, autisti, vigili);
		
		return presentiNumero;
	}

}
