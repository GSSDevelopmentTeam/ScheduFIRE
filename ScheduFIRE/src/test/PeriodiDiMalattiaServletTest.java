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
import org.springframework.test.util.ReflectionTestUtils;

import control.AutenticazioneException;
import control.PeriodiDiMalattiaServlet;

class PeriodiDiMalattiaServletTest {

	@BeforeEach
	void setUp() throws Exception {
		servlet = new PeriodiDiMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		sessione = new MockHttpSession();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void autenticazioneEffettuata() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		
		servlet.doPost(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void autenticazioneRuoloErrato() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "vigile");
		
		assertThrows(AutenticazioneException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	
	@Test
	void parameterNull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		
		servlet.doGet(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	private static PeriodiDiMalattiaServlet servlet;
	private static MockHttpServletRequest request;
	private static MockHttpServletResponse response;
	private static MockHttpSession sessione;
}
