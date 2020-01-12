package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import control.AutenticazioneException;
import control.PeriodiDiMalattiaServlet;
import control.ScheduFIREException;
import model.bean.GiorniMalattiaBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;

class PeriodiDiMalattiaServletTest {

	@BeforeEach
	void setUp() throws Exception {
		servlet = new PeriodiDiMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		sessione = new MockHttpSession();
	}
	
	@BeforeAll
	static void setUpBean() throws Exception {
		malattia = new GiorniMalattiaBean();
		malattia.setDataInizio(Date.valueOf("2020-03-15"));
		malattia.setDataFine(Date.valueOf("2020-03-20"));
		malattia.setEmailCT("capoturno");
		malattia.setEmailVF("alberto.barbarulo@vigilfuoco.it");
		
		malattia2 = new GiorniMalattiaBean();
		malattia2.setDataInizio(Date.valueOf("2020-03-15"));
		malattia2.setDataFine(Date.valueOf("2020-03-20"));
		malattia2.setEmailCT("capoturno");
		malattia2.setEmailVF("alberto.barbarulo@vigilfuoco.it");
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
	void ordinamentoNome() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("visMalattia", null);
		request.setParameter("ordinamento", "nome");
  
		servlet.doGet(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void ordinamentoGrado() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("visMalattia", null);
		request.setParameter("ordinamento", "grado");
		
		servlet.doGet(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void emailVFvisMalattianotNull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("visMalattia", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		
		servlet.doPost(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void emailVFvisMalattiaNull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("visMalattia", "true");
		request.getSession().setAttribute("emailVF", null);
		
		assertThrows(ScheduFIREException.class, () ->{
			servlet.doPost(request, response);
		});
	}
	
	@Test
	void giorniMalattieFerieNotNull() throws ServletException, IOException {
		this.inserimentoMalattie(malattia);
		this.inserimentoFerie();
		
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("visMalattia", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		
		servlet.doPost(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void rimozioneNotNull() throws ServletException, IOException {
		this.inserimentoMalattie(malattia2);
		
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("rimozione", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		
		servlet.doPost(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void rimozioneNullJSONnotNull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.getSession().setAttribute("rimozione", null);
		request.setParameter("ordinamento", "cognome");
		
		servlet.doPost(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void rimozioneNotNullJSONnull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.setParameter("rimozione", "true");
		request.setParameter("ordinamento", "cognome");
		
		servlet.doPost(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void rimozioneNull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", "true");
		request.getSession().setAttribute("rimozione", null);
		request.setParameter("ordinamento", "cognome");
		
		servlet.doPost(request, response);
		
		assertEquals("/JSP/GestioneMalattiaJSP.jsp", response.getForwardedUrl());
	}
	
	@Test
	void dataFinaleNull() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("rimozione", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		
		servlet.doPost(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	@AfterEach
	void tearDown() throws Exception {
		FerieDao.rimuoviPeriodoFerie("alberto.barbarulo@vigilfuoco.it", 
				Date.valueOf("2020-03-23"), Date.valueOf("2020-03-25"));
		
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("alberto.barbarulo@vigilfuoco.it", 
									malattia.getDataInizio(), malattia.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("alberto.barbarulo@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
	}
	
	private void inserimentoMalattie(GiorniMalattiaBean giorniMalattia) {
		GiorniMalattiaDao.addMalattia(giorniMalattia);
	}
	
	private void inserimentoFerie() {
		FerieDao.aggiungiPeriodoFerie("capoturno", "alberto.barbarulo@vigilfuoco.it",
								Date.valueOf("2020-03-23"), Date.valueOf("2020-03-25"));
	}
	
	private static PeriodiDiMalattiaServlet servlet;
	private static MockHttpServletRequest request;
	private static MockHttpServletResponse response;
	private static MockHttpSession sessione;
	private static GiorniMalattiaBean malattia;
	private static GiorniMalattiaBean malattia2;
}
