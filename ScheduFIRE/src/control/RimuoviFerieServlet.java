package control;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		SimpleDateFormat formattazione = new SimpleDateFormat("dd-MM-yyyy");
		boolean rimozione;
		
		emailVF = request.getParameter("id");
		
		try {
			dataInizio = (Date) formattazione.parse(request.getParameter("dataInizio"));
			dataFine = (Date) formattazione.parse(request.getParameter("dataFine"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		rimozione = FerieDao.rimuoviPeriodoFerie(emailVF, dataInizio, dataFine);
		
		if(rimozione) {
			//implementare risposta JSON
		}
	}

}
