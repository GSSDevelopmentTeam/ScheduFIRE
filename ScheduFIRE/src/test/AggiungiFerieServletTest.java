package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.google.protobuf.TextFormat.ParseException;

import control.AggiungiFerieServlet;
import control.AutenticazioneException;
import control.ScheduFIREException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import junit.framework.TestCase;
import model.ConnessioneDB;
import model.bean.CapoTurnoBean;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.GiorniMalattiaBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.Notifiche;


class AggiungiFerieServletTest extends TestCase{
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static AggiungiFerieServlet servlet;
	static CapoTurnoBean capoturno;

	static  ArrayList<ComponenteDellaSquadraBean> componente = new ArrayList<ComponenteDellaSquadraBean>();


	@BeforeEach
	public void setUp() throws Exception {
		servlet = new AggiungiFerieServlet();
		request = new MockHttpServletRequest();
		response= new MockHttpServletResponse();
		session = new MockHttpSession();

		capoturno = new CapoTurnoBean("capoturno","capoturno","capoturno", "B", "capoturno");

		Date data = Date.valueOf("2020-03-17");

		String sql1 ="INSERT INTO listasquadre (giornoLavorativo, oraIniziale, "
				+ "emailCT) VALUES (?, ?, ?);";
		String sql2 ="INSERT INTO squadra (tipologia, giornoLavorativo, caricoLavoro) VALUES (?, ?, ?);";
		String sql3 ="insert into ComponenteDellaSquadra(emailVF, tipologia, giornoLavorativo) "
				+ "values (?, ?, ?);";

		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps1 = con.prepareStatement(sql1);
			PreparedStatement ps2 = con.prepareStatement(sql2);
			PreparedStatement ps3 = con.prepareStatement(sql3);
			ps1.setDate(1, data);
			ps1.setInt(2, 8);
			ps1.setString(3, "capoturno");

			ps2.setString(1, "Auto Botte");
			ps2.setDate(2, data);
			ps2.setInt(3, 1);

			ps3.setString(1, "luca.raimondi@vigilfuoco.it");
			ps3.setString(2, "Auto Botte");
			ps3.setDate(3, data);


			ps1.executeUpdate();

			ps2.executeUpdate();
			ps3.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		VigileDelFuocoDao.aggiornaFeriePrecedenti("michele.granato@vigilfuoco.it", 0);
		VigileDelFuocoDao.aggiornaFerieCorrenti("michele.granato@vigilfuoco.it", 0);
	}
	
	@BeforeAll
	static void setUpBean() throws Exception {
		malattia = new GiorniMalattiaBean();
		malattia.setDataInizio(Date.valueOf("2020-03-15"));
		malattia.setDataFine(Date.valueOf("2020-03-20"));
		malattia.setEmailCT("capoturno");
		malattia.setEmailVF("rosario.marmo@vigilfuoco.it");
		
		malattia2 = new GiorniMalattiaBean();
		malattia2.setDataInizio(Date.valueOf("2020-03-15"));
		malattia2.setDataFine(Date.valueOf("2020-03-20"));
		malattia2.setEmailCT("capoturno");
		malattia2.setEmailVF("salvatore.malaspina@vigilfuoco.it");
		
		malattia3 = new GiorniMalattiaBean();
		malattia3.setDataInizio(Date.valueOf("2020-03-15"));
		malattia3.setDataFine(Date.valueOf("2020-03-20"));
		malattia3.setEmailCT("capoturno");
		malattia3.setEmailVF("michele73.sica@vigilfuoco.it");
		
		malattia4 = new GiorniMalattiaBean();
		malattia4.setDataInizio(Date.valueOf("2020-03-15"));
		malattia4.setDataFine(Date.valueOf("2020-03-20"));
		malattia4.setEmailCT("capoturno");
		malattia4.setEmailVF("michele.granato@vigilfuoco.it");
		
		malattia5 = new GiorniMalattiaBean();
		malattia5.setDataInizio(Date.valueOf("2020-03-15"));
		malattia5.setDataFine(Date.valueOf("2020-03-20"));
		malattia5.setEmailCT("capoturno");
		malattia5.setEmailVF("mario.delregno@vigilfuoco.it");
		
		malattia6 = new GiorniMalattiaBean();
		malattia6.setDataInizio(Date.valueOf("2020-03-15"));
		malattia6.setDataFine(Date.valueOf("2020-03-20"));
		malattia6.setEmailCT("capoturno");
		malattia6.setEmailVF("alberto.frosinone@vigilfuoco.it");
		
		malattia7 = new GiorniMalattiaBean();
		malattia7.setDataInizio(Date.valueOf("2020-03-15"));
		malattia7.setDataFine(Date.valueOf("2020-03-20"));
		malattia7.setEmailCT("capoturno");
		malattia7.setEmailVF("franco.mammato@vigilfuoco.it");
		
		malattia8 = new GiorniMalattiaBean();
		malattia8.setDataInizio(Date.valueOf("2020-03-15"));
		malattia8.setDataFine(Date.valueOf("2020-03-20"));
		malattia8.setEmailCT("capoturno");
		malattia8.setEmailVF("domenico.giordano@vigilfuoco.it");
	}


