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
import control.RimuoviFerieServlet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import junit.framework.TestCase;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;


class RimuoviferieServletTest{
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	
	static RimuoviFerieServlet servlet;

	@BeforeEach
	public void setUp() throws Exception {
		servlet = new RimuoviFerieServlet();
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		
		Date dataInizio = Date.valueOf("2020-03-02");
		Date dataFine = Date.valueOf("2020-03-06");
		FerieDao.aggiungiPeriodoFerie("capoturno", "luca.raimondi@vigilfuoco.it", dataInizio, dataFine);
	}
	
	@Test
	public void test_autenticazioneFallia1() throws ServletException, IOException, ParseException{
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void test_autenticazioneFallita2() throws ServletException, IOException {
		((MockHttpServletRequest) request).setSession(session);
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void test_autenticazioneFallita3() throws ServletException, IOException {
		((MockHttpServletRequest) request).setSession(session);
		request.getSession().setAttribute("email", "luca@vigilfuoco.it");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void test_giorniGiaConcessi()throws ServletException, IOException {
		String dataInizio = "02-03-2020";
		String dataFine = "06-03-2020";
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "luca.raimondi@vigilfuoco.it");
		request.getSession().setAttribute("dataIniziale", "02-03-2020");
		request.getSession().setAttribute("dataFinale", "06-03-2020");
		servlet.doPost(request, response);
		
		
		assertEquals("JSP/GestioneFerieJSP.jsp",response.getForwardedUrl());
	}

	

	
	
	@AfterEach
	protected void tearDown() throws SQLException {
		Date dataInizio = Date.valueOf("2020-03-02");
		Date dataFine = Date.valueOf("2020-03-06");
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", dataInizio, dataFine);
	}

}
