package control;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Notifiche;
import util.Util;

/**
 * Servlet implementation class AggiungiMalattiaServlet
 * 
 */
@WebServlet("/AggiungiMalattiaServlet")
public class AggiungiMalattiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiMalattiaServlet() {
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
		if(request.getParameter("JSON")!=null && request.getParameter("inserisci") != null) {
			
	    String emailVF = request.getParameter("emailVF");
		String dataIniz = request.getParameter("dataInizio");
		String dataFin = request.getParameter("dataFine");
		Date dataInizio = null;
	    Date dataFine = null;
		int giorniMalattia=0;
		
		
		HttpSession sessione = request.getSession();
		CapoTurnoBean capoTurno = (CapoTurnoBean) sessione.getAttribute("capoturno");
		String emailCT = capoTurno.getEmail();
					
				int annoInizio=Integer.parseInt(dataIniz.substring(6, 10));
				int meseInizio=Integer.parseInt(dataIniz.substring(3, 5));
				int giornoInizio=Integer.parseInt(dataIniz.substring(0, 2));
				int annoFine=Integer.parseInt(dataFin.substring(6, 10));
				int meseFine=Integer.parseInt(dataFin.substring(3, 5));
				int giornoFine=Integer.parseInt(dataFin.substring(0, 2));
				
				//ottiene un'istanza di LocalDate dalle stringhe relative a giorno, mese ed anno
				LocalDate inizioMalattia = LocalDate.of(annoInizio, meseInizio, giornoInizio);
				LocalDate fineMalattia = LocalDate.of(annoFine, meseFine, giornoFine);

				dataInizio = Date.valueOf(inizioMalattia);
				dataFine = Date.valueOf(fineMalattia);
				
				//Aggiornamento notifiche
				Notifiche.update(Notifiche.UPDATE_PER_MALATTIA, dataInizio, dataFine, emailVF);
				
				 GiorniMalattiaBean malattia = new GiorniMalattiaBean();
				    
					malattia.setId(0);
					malattia.setDataInizio(dataInizio);
					malattia.setDataFine(dataFine);
					malattia.setEmailCT(emailCT);
					malattia.setEmailVF(emailVF);
					JSONArray array = new JSONArray();
				   
				response.setContentType("application/json");
				
				response.getWriter().append(array.toString());
			}
	    }	
	
	
	//Metodo che verifica se nel periodo di ferie richiesto sono preseti almeno il numero minimo di VF
		private boolean isPresentiNumeroMinimo(Date dataIniziale, Date dataFinale, String mansioneVF) {

			boolean sufficienti = true;
			ArrayList<VigileDelFuocoBean> presenti = new ArrayList<VigileDelFuocoBean>();

			LocalDate inizio = dataIniziale.toLocalDate();
			LocalDate fine = dataFinale.toLocalDate();


			//per tutto il periodo considerato
			while(inizio.compareTo(fine) <= 0) {
				
				//se e' un giorno lavorativo
				if(GiornoLavorativo.isLavorativo(Date.valueOf(inizio))) {
					int capiSquadra = 0; 
					int autisti = 0;
					int vigili = 0;

					presenti = VigileDelFuocoDao.getDisponibili(Date.valueOf(inizio));

					//conto quanti capi squadra, autisti e vigili sono disponibili quel giorno
					for(int i=0; i<presenti.size(); i++) {
						String mansione = presenti.get(i).getMansione();
						if(mansione.equals("Capo Squadra"))
							capiSquadra += 1;
						else
							if(mansione.equals("Autista"))
								autisti += 1;
							else
								if(mansione.equals("Vigile"))
									vigili += 1;
					}

					//sottraggo il vigile che vuole andare in ferie
					if(mansioneVF.equals("Capo Squadra"))
						capiSquadra -= 1;
					else
						if(mansioneVF.equals("Autista"))
							autisti -= 1;
						else vigili -= 1;

					sufficienti = Util.abbastanzaPerTurno(capiSquadra, autisti, vigili);

					if(!sufficienti) {
						sufficienti=false;
						return sufficienti;
					}

				}
				inizio=inizio.plusDays(1);

			}
			return sufficienti;
		}

	

	}




