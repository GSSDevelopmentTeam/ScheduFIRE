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
	String data1 = "05-01-2020";
	String data2 = "05-02-2020";
	String data3 = "05-03-2020";
	String data4 = "05-04-2020";
	String data5 = "05-05-2020";
	String data6 = "05-06-2020";
	String data7 = "05-07-2020";
	String data8 = "05-08-2020";
	String data9 = "05-09-2020";
	String data10 = "05-10-2020";
	String data11 = "05-11-2020";
	String data12 = "05-12-2020";
	
	
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
		request.setParameter("data", data1);
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
		request.setParameter("data", data2);
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
		request.setParameter("data", data3);
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
		request.setParameter("data", data4);
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
		request.setParameter("data", data5);
		request.setParameter("ordinamento","disponibilita");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_7() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data6);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_8() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data7);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test0_9() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data8);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test1_0() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data9);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test1_1() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data10);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	@Test
	void test1_2() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data11);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	
	@Test
	void test1_3() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("JSON", 1);
		request.getSession().setAttribute("mese", 1);
		request.getSession().setAttribute("anno", 2020);
		request.setParameter("data", data12);
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}
	

	@Test
	void test1_5() throws ServletException, IOException {
		request.setSession(session);
		request.setParameter("JSON", "json");
		request.setParameter("mese","1");
		request.setParameter("anno","2020");
		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileJSP.jsp",
				response.getForwardedUrl());
	}

}
