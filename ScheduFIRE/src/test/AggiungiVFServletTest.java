
package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import control.AggiungiVFServlet;
import control.GestionePersonaleException;
import control.ParametroInvalidoException;
import control.ScheduFIREException;
import model.bean.CapoTurnoBean;
import model.dao.VigileDelFuocoDao;

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
	
	@AfterAll
	static void tearDown(){
		VigileDelFuocoDao.deleteVF("v.acanfora@vigilfuoco.it");
	}
	/**
	 * Questo metodo serve per verificare se nella servlet viene eseguita l'autenticazione 
	 * in caso di fallimento viene lanciata un eccezzione.
	 * @throws ScheduFIREException
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
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri fallisce passando nome con null
	 * @throws ServletException
	 * @throws OException
	 * @throws ParametroInvalidoException
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
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri fallisce passando cognome con null
	 * @throws ServletException
	 * @throws OException
	 * @throws ParametroInvalidoException
	 */
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
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri fallisce passando mansione con null
	 * @throws ServletException
	 * @throws OException
	 * @throws ParametroInvalidoException
	 */
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
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri va a buon fine ed entra nel caso di Capo Squadra con grado esperto
	 * @throws ServletException
	 * @throws OException
	 * @throws GestionePersonaleException
	 */
	@Test
	void passaggioParametriesperto() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("nome","Alberto");
		request.addParameter("cognome", "Frosinone");
		request.addParameter("email", "frosinone.alberto");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Non esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio va a buon fine ed entra nel caso d'errore di Capo Squadra con grado Qualificato
	 * @throws ServletException
	 * @throws OException
	 * @throws ParametroInvalidoException
	 */
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
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}	
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri va a buon fine e rientra nel caso di Capo Squadra con grado Semplice
	 * @throws ServletException
	 * @throws OException
	 * @throws GestionePersonaleException
	 */
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
		assertDoesNotThrow(()->{
			servlet.doPost(request, response);
		});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri non va a buon fine poiche ferieannocorrente sono null
	 * @throws ServletException
	 * @throws OException
	 * @throws NumberFormatException
	 */
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
		request.addParameter("giorniFerieAnnoCorrente", " ");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
		assertThrows(NumberFormatException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri va a buon fine, ma rientra nel caso in cui il VF e' già registrato
	 * @throws ServletException
	 * @throws OException
	 * @throws ParametroInvalidoException
	 */
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
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri va a buon fine, ma rientra nel caso in cui il VF e' già registrato ed non è adoperabile
	 * @throws ServletException
	 * @throws OException
	 * @throws GestionePersonaleException
	 */
//	@Test
//	void passaggioParametrifailsalva() throws ServletException, IOException {
//		VigileDelFuocoDao.setAdoperabile("domenico.giordano",false);
//		request.setSession(session);
//		request.getSession().setAttribute("ruolo", "capoturno");
//		request.getSession().setAttribute("capoturno", ct);
//		request.getSession().setAttribute("notifiche", "notifiche");
//		request.getSession().setAttribute("email", "capoturno");
//		request.addParameter("nome","Domenico");
//		request.addParameter("cognome", "Giordano");
//		request.addParameter("email", "giordano.domenico");
//		request.addParameter("mansione", "Autista");
//		request.addParameter("grado", "Esperto");
//		request.addParameter("giorniFerieAnnoCorrente", "10");
//		request.addParameter("giorniFerieAnnoPrecedente", "5");
//		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
//	}
//	
	/**
	 * Questo metodo testa il passaggio dei parametri alla Servlet
	 * e i vari casi di aggiunta VF
	 * il passaggio di parametri va a buon fine, e il vf viene registrato sul Database
	 * @throws ServletException
	 * @throws OException
	 */
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
		request.addParameter("email", "v.acanfora");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "5");
		servlet.doPost(request, response);
		assertEquals("./GestionePersonaleServlet",response.getRedirectedUrl());
	}
	
	
	
}
