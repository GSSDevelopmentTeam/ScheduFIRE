package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.jasper.tagplugins.jstl.core.Param;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import control.AggiungiVFServlet;
import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import control.GestionePersonaleException;
import control.ParametroInvalidoException;
import control.ScheduFIREException;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;

class AggiungiVFTestBlackBox {
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
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("capoturno", ct);
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("email", "capoturno");
	}

	@AfterAll
	static void tearDown(){
		VigileDelFuocoDao.deleteVF("d.giordano@vigilfuoco.it");
		VigileDelFuocoDao.deleteVF("null@vigilfuoco.it");
		VigileDelFuocoDao.deleteVF("d.giordano@vigilfuoco.it@vigilfuoco.it");
	}


	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il nome inserito e' null
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_1() {
		request.addParameter("nome", " ");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il nome inserito e' troppo lungo
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_2() {
		request.addParameter("nome", "Domenicodomenicodomenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il nome inserito non e' del formato corretto
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_3() {
		request.addParameter("nome", "DOMenico1");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il cognome inserito e' null
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_4() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", " ");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il cognome inserito e' troppo lungo
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_5() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordanogiordanogiordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il cognome non e' del formato corretto
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_6() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "GIOrdano1");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� l'email e' troppo corta
	 * @throws NumberFormatException
	 */
	@Test
	void testTC_10_7() {
		request.addParameter("nom", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "d@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", " ");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� l'email non e' del formato corretto
	 * @throws NumberFormatException
	 */
	@Test
	void testTC_10_8() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano1@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", " ");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� l'email non � valida
	 * @throws NumberFormatException
	 */
	@Test
	void testTC_10_9() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "michele.sansone@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", " ");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� la mansione e' null
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_10() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", " ");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "10");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il numero di ferieannocorrente e' null
	 * @throws NumberFormatException
	 */
	@Test
	void testTC_10_11() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", " ");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il numero di ferieannocorrente e' troppo grande
	 * @throws ScheduFIREException
	 */
	@Test
	void testTC_10_12() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniferieannoCorrente", "222");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il numero di ferieannocorrente non rispetta il formato
	 * @throws ScheduFIREException
	 */
	@Test
	void testTC_10_13() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniferieannoCorrente", "25");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il numero di ferieannoprecedente e' null
	 * @throws NumberFormatException
	 */
	@Test
	void testTC_10_14() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "22");
		request.addParameter("giorniFerieAnnoPrecedente", " ");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}
	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il numero di ferieannoprecedente e' troppo grande
	 * @throws ScheduFIREException
	 */
	@Test
	void testTC_10_15() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "22");
		request.addParameter("giorniferieannoPrecedente", "1000");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * Il metodo fallisce poich� il numero di ferieannoprecedente non rispetta il formato
	 * @throws ScheduFIREException
	 */
	@Test
	void testTC_10_16() {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "giordano.domenico@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "22");
		request.addParameter("giorniferieannoprecedente", "1000");
		assertThrows(ParametroInvalidoException.class, () -> {
			servlet.doPost(request, response);
		});
	}

	/**
	 * Questo metodo inserisce testa l'inserimento di un VF
	 * operazione riuscita con successo
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws ParametroInvalidoException
	 */
	@Test
	void testTC_10_17() throws ServletException, IOException {
		request.addParameter("nome", "Domenico");
		request.addParameter("cognome", "Giordano");
		request.addParameter("email", "domenico.giordano@vigilfuoco.it");
		request.addParameter("mansione", "Capo Squadra");
		request.addParameter("grado", "Esperto");
		request.addParameter("giorniFerieAnnoCorrente", "22");
		request.addParameter("giorniFerieAnnoPrecedente", "0");
		assertThrows(ParametroInvalidoException.class,()->{
			servlet.doPost(request, response);
		});
	}
}
