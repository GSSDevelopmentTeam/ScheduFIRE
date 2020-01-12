package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.google.protobuf.TextFormat.ParseException;

import control.AggiungiFerieServlet;
import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import control.RimuoviFerieServlet;
import control.ScheduFIREException;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import junit.framework.TestCase;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.CapoTurnoDao;
import model.dao.FerieDao;
import util.Notifiche;


class AggiungiFerieServletTest extends TestCase{
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static AggiungiFerieServlet servlet;
	static CapoTurnoBean capoturno;


	@BeforeEach
	public void setUp() throws Exception {
		servlet = new AggiungiFerieServlet();
		request = new MockHttpServletRequest();
		response= new MockHttpServletResponse();
		session = new MockHttpSession();
		
		capoturno = new CapoTurnoBean("capoturno","capoturno","capoturno", "B", "capoturno");
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
		request.getSession().setAttribute("notifiche", new Notifiche());
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
		request.getSession().setAttribute("notifiche", new Notifiche());
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

	
	@AfterEach
	protected void tearDown() throws SQLException {
		Date dataInizio = Date.valueOf("2020-03-02");
		Date dataFine = Date.valueOf("2020-03-06");
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", dataInizio, dataFine);
	}

}
