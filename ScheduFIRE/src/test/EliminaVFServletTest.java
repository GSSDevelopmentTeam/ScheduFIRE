package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.EliminaVFServlet;
import control.ModificaVFServlet;
import control.ParametroInvalidoException;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;

class EliminaVFServletTest {

	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static ArgumentCaptor<String> captor;
	static EliminaVFServlet servlet;
	static CapoTurnoBean ct = new CapoTurnoBean();
	static String email="dematteo.antonio";

	@BeforeEach
	void setUp() {
		servlet = new EliminaVFServlet();
		captor = ArgumentCaptor.forClass(String.class);
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		ct.setTurno("B");
		ct.setEmail("capoturno");
	}

	/**
	 * Questo metodo serve per verificare se nella servlet viene eseguita l'autenticazione 
	 * in caso di fallimento viene lanciata un eccezzione.
	 * @throws ParametroInvalidoException
	 */
	@Test
	void autenticazione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo viene passata l'email del VF che si intende rimuovere
	 * il test fallisce poichè la mail non viene inviata
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testemailfail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo viene passata l'email del VF che si intende rimuovere
	 * il test fallisce poichè la mail non viene inviata nel modo corretto
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testmailfail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emai", "dematteo.antonio");
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}

	/**
	 * Questo metodo viene passata l'email del VF che si intende rimuovere
	 * il test va a buon fine 
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testemail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("email", "dematteo.antonio");
		servlet.doPost(request, response);
		assertEquals("./GestionePersonaleServlet",response.getRedirectedUrl());
	}
}
