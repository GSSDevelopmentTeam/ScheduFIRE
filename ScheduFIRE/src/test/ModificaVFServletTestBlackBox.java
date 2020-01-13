package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.ModificaVFServlet;
import control.ParametroInvalidoException;
import control.ScheduFIREException;

class ModificaVFServletTestBlackBox {
	
	private ModificaVFServlet servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	
	@BeforeEach
	void setUp() {
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		servlet = new ModificaVFServlet();
		session = new MockHttpSession();
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.addParameter("emailVecchia", "domenico.giordano");
		
	}
	
	@Test
	void test1() {
	
		request.addParameter("nomeNuovo", "");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test2() {
	
		request.addParameter("nomeNuovo", "Domenicodomenicodomenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test3() {
	
		request.addParameter("nomeNuovo", "Domenico1");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test4() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test5() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordanogiordanogiordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test6() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano1");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test7() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "d.");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test8() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test9() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "Nicola.labanca");
		request.addParameter("mansioneNuova", "");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test10() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ScheduFIREException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test11() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "222");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test12() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "");
		
		assertThrows(ScheduFIREException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test13() {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "1000");
		
		assertThrows(ParametroInvalidoException.class, () -> {servlet.doPost(request, response);} );
		
	}
	
	@Test
	void test14() throws ServletException, IOException {
	
		request.addParameter("nomeNuovo", "Domenico");
		request.addParameter("cognomeNuovo", "Giordano");
		request.addParameter("emailNuova", "domenico.giordano");
		request.addParameter("mansioneNuova", "Capo Squadra");
		request.addParameter("gradoNuovo", "Esperto");
		request.addParameter("giorniFerieAnnoCorrenteNuovi", "22");
		request.addParameter("giorniFerieAnnoPrecedenteNuovi", "0");
		
		servlet.doPost(request, response);
		String risultato = (String) request.getSession().getAttribute("risultato");

		assertEquals("Modifica del Vigile del Fuoco avvenuta con successo!", risultato);
		
	}

}