	@Test
	public void test_autenticazioneFallia1() throws ServletException, IOException, ParseException{
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}



	@Test
	void test_autenticazioneFallita2() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("email", "luca@vigilfuoco.it");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}


	@Test
	void test_inserimentoGiorniFerie()throws ServletException, IOException {

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataIniziale", "06-03-2020");
		request.setParameter("dataFinale", "20-03-2020");
		session.setAttribute("capoturno", capoturno);

		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}

	@Test
	void test_numeroGiorniPeriodoNonLavorativo()throws ServletException, IOException {

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataIniziale", "08-03-2020");
		request.setParameter("dataFinale", "08-03-2020");
		session.setAttribute("capoturno", capoturno);

		assertThrows(ScheduFIREException.class, ()->{
			servlet.doPost(request, response);
		});
	}

	@Test
	void test_personaleInsufficiente()throws ServletException, IOException {

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "alberto.barbarulo@vigilfuoco.it");
		request.setParameter("dataIniziale", "06-03-2020");
		request.setParameter("dataFinale", "08-03-2020");
		session.setAttribute("capoturno", capoturno);
		servlet.doPost(request, response);

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("email", "carmine.sarraino@vigilfuoco.it");
		request.setParameter("dataIniziale", "06-03-2020");
		request.setParameter("dataFinale", "08-03-2020");
		session.setAttribute("capoturno", capoturno);

		assertDoesNotThrow(()->{servlet.doPost(request, response);});


		Date dataInizio = Date.valueOf("2020-03-06");
		Date dataFine = Date.valueOf("2020-03-08");
		FerieDao.rimuoviPeriodoFerie("alberto.barbarulo@vigilfuoco.it", dataInizio, dataFine);
		FerieDao.rimuoviPeriodoFerie("carmine.sarraino@vigilfuoco.it", dataInizio, dataFine);

	}
	
	@Test
	void test_personaleInsufficienteTrue()throws ServletException, IOException {
		this.inserimentoMalattie(malattia);
		this.inserimentoMalattie(malattia2);
		this.inserimentoMalattie(malattia3);
		this.inserimentoMalattie(malattia4);
		this.inserimentoMalattie(malattia5);

		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "alberto.barbarulo@vigilfuoco.it");
		request.setParameter("dataIniziale", "15-03-2020");
		request.setParameter("dataFinale", "20-03-2020");
		session.setAttribute("capoturno", capoturno);
		servlet.doPost(request, response);

		assertDoesNotThrow(() -> {servlet.doPost(request, response);});

	}

	@Test
	void test_inserimentoGiorniFerieisComponente()throws ServletException, IOException {

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataIniziale", "17-03-2020");
		request.setParameter("dataFinale", "20-03-2020");
		session.setAttribute("capoturno", capoturno);

		servlet.doPost(request, response);
		assertEquals("application/json", response.getContentType());
	}
	@Test
	void test_inserimentoGiorniFerieInsufficienti()throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "michele.granato@vigilfuoco.it");
		request.setParameter("dataIniziale", "06-03-2020");
		request.setParameter("dataFinale", "08-03-2020");
		session.setAttribute("capoturno", capoturno);

		assertThrows(ScheduFIREException.class, ()->{servlet.doPost(request, response);});
	}


	@AfterEach
	protected void tearDown() throws SQLException {
		Date dataInizio = Date.valueOf("2020-03-02");
		Date dataFine = Date.valueOf("2020-03-06");
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", dataInizio, dataFine);

		Date data = Date.valueOf("2020-03-17");

		String sql1 ="DELETE FROM ListaSquadre WHERE giornoLavorativo = ? ;";
		String sql2 ="DELETE FROM Squadra WHERE giornoLavorativo = ? ;";
		String sql3 ="DELETE FROM ComponenteDellaSquadra WHERE giornoLavorativo = ? ;";

		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps1 = con.prepareStatement(sql1);
			PreparedStatement ps2 = con.prepareStatement(sql2);
			PreparedStatement ps3 = con.prepareStatement(sql3);
			ps1.setDate(1, data);
			ps2.setDate(1, data);
			ps3.setDate(1, data);


			ps1.executeUpdate();
			ps2.executeUpdate();
			ps3.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		VigileDelFuocoDao.aggiornaFeriePrecedenti("michele.granato@vigilfuoco.it", 5);
		VigileDelFuocoDao.aggiornaFerieCorrenti("michele.granato@vigilfuoco.it", 20);
		
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("rosario.marmo@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("salvatore.malaspina@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("michele73.sica@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("michele.granato@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.delregno@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("alberto.frosinone@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("franco.mammato@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("domenico.giordano@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
	}
	
	private void inserimentoMalattie(GiorniMalattiaBean giorniMalattia) {
		GiorniMalattiaDao.addMalattia(giorniMalattia);
	}

	private static GiorniMalattiaBean malattia;
	private static GiorniMalattiaBean malattia2;
	private static GiorniMalattiaBean malattia3;
	private static GiorniMalattiaBean malattia4;
	private static GiorniMalattiaBean malattia5;
	private static GiorniMalattiaBean malattia6;
	private static GiorniMalattiaBean malattia7;
	private static GiorniMalattiaBean malattia8;
}


