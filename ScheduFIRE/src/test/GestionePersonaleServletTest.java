package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.GeneraSquadreServlet;
import control.GestionePersonaleServlet;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;

class GestionePersonaleServletTest {

	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static GestionePersonaleServlet servlet;
	static ArgumentCaptor<String> captor;
	static CapoTurnoBean ct = new CapoTurnoBean();

	@BeforeEach
	void setUp() {
		servlet = new GestionePersonaleServlet ();
		captor = ArgumentCaptor.forClass(String.class);
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		ct.setEmail("capoturno");
		
	}
	
	/**
	 * Questo metodo serve per verificare se nella servlet viene eseguita l'autenticazione 
	 * in caso di fallimento viene lanciata un eccezzione.
	 */
	@Test
	void autenticazione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		servlet.doPost(request, response);
	}

	/**
	 * Questo metodo setta un parametro per "ordinamento", 
	 * il parametro ordinamento non viene passato correttamente
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void ordinamentotestfailvoid() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("ordinament","nome");
		servlet.doPost(request, response);
		assertEquals("/JSP/GestionePersonaleJSP.jsp",response.getForwardedUrl());
	}
	/**
	 * Questo metodo setta un parametro per "ordinamento", 
	 * il parametro ordinamento passato e' "nome"
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void ordinamentotestnome() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("ordinamento","nome");
		servlet.doPost(request, response);
		assertEquals("/JSP/GestionePersonaleJSP.jsp",response.getForwardedUrl());
	}
	/**
	 * Questo metodo setta un parametro per "ordinamento", 
	 * il parametro ordinamento passato e' "cognome"
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void ordinamentotestcognome() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("ordinamento","cognome");
		servlet.doPost(request, response);
		assertEquals("/JSP/GestionePersonaleJSP.jsp",response.getForwardedUrl());
	}
	/**
	 * Questo metodo setta un parametro per "ordinamento", 
	 * il parametro ordinamento passato e' "caricoLavoro"
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void ordinamentotestcarcoLavoro() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.setParameter("ordinamento","caricoLavoro");
		servlet.doPost(request, response);
		assertEquals("/JSP/GestionePersonaleJSP.jsp",response.getForwardedUrl());
	}
	/**
	 * Questo metodo setta un parametro per "ordinamento", 
	 * il parametro ordinamento passato e'"ferie"
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void ordinamentotestferie() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.getSession().setAttribute("ordinamento","ferie");
		servlet.doPost(request, response);
		assertEquals("/JSP/GestionePersonaleJSP.jsp",response.getForwardedUrl());
	}
}
