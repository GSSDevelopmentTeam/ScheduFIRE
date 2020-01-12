package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import control.AggiungiMalattiaServlet;
import model.bean.CapoTurnoBean;
import model.dao.GiorniMalattiaDao;

class AggiungiMalattiaServletTestBlackBox {

	@BeforeEach
	void setUp() throws Exception {
		servlet = new AggiungiMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		sessione = new MockHttpSession();
		
		capoturno = new CapoTurnoBean();
		capoturno.setEmail("capoturno");
	}

	@AfterEach
	void tearDown() throws Exception {
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("alberto.barbarulo@vigilfuoco.it", 
				Date.valueOf("2020-01-20"), Date.valueOf("2020-01-24"));
	}

	@Test
	void tc9_1() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		request.setParameter("dataInizio", " ");
		request.setParameter("dataFine", "24-01-2020");
		
		request.getSession().setAttribute("capoturno", new CapoTurnoBean());
		
		assertThrows(StringIndexOutOfBoundsException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test
	void tc9_2() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		request.setParameter("dataInizio", "20-01-2020");
		request.setParameter("dataFine", " ");
		
		request.getSession().setAttribute("capoturno", new CapoTurnoBean());
		
		assertThrows(StringIndexOutOfBoundsException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test
	void tc9_3() throws ServletException, IOException {
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "alberto.barbarulo@vigilfuoco.it");
		request.setParameter("dataInizio", "20-01-2020");
		request.setParameter("dataFine", "24-01-2020");
		
		request.getSession().setAttribute("capoturno", capoturno);
		
		servlet.doGet(request, response);
		
		assertEquals("application/json", response.getContentType());
	}


	private static AggiungiMalattiaServlet servlet;
	private static MockHttpServletRequest request;
	private static MockHttpServletResponse response;
	private static MockHttpSession sessione;
	private CapoTurnoBean capoturno;
}
