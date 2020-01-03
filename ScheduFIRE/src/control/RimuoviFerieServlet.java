package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;

/**
 * Servlet implementation class RimuoviFerieServlet
 */
@WebServlet("/RimuoviFerieServlet")
public class RimuoviFerieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviFerieServlet() {
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
		boolean rimozione;
		
		emailVF = request.getParameter("email");
		String dataIniz= request.getParameter("dataIniziale");
		String dataFin= request.getParameter("dataFinale");
		int annoIniz=Integer.parseInt(dataIniz.substring(0, 4));
		int meseIniz=Integer.parseInt(dataIniz.substring(5, 7));
		int giornoIniz=Integer.parseInt(dataIniz.substring(8, 10));
		int annoFin=Integer.parseInt(dataFin.substring(0, 4));
		int meseFin=Integer.parseInt(dataFin.substring(5, 7));
		int giornoFin=Integer.parseInt(dataFin.substring(8, 10));
		
		LocalDate inizio=LocalDate.of(annoIniz, meseIniz, giornoIniz);
		LocalDate fine=LocalDate.of(annoFin, meseFin, giornoFin);

		dataInizio=Date.valueOf(inizio);
		dataFine=Date.valueOf(fine);
		
		int numeroGiorniFerie = contaNumeroGiorniFerie(inizio, fine); 

		rimozione = FerieDao.rimuoviPeriodoFerie(emailVF, dataInizio, dataFine);
		
		aggiornaFerie(numeroGiorniFerie, emailVF);
		
		response.setContentType("application/json");
		JSONArray array = new JSONArray();
		if(rimozione) { 
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

	private int contaNumeroGiorniFerie(LocalDate inizio, LocalDate fine) {
		int numeroGiorniFerie = 0;
		
		while(inizio.compareTo(fine)<=0) {
			if (GiornoLavorativo.isLavorativo(Date.valueOf(inizio)))
				numeroGiorniFerie++;
			inizio=inizio.plusDays(1);
		}
		
		return numeroGiorniFerie;
	}
	
	private void aggiornaFerie(int numeroFerie, String emailVF) {
		
		int feriePrecedenti = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
		int ferieCorrenti = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
		 
		if(ferieCorrenti + numeroFerie <= 22){
			VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieCorrenti + numeroFerie);
		}
		else {
			VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieCorrenti + (22 - ferieCorrenti));
			VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, feriePrecedenti + (numeroFerie - (22 - ferieCorrenti)));
		}
	}
	
}
