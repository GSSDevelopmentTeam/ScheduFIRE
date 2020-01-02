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

import org.json.JSONArray;

import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.Util;

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
		Util.isCapoTurno(request);
		if(request.getParameter("JSON")!=null && request.getParameter("visFerie") != null ) {
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
		
		
		else if(request.getParameter("JSON")!=null && request.getParameter("aggiunta")!=null )
		{   
		    String emailCT = "mail55";
		    String emailVF = request.getParameter("emailVF");
			String dataIniz = request.getParameter("dataInizio");
			String dataFin = request.getParameter("dataFine"); 
			Date dataInizio = null;
		    Date dataFine = null;
				   
					if( emailCT == null )
						throw new ScheduFIREException();
					if( dataFin == null )
						throw new ScheduFIREException();
		    
			    String annoIniz=dataIniz.substring(0, 4);
			    String meseIniz=dataIniz.substring(5, 7);
			    String giornoIniz=dataIniz.substring(8, 10);
			    String annoFin=dataFin.substring(0, 4);
			    String meseFin=dataFin.substring(5, 7);
			    String giornoFin=dataFin.substring(8, 10);
			    
			    
			    String datainiz = annoIniz+"-"+meseIniz+"-"+giornoIniz;
			    String datafin = annoFin+"-"+meseFin+"-"+giornoFin;
			    
			    DateFormat df = new SimpleDateFormat ("d/M/yyyy");
			    df.setLenient (false);
			   
			    
			    try {
					dataInizio = (Date) df.parse (datainiz);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					dataFine = (Date) df.parse (datafin);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			    GiorniMalattiaBean malattia = new GiorniMalattiaBean(0, dataInizio, dataFine, emailCT, emailVF);
			    
				malattia.setId(0);
				malattia.setDataInizio(dataInizio);//date
				malattia.setDataFine(dataFine);//date
				malattia.setEmailCT(emailCT);
				malattia.setEmailVF(emailVF);
			
			   if( ! GiorniMalattiaDao.addMalattia(malattia)) 
					throw new ScheduFIREException();
			
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
