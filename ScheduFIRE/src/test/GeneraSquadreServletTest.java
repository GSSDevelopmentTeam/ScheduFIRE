package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ListaSquadreDao;

class GeneraSquadreServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static GeneraSquadreServlet servlet;
	HashMap<VigileDelFuocoBean, String> squadraDiurno;
	VigileDelFuocoBean vigile;
	CapoTurnoBean capoturno;

	@BeforeEach
	void setUp() {
		servlet = new GeneraSquadreServlet();
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		
		squadraDiurno=new HashMap<>();
		VigileDelFuocoBean vigile=new VigileDelFuocoBean("Domenico", "cognome", "domenico.giordano@vigilfuoco.it", "B", "vigile", "username",
				"Esperto", 0, 0);
		vigile.setCaricoLavoro(0);
		squadraDiurno.put(vigile, "Sala Operativa");
		capoturno= new CapoTurnoBean("capoturno","capoturno","capoturno","B","capoturno");
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
	void squadreGiaInDatabase() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	
	@Test
	void squadreSalvataggioGenerazione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadraDiurno);
		session.setAttribute("squadraNotturno", squadraDiurno);
		session.setAttribute("capoturno", capoturno);
		servlet.doPost(request, response);

		assertEquals("GeneraSquadreServlet?squadraSalvata",response.getRedirectedUrl());
	}
	
	
	@Test
	void squadraSalvataggioDaCalendario()throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadraDiurno);
		session.setAttribute("squadraNotturno", squadraDiurno);
		request.setParameter("calendario", "calendario");
		request.setParameter("data", "2020-01-01");
		session.setAttribute("squadra", squadraDiurno);
		request.addHeader("referer", "ModificaComposizioneSquadreServlet");
		servlet.doPost(request, response);
		assertEquals("ModificaComposizioneSquadreServlet&squadraSalvata",response.getRedirectedUrl());
	}
	
	@Test
	void squadraSalvataggioDaCalendarioNonSuDB()throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadraDiurno);
		session.setAttribute("squadraNotturno", squadraDiurno);
		request.setParameter("calendario", "calendario");
		request.setParameter("data", "2020-01-05");
		session.setAttribute("squadra", squadraDiurno);
		request.addHeader("referer", "ModificaComposizioneSquadreServlet");
		servlet.doPost(request, response);
		assertEquals("ModificaComposizioneSquadreServlet&squadraSalvata",response.getRedirectedUrl());
	}
	
	
	@Test
	void squadraSalvataggioDaCalendarioNonSuDB2()throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("squadraDiurno", squadraDiurno);
		session.setAttribute("squadraNotturno", squadraDiurno);
		request.setParameter("calendario", "calendario");
		request.setParameter("data", "2020-01-05");
		session.setAttribute("squadra", squadraDiurno);
		request.addHeader("referer", "ModificaComposizioneSquadreServlet");
		servlet.doPost(request, response);
		assertEquals("GeneraSquadreServlet",response.getForwardedUrl());
	}
	
	
	
	
	

}
