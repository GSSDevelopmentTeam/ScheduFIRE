package control;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;

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
		
		if(request.getParameter("JSON")!=null && request.getParameter("aggiunta")!=null )
		{   
			
		    String emailCT = "mail55";
			String emailVF = request.getParameter("emailVF");
			String dataInizio = request.getParameter("dataInizio");
			String dataFine = request.getParameter("dataFine");
		
		    Date dataInizioData;
		    Date dataFineData;
			
		    
		   
				try {
					dataInizioData = (Date) new SimpleDateFormat("yyyy/MM/dd").parse(dataInizio);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					dataFineData=(Date) new SimpleDateFormat("yyyy/MM/dd").parse(dataFine);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					dataFineData = null;
				} 

		   
	
			
			GiorniMalattiaBean malattia = new GiorniMalattiaBean();
			
			malattia.setId(0);
			//malattia.setDataInizio(dataInizioData);//date
			malattia.setDataFine(dataFineData);//date
			malattia.setEmailCT(emailCT);
			malattia.setEmailVF(emailVF);
			
		    GiorniMalattiaDao.addMalattia(malattia);
			
		}
		else {
		ArrayList<VigileDelFuocoBean> listaVigili = new ArrayList<VigileDelFuocoBean>(VigileDelFuocoDao.ottieni());
		
		request.setAttribute("listaVigili", listaVigili);
		request.getRequestDispatcher("JSP/GestioneMalattiaJSP.jsp").forward(request, response);
	}
			/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		
				Date inizio = Date.parse(request.getParameter("data_inizio"), formatter);
				LocalDate fine = LocalDate.parse(request.getParameter("data_fine"), formatter);;
		
		
				if( inizio == null || fine == null) {
					
				}
				else {
					int v = 8;
					GiorniMalattiaBean malattia = new GiorniMalattiaBean(v, inizio, fine, "mail55", "mail14");
					
					GiorniMalattiaDao x = new GiorniMalattiaDao();	
					x.addMalattia(malattia);
				}*/
	}
	

}
