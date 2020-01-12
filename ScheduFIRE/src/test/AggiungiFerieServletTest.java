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
import org.junit.jupiter.api.BeforeEach;


import junit.framework.TestCase;
import model.ConnessioneDB;
import model.bean.CapoTurnoBean;
import model.bean.ComponenteDellaSquadraBean;
import model.dao.FerieDao;
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

		servlet.doPost(request, response);
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

		assertThrows(ScheduFIREException.class, ()->{servlet.doPost(request, response);});


		Date dataInizio = Date.valueOf("2020-03-06");
		Date dataFine = Date.valueOf("2020-03-08");
		FerieDao.rimuoviPeriodoFerie("alberto.barbarulo@vigilfuoco.it", dataInizio, dataFine);
		FerieDao.rimuoviPeriodoFerie("carmine.sarraino@vigilfuoco.it", dataInizio, dataFine);

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
	}

}


