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

		dataInizio=Date.valueOf(LocalDate.of(annoIniz, meseIniz, giornoIniz));
		dataFine=Date.valueOf(LocalDate.of(annoFin, meseFin, giornoFin));

		rimozione = FerieDao.rimuoviPeriodoFerie(emailVF, dataInizio, dataFine);
		response.setContentType("application/json");
		JSONArray array = new JSONArray();
		if(rimozione) 
			array.put(true);
		else
			array.put(false);
		
			response.getWriter().append(array.toString());
		
	}

}
