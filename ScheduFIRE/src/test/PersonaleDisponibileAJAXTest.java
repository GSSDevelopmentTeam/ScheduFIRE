package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import control.AutenticazioneException;
import control.PersonaleDisponibileAJAX;
import model.bean.VigileDelFuocoBean;

class PersonaleDisponibileAJAXTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static PersonaleDisponibileAJAX servlet;
	HashMap<VigileDelFuocoBean, String> squadraDiurno;

	@BeforeEach
	void setUp() {
		servlet = new PersonaleDisponibileAJAX();
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		
		squadraDiurno=new HashMap<>();
		VigileDelFuocoBean vigile=new VigileDelFuocoBean("Domenico", "cognome", "domenico.giordano@vigilfuoco.it", "B", "vigile", "username",
				"Esperto", 0, 0);
		vigile.setCaricoLavoro(0);
		squadraDiurno.put(vigile, "Sala Operativa");
	}

	@Test
	void sessioneNonEsistente() throws ServletException, IOException {
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void ruoloNonInSessione() throws ServletException, IOException {
		request.setSession(session);
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void ruoloErrato() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "vigile");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void notificheNonInSessione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		assertThrows(AutenticazioneException.class, ()->{servlet.doPost(request, response);});
	}
	
	@Test
	void squadraDiurno() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("email", "email");
		request.setParameter("mansione", "mansione");
		request.setParameter("tiposquadra", "1");
		session.setAttribute("squadraDiurno", squadraDiurno);
		request.setParameter("dataModifica", "2015-01-03");
		request.setParameter("altroturno", "2015-01-03");


		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileAJAXJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void squadraNotturno() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("email", "email");
		request.setParameter("mansione", "mansione");
		request.setParameter("tiposquadra", "2");
		session.setAttribute("squadraNotturno", squadraDiurno);
		request.setParameter("dataModifica", "2015-01-03");
		request.setParameter("altroturno", "2015-01-03");


		servlet.doPost(request, response);
		assertEquals("JSP/PersonaleDisponibileAJAXJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void squadraCalendario() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("email", "email");
		request.setParameter("mansione", "mansione");
		request.setParameter("tiposquadra", "3");
		session.setAttribute("squadra", squadraDiurno);
		request.setParameter("dataModifica", "2015-01-03");
		request.setParameter("altroturno", "2015-01-03");


		servlet.doGet(request, response);
		assertEquals("JSP/PersonaleDisponibileAJAXJSP.jsp",response.getForwardedUrl());
	}

}
