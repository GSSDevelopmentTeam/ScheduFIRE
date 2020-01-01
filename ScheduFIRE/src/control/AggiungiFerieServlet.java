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
		LocalDate inizio;
		LocalDate fine;
		String emailVF;
		boolean aggiunta = false;
		int numeroGiorniFerie=0;

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
		inizio=LocalDate.of(annoIniziale, meseIniziale, giornoIniziale);
		fine=LocalDate.of(annoFinale, meseFinale, giornoFinale);
		dataInizio = Date.valueOf(inizio);
		dataFine = Date.valueOf(fine);



		while(inizio.compareTo(fine)<=0) {
			if (GiornoLavorativo.isLavorativo(Date.valueOf(inizio)))
				numeroGiorniFerie++;
			inizio=inizio.plusDays(1);
		}
		System.out.println("1");

		if(numeroGiorniFerie==0) {
			throw new ScheduFIREException("Selezionato un periodo contenente giorni non lavorativi!");
		}

		else {
			System.out.println("2");
			String mansioneVF=VigileDelFuocoDao.ottieni(emailVF).getMansione();
			System.out.println("mansione: "+mansioneVF);
			if(!isPresentiNumeroMinimo(dataInizio, dataFine,mansioneVF)) 
				throw new ScheduFIREException("Personale insufficiente. Impossibile inserire ferie");
			
			System.out.println("8");

			if(ComponenteDellaSquadraDao.isComponente(emailVF, dataInizio))
				throw new ScheduFIREException("Impossibile inserire ferie. Vigile già  inserito in squadra");
			System.out.println("12");
			int feriePrecedenti=VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(emailVF);
			int ferieCorrenti=VigileDelFuocoDao.ottieniNumeroFerieCorrenti(emailVF);
			int totaleFerie=feriePrecedenti+ferieCorrenti;
			System.out.println("totaleFerie: "+totaleFerie);
			if(totaleFerie<numeroGiorniFerie)
				throw new ScheduFIREException("Giorni di ferie insufficienti");
			else {
				System.out.println("20");
				if(feriePrecedenti>=numeroGiorniFerie)
					VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, feriePrecedenti-numeroGiorniFerie);
				else {
					VigileDelFuocoDao.aggiornaFeriePrecedenti(emailVF, 0);
					numeroGiorniFerie-=feriePrecedenti;
					VigileDelFuocoDao.aggiornaFerieCorrenti(emailVF, ferieCorrenti-numeroGiorniFerie);
				}
				System.out.println("30");
				aggiunta =FerieDao.aggiungiPeriodoFerie(emailCT, emailVF, dataInizio, dataFine);
			}


		}
		System.out.println("Aggiunta: "+aggiunta);
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



	private boolean isPresentiNumeroMinimo(Date dataIniziale, Date dataFinale, String mansioneVF) {

		boolean sufficienti = true;
		ArrayList<VigileDelFuocoBean> presenti = new ArrayList<VigileDelFuocoBean>();

		LocalDate inizio = dataIniziale.toLocalDate();
		LocalDate fine = dataFinale.toLocalDate();


		//per tutto il periodo considerato
		while(inizio.compareTo(fine) <= 0) {
			
			//se è un giorno lavorativo
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
