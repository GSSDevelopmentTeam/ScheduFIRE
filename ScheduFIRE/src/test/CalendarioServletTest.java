package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.CalendarioServlet;
import model.bean.CapoTurnoBean;

class CalendarioServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static CalendarioServlet servlet;
	static Date data;
	static CapoTurnoBean ct;
	

	@BeforeEach
	void setUp() throws Exception {
		servlet = new CalendarioServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		data=Date.valueOf("2020-02-02");
		
		ct= new CapoTurnoBean("capoturno","capoturno","capoturno","B","capoturno");
		
	    request.setSession(session);
	    request.getSession().setAttribute("ruolo", "capoturno");
	    request.getSession().setAttribute("capoturno", ct);
	    request.getSession().setAttribute("notifiche", "notifiche");
	    request.getSession().setAttribute("email", "capoturno");
	    servlet.doPost(request, response);
		
		
	}
	
	@Test
	void autentificazione() throws ServletException, IOException {
		request.setSession(session);
		request.setAttribute("ruolo", "capoturno");
		servlet.doGet(request, response);
		assertEquals("JSP/CalendarioJSP.jsp",
				response.getForwardedUrl());

	}
	
	@Test
	void autentificazioneFallita() throws ServletException, IOException {
		request.setSession(session);
	    request.getSession().setAttribute("ruolo", "");
	    request.getSession().setAttribute("capoturno", ct);
	    request.getSession().setAttribute("notifiche", "notifiche");
	    request.getSession().setAttribute("email", "capoturno");
	    servlet.doPost(request, response);
		assertEquals("JSP/CalendarioJSP.jsp",
				response.getForwardedUrl());

	}
	
	
	@Test
	void annoNonNullo() throws ServletException, IOException {
		request.setSession(session);
		request.setAttribute("ruolo", "capoturno");
		request.setParameter("anno","2020");
		servlet.doGet(request, response);
		assertEquals("JSP/CalendarioJSP.jsp",
				response.getForwardedUrl());
	}
	
	
	@Test
	void meseNonNullo() throws ServletException,IOException{
		request.setSession(session);
		request.setAttribute("ruolo", "capoturno");
		request.setParameter("mese","1");
		servlet.doGet(request, response);
		assertEquals("JSP/CalendarioJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void isBisestile() throws ServletException,IOException {
		request.setSession(session);
		request.setAttribute("ruolo","capoturno");
		request.setAttribute("date","data");
		assertEquals("JSP/CalendarioJSP.jsp",
				response.getForwardedUrl());
	}
	
	
	
		
		
		
	
	
	
	


}
