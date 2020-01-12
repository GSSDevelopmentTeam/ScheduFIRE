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

import control.AggiungiVFServlet;
import control.GestionePersonaleException;
import control.ModificaVFServlet;
import control.ParametroInvalidoException;
import control.ScheduFIREException;
import model.bean.CapoTurnoBean;

class ModificaVFServletTest {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static ArgumentCaptor<String> captor;
	static ModificaVFServlet servlet;
	static CapoTurnoBean ct = new CapoTurnoBean();
	static String email="dematteo.antonio";

	@BeforeEach
	void setUp() {
		servlet = new ModificaVFServlet();
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
	 * in questo metodo settiamo il parametro dell'email del Vf da sostituire
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testemailfail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.getSession().setAttribute("emailVecchi", email);
		assertThrows(ParametroInvalidoException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testemail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia", email);
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testemailinesistente() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia", "loredana.viviani");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}	
	/**
	 * Inseriamo nuovi dati al VF
	 * @throws ServletException
	 * @throws IOException
	 */	
	@Test
	void testNuoviDatiFail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nome","Antonio");
		request.addParameter("cogne", "De Matteo");
		request.addParameter("email", "dematteo.antonio");
		request.addParameter("mansione", "Vigile");
		request.addParameter("grado", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrente", "");
		request.addParameter("giorniFerieAnnoPrecedente", "");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNuoviDatiFailError() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("noe","Antonio");
		request.addParameter("cogne", "De Matteo");
		request.addParameter("email", "dematteo.antonio");
		request.addParameter("mansione", "Vigile");
		request.addParameter("grado", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrente", "");
		request.addParameter("giorniFerieAnnoPrecedente", "10");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNuoviDatiFailgiorni() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo","Antonio");
		request.addParameter("cognomeNuovo", "De Matteo");
		request.addParameter("emailNuova", "dematteo.antonio");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "");
		assertThrows(ScheduFIREException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNuoviDatiInvlidDati() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo"," Antonio");
		request.addParameter("cognomeNuovo", "De Matteo");
		request.addParameter("emailNuova", "dematteo.antonio");
		request.addParameter("mansioneNuova", "Autista");
		request.addParameter("gradoNuovo", "Semplice");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNuoviDatiInvlidgrade() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo"," Antonio");
		request.addParameter("cognomeNuovo", "De Matteo");
		request.addParameter("emailNuova", "dematteo.antonio");
		request.addParameter("mansioneNuova", "Autista");
		request.addParameter("gradoNuovo", "Semplice");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "5");
	}
	@Test
	void testNuoviDatiInvlidgradeCt() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo"," Antonio");
		request.addParameter("cognomeNuovo", "De Matteo");
		request.addParameter("emailNuova", "dematteo.antonio");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "5");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNuoviDatiInvlidDateInput() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo"," Ant0ni0");
		request.addParameter("cognomeNuovo", " M4Tteo");
		request.addParameter("emailNuova", "dematteo1.antonio");
		request.addParameter("mansioneNuova", "Capo Squadra ");
		request.addParameter("gradoNuovo", "Coordinatore");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNuoviDati() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo","Antonio");
		request.addParameter("cognomeNuovo", "De Matteo");
		request.addParameter("emailNuova", "dema.antonio");
		request.addParameter("mansioneNuova", "Vigile");
		request.addParameter("gradoNuovo", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "5");
		assertThrows(GestionePersonaleException.class, ()->{servlet.doPost(request, response);});
	}
	@Test
	void testNewEmail() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
		request.addParameter("emailVecchia",email);
		request.addParameter("nomeNuovo","Antonio");
		request.addParameter("cognomeNuovo", "De Matteo");
		request.addParameter("emailNuova", "dematteo.antonio");
		request.addParameter("mansioneNuova", "Vigile");
		request.addParameter("gradoNuovo", "Qualificato");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "10");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "5");
		servlet.doPost(request, response);
	}

}
