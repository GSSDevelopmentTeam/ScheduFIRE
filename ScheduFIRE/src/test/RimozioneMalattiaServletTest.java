package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.RimozioneMalattiaServlet;
import model.bean.CapoTurnoBean;
import model.bean.GiorniMalattiaBean;
import model.dao.GiorniMalattiaDao;
import util.Notifiche;

class RimozioneMalattiaServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static RimozioneMalattiaServlet servlet;

	@BeforeAll
	static void setup() {
		servlet = new RimozioneMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();	
	}

	@BeforeEach 
	void autentica() {
		GiorniMalattiaDao.addMalattia(new GiorniMalattiaBean(10, Date.valueOf("2020-05-15"),
				Date.valueOf("2020-05-30"), "capoturno", "mario.buonomo@vigilfuoco.it"));
		servlet = new RimozioneMalattiaServlet();
		session.setAttribute("ruolo", "capoturno");
		session.setAttribute("capoturno", new CapoTurnoBean("capoturno", "capoturno", "capoturno", "B", "capoturno"));
		session.setAttribute("notifiche", new Notifiche());
		request.setSession(session);
	}

	@AfterEach
	void teardown() {
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.buonomo@vigilfuoco.it", Date.valueOf("2020-05-15"),
				Date.valueOf("2020-05-30"));
	}

	@AfterEach
	void reset() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		servlet.destroy();
	}

	@Test
	void testInvalido() throws ServletException, IOException {
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataIniziale", "15-05-2020");
		request.setParameter("dataFinale", "14-05-2020");
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testStessogiorno() throws ServletException, IOException {
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataIniziale", "15-05-2020");
		request.setParameter("dataFinale", "15-05-2020");
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testNoSelezione() throws ServletException, IOException {
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataIniziale", "01-05-2020");
		request.setParameter("dataFinale", "01-05-2020");
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}

	@Test
	void rimozioneParteMalattia() throws ServletException, IOException {
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataIniziale", "16-05-2020");
		request.setParameter("dataFinale", "20-05-2020");
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}

	@Test
	void rimozioneParteDaFuori() throws ServletException, IOException {
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataIniziale", "16-05-2020");
		request.setParameter("dataFinale", "30-05-2020");
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}

	@Test
	void testaFunzionalita() throws ServletException, IOException {
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataIniziale", "15-05-2020");
		request.setParameter("dataFinale", "30-05-2020");
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}


}
