package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.*;

import control.AggiungiMalattiaServlet;
import control.ScheduFIREException;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.Notifiche;


class AggiungiMalattiaServletTest {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static AggiungiMalattiaServlet servlet;
	
	@BeforeAll
	static void setup() {
		servlet = new AggiungiMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		
	}

	@BeforeEach 
	void autentica() {
		servlet = new AggiungiMalattiaServlet();
		session.setAttribute("ruolo", "capoturno");
		session.setAttribute("capoturno", new CapoTurnoBean("capoturno", "capoturno", "capoturno", "B", "capoturno"));
		session.setAttribute("notifiche", new Notifiche());
		request.setSession(session);
	}
	
	@AfterEach
	void reset() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		servlet.destroy();
	}
	
	@AfterAll 
	static void remove() {
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.buonomo@vigilfuoco.it", Date.valueOf("2020-05-15"), Date.valueOf("2020-05-30"));
	}
	
	@Test
	void noOp() throws ServletException, IOException {
		servlet.doGet(request, response);
		assertFalse("application/json".equals(response.getContentType()));
	}
	
	@Test
	void aggiuntaMalattia() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "aaa");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONtrueInserisciNull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.getSession().setAttribute("inserisci", null);
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals(null, response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONnullInseriscitrue() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals(null, response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONtrueInseriscitrue() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("inserisci", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals(null, response.getContentType());
	}
	
	@Test 
	void malattiaGiaAggiunta() throws ServletException, IOException {
		request.setParameter("JSON", "aaa");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		assertDoesNotThrow(() -> {
			servlet.doGet(request, response);
		}); 
	}

}
