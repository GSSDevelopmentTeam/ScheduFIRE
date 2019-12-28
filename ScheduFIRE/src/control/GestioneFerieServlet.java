package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import model.bean.FerieBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;

/**
 * Servlet implementation class GestioneFerieServlet
 * 
 * @author Nicola Labanca
 */
@WebServlet("/GestioneFerieServlet")
public class GestioneFerieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestioneFerieServlet() {
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
		if(request.getParameter("JSON")!=null && request.getParameter("aggiunta")!=null ) {
			String email=request.getParameter("email");
			List<FerieBean> ferie=FerieDao.ottieniFerieConcesse(email);
			JSONArray array = new JSONArray();
			for(FerieBean ferieBean:ferie) {

				JSONArray arrayrange = new JSONArray();
				arrayrange.put(ferieBean.getDataInizio());
				arrayrange.put(ferieBean.getDataFine().toLocalDate().plusDays(1));
				array.put(arrayrange);

			}
			response.setContentType("application/json");
			response.getWriter().append(array.toString());
		}
		else if(request.getParameter("JSON")!=null && request.getParameter("rimozione")!=null ) {
			String email=request.getParameter("email");
			List<FerieBean> ferie=FerieDao.ottieniFerieConcesse(email);
			JSONArray array = new JSONArray();
			for(FerieBean ferieBean:ferie) {

				JSONArray arrayrange = new JSONArray();
				arrayrange.put(ferieBean.getDataInizio());
				arrayrange.put(ferieBean.getDataFine().toLocalDate());
				array.put(arrayrange);

			}
			response.setContentType("application/json");
			response.getWriter().append(array.toString());
		}
		else {
			ArrayList<VigileDelFuocoBean> listaVigili = new ArrayList<VigileDelFuocoBean>(VigileDelFuocoDao.ottieni());

			request.setAttribute("listaVigili", listaVigili);
			request.getRequestDispatcher("JSP/GestioneFerieJSP.jsp").forward(request, response);
		}
	}

}
