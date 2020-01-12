package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.*;

import control.AutenticazioneException;
import control.RimuoviNotificheServlet;
import util.Notifica;
import util.Notifiche;

class RimuoviNotificheServletTest {
	private static RimuoviNotificheServlet servlet;
	private static MockHttpServletRequest request;
	private static MockHttpServletResponse response;
	private static MockHttpSession session;
	
	@BeforeAll
	static void setup() {
		servlet = new RimuoviNotificheServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
	}
	
	@BeforeEach
	void beforeEach() {
		servlet = new RimuoviNotificheServlet();
		session = new MockHttpSession();
	}
	
//	@AfterEach
//	void destroy() {
//		session.invalidate();
//		servlet.destroy();
//	}
	
	@Test
	void noAutenticazione() {
		assertThrows(AutenticazioneException.class, () -> {
			servlet.doGet(request, response);
		});
	}

	@Test
	void noAutenticazione2() {
		request.setSession(session);
		assertThrows(AutenticazioneException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test
	void noCapoTurno() {
		session.setAttribute("ruolo", "vigile");
		request.setSession(session);
		assertThrows(AutenticazioneException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test
	void autenticatoNoNotifiche() {
		session.setAttribute("ruolo", "capoturno");
		request.setSession(session);
		assertThrows(AutenticazioneException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test
	void autenticatoNoIDNotifiche() {
		session.setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setSession(session);	
		request.setParameter("indice", "Numero");
		assertThrows(NumberFormatException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test
	void idNotificheSbagliato() throws ServletException, IOException {
		Notifiche oracolo = new Notifiche();
		oracolo.addNotifica(new Notifica(1, "Oracolo", "Path", 10));
	
		session.setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", oracolo);
		request.setSession(session);
		request.setParameter("indice", "100");
		servlet.doGet(request, response);
		
		assertTrue(oracolo.equals(session.getAttribute("notifiche")));
	}
		
}
