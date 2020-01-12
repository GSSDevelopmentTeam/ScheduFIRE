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

import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import control.GestioneFerieServlet;
import control.RimuoviFerieServlet;

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
import util.Notifiche;

class GestioneFerieServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static GestioneFerieServlet servlet;

	
	
	@BeforeEach
	public void setUp() throws Exception {
		servlet = new GestioneFerieServlet();
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
	}
	@Test
	public void test_autenticazioneFallia1() throws ServletException, IOException, ParseException{
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void test_visualizzaFerieDaConcedere() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("JSON", "true");
		request.setParameter("aggiunta", "true");
		
		servlet.doPost(request, response);
		 assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void test_visualizzaFerieConcesse() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("JSON", "true");
		request.setParameter("rimozione", "true");
		
		servlet.doPost(request, response);
		 assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void test_gestioneFerieJsp() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void test_JspOrdinamentoNome() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("ordinamento", "nome");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void test_JspOrdinamentoCognome() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("ordinamento", "cognome");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void test_JspOrdinamentoMansione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("ordinamento", "mansione");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}
	@Test
	void test_JspOrdinamentoFiorniFerie() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("ordinamento", "giorniFerie");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}
	@Test
	void test_JspOrdinamentoGiorniFerieAnnoPrecedente() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("ordinamento", "giorniFerieAnnoPrecedente");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}
	@Test
	void test_JspOrdinamentoGrado() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setParameter("ordinamento", "grado");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}


}
