package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;

import control.GeneraSquadreServlet;
import control.PersonaleServlet;
import control.RimuoviFerieServlet;
import model.bean.CapoTurnoBean;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;

class PersonaleServletTest {

	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static PersonaleServlet servlet;
	static HashMap<VigileDelFuocoBean, String> squadra;
	VigileDelFuocoBean vigile;
	static CapoTurnoBean capoturno;
	static Date data;
	static Date dataNonInDB;
	static Date giornoSuccessivo;
	
	
	@BeforeAll
	static void setUpAll() {
		servlet = new PersonaleServlet();
		data=Date.valueOf("2020-03-06");
	
		giornoSuccessivo=Date.valueOf("2020-03-07");
		capoturno= new CapoTurnoBean("capoturno","capoturno","capoturno","B","capoturno");
		squadra=new HashMap<>();
		VigileDelFuocoBean vigile=new VigileDelFuocoBean("Domenico", "Giordano", "domenico.giordano@vigilfuoco.it", "B", "vigile", "turnoB",
				"Esperto", 0, 0);
		vigile.setCaricoLavoro(0);
		squadra.put(vigile, "Sala Operativa");
		squadra.put(vigile, "Prima Partenza");
		squadra.put(vigile, "Auto Scala");
		squadra.put(vigile, "Auto Botte");
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
	}
	
	@Test
	void test_rimozioneGiorniDiFerie()throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.setParameter("email", "luca.raimondi@vigilfuoco.it");
		request.setParameter("dataDiurno", "2020-03-06");
		request.setParameter("dataNotturno", "2020-03-06");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		
		System.out.println(request.getParameter("dataDiurno"));
		servlet.doPost(request, response);
		 assertEquals("JSP/PersonaleJSP.jsp", response.getForwardedUrl());
	}

}
