package control;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;

/**
 * Servlet per la visualizzazione del personale disponibile nel turno.
 * @author Alfredo Giuliano
 */

@WebServlet("/PersonaleDisponibile")
public class PersonaleDisponibileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//prendo i vigili del fuoco disponibili alla data odierna
		List<VigileDelFuocoBean> vigili=VigileDelFuocoDao.getDisponibili(new Date(System.currentTimeMillis()));
		request.setAttribute("vigili", vigili);
		request.getRequestDispatcher("JSP/PersonaleDisponibile.jsp").forward(request, response);
		
		
	}
	
}
