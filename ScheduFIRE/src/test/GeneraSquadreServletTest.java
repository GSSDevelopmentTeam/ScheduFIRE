package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import model.bean.VigileDelFuocoBean;

class GeneraSquadreServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static GeneraSquadreServlet servlet;
	static ArgumentCaptor<String> captor;
	HashMap<VigileDelFuocoBean, String> squadraDiurno;
	VigileDelFuocoBean vigile;
 
	@BeforeEach
	void setUp() {
		servlet = new GeneraSquadreServlet();
		captor = ArgumentCaptor.forClass(String.class);
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		squadraDiurno=new HashMap<>();
		VigileDelFuocoBean vigile=new VigileDelFuocoBean("Nome", "cognome", "email", "B", "autista", "username",
				"Esperto", 0, 0);
		vigile.setCaricoLavoro(0);
		HashMap<VigileDelFuocoBean, String> squadraDiurno=new HashMap<VigileDelFuocoBean, String>();
		squadraDiurno.put(vigile, "Sala Operativa");
	}
	
	
	

	  

	
	@Test
	void autenticazioneFallita() throws ServletException, IOException {
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void autenticazioneFallita2() throws ServletException, IOException {
		request.setSession(session);
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void autenticazioneFallita3() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "vigile");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void autenticazioneFallita4() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void squadreGiaInDatabase() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void squadreGiaInDatabase2() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadraDiurno);
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	

}
