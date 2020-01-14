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
import model.bean.GiorniMalattiaBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
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
	
	@BeforeAll
	static void setUpBean() throws Exception {
		malattia = new GiorniMalattiaBean();
		malattia.setDataInizio(Date.valueOf("2020-03-15"));
		malattia.setDataFine(Date.valueOf("2020-03-20"));
		malattia.setEmailCT("capoturno");
		malattia.setEmailVF("luca.raimondi@vigilfuoco.it");
		
		malattia2 = new GiorniMalattiaBean();
		malattia2.setDataInizio(Date.valueOf("2020-03-15"));
		malattia2.setDataFine(Date.valueOf("2020-03-20"));
		malattia2.setEmailCT("capoturno");
		malattia2.setEmailVF("luca.raimondi@vigilfuoco.it");
	}
	
	@Test
	void autenticazioneRuoloErrato() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "vigile");
		
		assertThrows(AutenticazioneException.class, () -> {
			servlet.doPost(request, response);
		});
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
	void aggiuntaJSONnull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("aggiunta", null);
		request.setParameter("ordinamento", "grado");
		
		servlet.doGet(request, response);
		
		assertEquals("JSP/GestioneFerieJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void aggiuntaNullJSONtrue() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.getSession().setAttribute("aggiunta", null);
		request.setParameter("ordinamento", "grado");
		
		servlet.doGet(request, response);
		
		assertEquals("JSP/GestioneFerieJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void aggiuntaTrueJSONnull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("aggiunta", "true");
		request.setParameter("ordinamento", "grado");
		
		servlet.doGet(request, response);
		
		assertEquals("JSP/GestioneFerieJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void aggiuntaTrueJSONtrue() throws ServletException, IOException {
		this.inserimentoFerie();
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("aggiunta", "true");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doGet(request, response);
		
		assertEquals("application/json", response.getContentType());
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
	
	@Test
	void giorniMalattieNotNull() throws ServletException, IOException {
		this.inserimentoMalattie(malattia);
		this.inserimentoMalattie(malattia2);
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("aggiunta", "true");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doGet(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void rimozioneJSONnull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("rimozione", null);
		request.setParameter("ordinamento", "cognome");
		
		servlet.doPost(request, response);
		
		assertEquals("JSP/GestioneFerieJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void rimozioneNotNullJSONnull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.setParameter("rimozione", "true");
		request.setParameter("ordinamento", "cognome");
		
		servlet.doPost(request, response);
		
		assertEquals("JSP/GestioneFerieJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void rimozioneNull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", "true");
		request.getSession().setAttribute("rimozione", null);
		request.setParameter("ordinamento", "cognome");
		
		servlet.doPost(request, response);
		
		assertEquals("JSP/GestioneFerieJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void dataFinaleNull() throws ServletException, IOException {
		this.inserimentoFerie();
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("rimozione", "true");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		
		servlet.doPost(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@AfterEach
	void tearDown() throws Exception {
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", 
				Date.valueOf("2020-03-23"), Date.valueOf("2020-03-25"));
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", 
				Date.valueOf("2020-03-02"), Date.valueOf("2020-03-02"));
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", 
				Date.valueOf("2020-03-06"), Date.valueOf("2020-03-20"));
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("luca.raimondi@vigilfuoco.it", 
									malattia.getDataInizio(), malattia.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("luca.raimondi@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
	}
	
	private void inserimentoMalattie(GiorniMalattiaBean giorniMalattia) {
		GiorniMalattiaDao.addMalattia(giorniMalattia);
	}
	
	private void inserimentoFerie() {
		FerieDao.aggiungiPeriodoFerie("capoturno", "luca.raimondi@vigilfuoco.it",
								Date.valueOf("2020-03-23"), Date.valueOf("2020-03-25"));
	}

	private static GiorniMalattiaBean malattia;
	private static GiorniMalattiaBean malattia2;
}
