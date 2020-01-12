package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.AggiungiFerieServlet;
import control.AggiungiMalattiaServlet;
import model.bean.CapoTurnoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import util.Notifiche;

class AggiungiFerieServletTestBlackBox {

	private static AggiungiFerieServlet servlet;
	private static MockHttpServletRequest request;
	private static MockHttpServletResponse response;
	private static MockHttpSession session;
	private CapoTurnoBean capoturno;
	
	@BeforeEach
	void setUp() throws Exception {
		session = new MockHttpSession();
		servlet = new AggiungiFerieServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		capoturno = new CapoTurnoBean("capoturno","capoturno","capoturno", "B", "capoturno");
	}
	@Test
	void testCase_6_1() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataIniziale", " ");
		request.setParameter("dataFinale", "20-03-2020");
		session.setAttribute("capoturno", capoturno);
		
		assertThrows(StringIndexOutOfBoundsException.class, ()-> {servlet.doGet(request, response);});
	}
	
	@Test
	void testCase_6_2() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataIniziale", "06-03-2020");
		request.setParameter("dataFinale", " ");
		session.setAttribute("capoturno", capoturno);
		
		assertThrows(StringIndexOutOfBoundsException.class, ()-> {servlet.doGet(request, response);});

	}
	
	
	@Test
	void testCase_6_3() throws ServletException, IOException {

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
	
	
	@AfterEach
	void tearDown() throws Exception {
		FerieDao.rimuoviPeriodoFerie("alberto.barbarulo@vigilfuoco.it", Date.valueOf("2020-03-06"), Date.valueOf("2020-03-20"));
	}

}
