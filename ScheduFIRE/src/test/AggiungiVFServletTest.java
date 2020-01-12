
package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import control.AggiungiVFServlet;
import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import control.ScheduFIREException;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;

import org.junit.jupiter.api.Test;

/**
 * @author Giusy
 *
 */
class AggiungiVFServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static ArgumentCaptor<String> captor;
	static AggiungiVFServlet servlet;
	static CapoTurnoBean ct = new CapoTurnoBean();

	@BeforeEach
	void setUp() {
		servlet = new AggiungiVFServlet();
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
	 */
	@Test
	void autenticazione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
	
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 */
	@Test
	void passaggioParametrifailnome() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nme","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Vigile");
		request.addParameter("grado", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	@Test
	void passaggioParametrifailcognome() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cogne", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Vigile");
		request.addParameter("grado", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	@Test
	void passaggioParametrifailmansione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mans", "Vigile");
		request.addParameter("grado", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	@Test
	void passaggioParametrifailgiorni() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	
	@Test
	void passaggioParametrifailmansionegrado() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}	
	@Test
	void passaggioParametrifailcapo() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", " ");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	@Test
	void passaggioParametrifailcaposquadra() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	
	@Test
	void passaggioParametrifail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Carmine");
		request.addParameter("cognome", "Sarraino");
		request.addParameter("email", "carmine1.sarraino");
		request.addParameter("mansione", "Autista");
		request.addParameter("grado", "Semplice");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
	}
	
	@Test
	void passaggioParametrifailsalva() throws ServletException, IOException {
		VigileDelFuocoDao.setAdoperabile("domenico.giordano",false);
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "giordano.domenico");
		request.addParameter("mansione", "Autista");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");;
	}
	
	@Test
	void passaggioParametririuscito() throws ServletException, IOException {
		VigileDelFuocoDao.setAdoperabile("domenico.giordano",false);
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Vincenzo");
		request.addParameter("cognome", "Acanfora");
		request.addParameter("email", "acanfora.vincenzo");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
		servlet.doPost(request, response);
	}
	
	
	
}
