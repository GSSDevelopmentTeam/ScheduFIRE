package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.google.protobuf.TextFormat.ParseException;

import control.AggiungiFerieServlet;
import control.AutenticazioneException;
import control.RimuoviFerieServlet;
import junit.framework.TestCase;
import model.ConnessioneDB;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;

class AggiungiFerieServletTest extends TestCase{
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	
	static AggiungiFerieServlet servlet;

	@BeforeEach
	public void setUp() throws Exception {
		servlet = new AggiungiFerieServlet();
		request = new MockHttpServletRequest();
		response= new MockHttpServletResponse();
		session = new MockHttpSession();
	}
	@Test
	public void test_autenticazioneFallia1() throws ServletException, IOException, ParseException{
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void test_autenticazioneFallita2() throws ServletException, IOException {
		request.setSession(session);
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}

	@Test
	void test_autenticazioneFallita3() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("email", "luca@vigilfuoco.it");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void test_inserimentoGiorniFerie()throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataIniziale", "02-03-2020");
		request.setParameter("dataFinale", "06-03-2020");
		servlet.doPost(request, response);
		assertEquals("application/json", response.getContentType());
	}

	
	@AfterEach
	protected void tearDown() throws SQLException {
		Date dataInizio = Date.valueOf("2020-03-02");
		Date dataFine = Date.valueOf("2020-03-06");
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", dataInizio, dataFine);
	}

}
