package control;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import model.bean.CapoTurnoBean;
import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;

/**
 * Servlet implementation class PeriodiDiMalattiaServlet
 */
@WebServlet("/PeriodiDiMalattiaServlet")
public class PeriodiDiMalattiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PeriodiDiMalattiaServlet() {
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
		if(request.getParameter("JSON")!=null && request.getParameter("visMalattia")!=null ) {
					String emailVF = request.getParameter("emailVF");
					
					if( emailVF == null )
						throw new ScheduFIREException();
			
					List<GiorniMalattiaBean> GiorniMalattiaBean = GiorniMalattiaDao.ottieniMalattie(emailVF);
					JSONArray array = new JSONArray();
					for(GiorniMalattiaBean giorniMalattiaBean:GiorniMalattiaBean) {
				
					JSONArray arrayrange = new JSONArray();
					arrayrange.put(giorniMalattiaBean.getDataInizio());
					arrayrange.put(giorniMalattiaBean.getDataFine().toLocalDate().plusDays(1));
					array.put(arrayrange);
				
					}
					response.setContentType("application/json");
					response.getWriter().append(array.toString());
						}
		
		else {
		ArrayList<VigileDelFuocoBean> listaVigili = new ArrayList<VigileDelFuocoBean>(VigileDelFuocoDao.ottieni());
		
		request.setAttribute("listaVigili", listaVigili);
		request.getRequestDispatcher("JSP/GestioneMalattiaJSP.jsp").forward(request, response);
  }
 }
}
