package control;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import model.bean.CapoTurnoBean;
import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.GiornoLavorativo;
import util.Util;

/**
 * Servlet implementation class RimozioneMalattiaServlet
 * @author Biagio Bruno
 */
@WebServlet("/RimozioneMalattiaServlet")
public class RimozioneMalattiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RimozioneMalattiaServlet() {
		super(); 
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.isCapoTurno(request);
		String emailVF;
		Date dataInizio = null;
		Date dataFine = null;
		boolean rimozione = false;



		//Ottenimento parametri
		emailVF = request.getParameter("emailVF");
		String dataIniz = request.getParameter("dataIniziale");
		String dataFin = request.getParameter("dataFinale");

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

		GiorniMalattiaBean iniziale = null;
		GiorniMalattiaBean finale = null;

		//Controllo del periodo di Ferie mediante metodo della classe FerieDao
		List<GiorniMalattiaBean> malattia = new ArrayList<GiorniMalattiaBean>();

		malattia = GiorniMalattiaDao.malattiaInRange(dataInizio, dataFine, emailVF);

		//Istanziazione GiorniMalattiaBean iniziale
		if(malattia.size() > 0 && !malattia.get(0).getDataInizio().toLocalDate().equals(dataInizio.toLocalDate())) {
			GiorniMalattiaBean m = malattia.get(0);
			iniziale = new GiorniMalattiaBean();
			iniziale.setId(m.getId());
			iniziale.setEmailVF(m.getEmailVF());
			iniziale.setEmailCT(m.getEmailCT());
			iniziale.setDataInizio(m.getDataInizio());
			iniziale.setDataFine(dataInizio);
		}


		//Istanziazione GiorniMalattiaBean finale
		if(malattia.size() > 0 && !malattia.get(malattia.size() - 1).getDataFine().toLocalDate().equals(dataFine.toLocalDate())) {
			GiorniMalattiaBean m = malattia.get(malattia.size()-1);;
			finale = new GiorniMalattiaBean();
			finale.setId(m.getId());
			finale.setEmailVF(m.getEmailVF());
			finale.setEmailCT(m.getEmailCT());
			finale.setDataInizio(dataFine);
			finale.setDataFine(m.getDataFine());
		}

		//Malattie da non rimuovere
		if(iniziale != null) {
			GiorniMalattiaBean inizialeBean = new GiorniMalattiaBean();
			inizialeBean.setDataInizio(iniziale.getDataInizio());
			inizialeBean.setDataFine(Date.valueOf(dataInizio.toLocalDate().plusDays(-1)));
			inizialeBean.setEmailCT(iniziale.getEmailCT());
			inizialeBean.setEmailVF(iniziale.getEmailVF());
			GiorniMalattiaDao.addMalattia(inizialeBean);
		}
		if(finale != null) {
			GiorniMalattiaBean finaleBean = new GiorniMalattiaBean();
			finaleBean.setDataInizio(Date.valueOf(dataFine.toLocalDate().plusDays(1)));
			finaleBean.setDataFine(finale.getDataFine());
			finaleBean.setEmailCT(finale.getEmailCT());
			finaleBean.setEmailVF(finale.getEmailVF());
			GiorniMalattiaDao.addMalattia(finaleBean);
		}



		//Rimozione giorni malattia selezionati
		for(GiorniMalattiaBean m : malattia) {
			rimozione = GiorniMalattiaDao.rimuoviPeriodoDiMalattia(m.getEmailVF(), m.getDataInizio(), m.getDataFine());
		}

		response.setContentType("application/json");
		JSONArray array = new JSONArray();

		if(rimozione) {
			array.put(true);
		}else
			array.put(false);

		response.getWriter().append(array.toString());


	}
}
