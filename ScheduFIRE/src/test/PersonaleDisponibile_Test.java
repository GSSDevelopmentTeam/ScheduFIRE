package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import control.PersonaleDisponibileServlet;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
/**
 * Test per la verifica di PersonaleDisponibileServlet.java
 * @author Francesca Pia Perillo
 *
 */
class PersonaleDisponibile_Test {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static PersonaleDisponibileServlet servlet;
	
	static CapoTurnoBean ct = new CapoTurnoBean();
	String data = "05-01-2020";
	
	@BeforeEach
	void setUp() throws ServletException, IOException {
		servlet = new PersonaleDisponibileServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		
		ct= new CapoTurnoBean("capoturno","capoturno","capoturno","B","capoturno");
		
	    request.setSession(session);
	    request.getSession().setAttribute("ruolo", "capoturno");
	    request.getSession().setAttribute("capoturno", ct);
	    request.getSession().setAttribute("notifiche", "notifiche");
	    request.getSession().setAttribute("email", "capoturno");
	    servlet.doPost(request, response);
	}
	

	
	@Test
	void fail0_2() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "vigile");
		assertThrows(AutenticazioneException.class, ()->
			{servlet.doPost(request, response);});
	}
	
	
	@Test
	void test0_0() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_2() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_3() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data);
		request.setParameter("ordinamento","nome");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_4() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data);
		request.setParameter("ordinamento","cognome");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_5() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data);
		request.setParameter("ordinamento","grado");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_6() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data);
		request.setParameter("ordinamento","disponibilita");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	

	
	@Test
	void test0_7() throws ServletException, IOException {
		request.setSession(session);
		request.setParameter("JSON", "json");
		request.setParameter("mese","1");
		request.setParameter("anno","2020");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}

}
