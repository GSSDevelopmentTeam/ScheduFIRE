package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.*;

import control.AggiungiMalattiaServlet;
import control.ScheduFIREException;
import model.bean.CapoTurnoBean;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;
import util.Notifiche;


class AggiungiMalattiaServletTest {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static AggiungiMalattiaServlet servlet;
	
	@BeforeAll
	static void setup() {
		servlet = new AggiungiMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		
	}
	
	@BeforeAll
	static void setUpBean() throws Exception {
		malattia = new GiorniMalattiaBean();
		malattia.setDataInizio(Date.valueOf("2020-03-15"));
		malattia.setDataFine(Date.valueOf("2020-03-20"));
		malattia.setEmailCT("capoturno");
		malattia.setEmailVF("rosario.marmo@vigilfuoco.it");
		
		malattia2 = new GiorniMalattiaBean();
		malattia2.setDataInizio(Date.valueOf("2020-03-15"));
		malattia2.setDataFine(Date.valueOf("2020-03-20"));
		malattia2.setEmailCT("capoturno");
		malattia2.setEmailVF("salvatore.malaspina@vigilfuoco.it");
		
		malattia3 = new GiorniMalattiaBean();
		malattia3.setDataInizio(Date.valueOf("2020-03-15"));
		malattia3.setDataFine(Date.valueOf("2020-03-20"));
		malattia3.setEmailCT("capoturno");
		malattia3.setEmailVF("michele73.sica@vigilfuoco.it");
		
		malattia4 = new GiorniMalattiaBean();
		malattia4.setDataInizio(Date.valueOf("2020-03-15"));
		malattia4.setDataFine(Date.valueOf("2020-03-20"));
		malattia4.setEmailCT("capoturno");
		malattia4.setEmailVF("michele.granato@vigilfuoco.it");
		
		malattia5 = new GiorniMalattiaBean();
		malattia5.setDataInizio(Date.valueOf("2020-03-15"));
		malattia5.setDataFine(Date.valueOf("2020-03-20"));
		malattia5.setEmailCT("capoturno");
		malattia5.setEmailVF("mario.delregno@vigilfuoco.it");
		
		malattia6 = new GiorniMalattiaBean();
		malattia6.setDataInizio(Date.valueOf("2020-03-15"));
		malattia6.setDataFine(Date.valueOf("2020-03-20"));
		malattia6.setEmailCT("capoturno");
		malattia6.setEmailVF("maurizio.merluzzo@vigilfuoco.it");
		
		malattia7 = new GiorniMalattiaBean();
		malattia7.setDataInizio(Date.valueOf("2020-03-15"));
		malattia7.setDataFine(Date.valueOf("2020-03-20"));
		malattia7.setEmailCT("capoturno");
		malattia7.setEmailVF("franco.mammato@vigilfuoco.it");
		
		malattia8 = new GiorniMalattiaBean();
		malattia8.setDataInizio(Date.valueOf("2020-03-15"));
		malattia8.setDataFine(Date.valueOf("2020-03-20"));
		malattia8.setEmailCT("capoturno");
		malattia8.setEmailVF("arturo.felice@vigilfuoco.it");
	}

	@BeforeEach 
	void autentica() {
		servlet = new AggiungiMalattiaServlet();
		session.setAttribute("ruolo", "capoturno");
		session.setAttribute("capoturno", new CapoTurnoBean("capoturno", "capoturno", "capoturno", "B", "capoturno"));
		session.setAttribute("notifiche", new Notifiche());
		request.setSession(session);
	}
	
	@AfterEach
	void reset() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		servlet.destroy();
	}
	
	@AfterAll 
	static void remove() {
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.buonomo@vigilfuoco.it", Date.valueOf("2020-05-15"), Date.valueOf("2020-05-30"));
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("gerardo.citarella@vigilfuoco.it", Date.valueOf("2020-01-14"), Date.valueOf("2020-01-14"));
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.buonomo@vigilfuoco.it", Date.valueOf("2020-05-16"), Date.valueOf("2020-05-30"));
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.buonomo@vigilfuoco.it", Date.valueOf("2020-05-21"), Date.valueOf("2020-05-30"));
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("rosario.marmo@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("salvatore.malaspina@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("michele73.sica@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("michele.granato@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("mario.delregno@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("maurizio.merluzzo@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("franco.mammato@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("arturo.felice@vigilfuoco.it", 
				malattia2.getDataInizio(), malattia2.getDataFine());
		FerieDao.rimuoviPeriodoFerie("luca.raimondi@vigilfuoco.it", 
				Date.valueOf("2020-03-23"), Date.valueOf("2020-03-25"));
	}
	
	@Test
	void noOp() throws ServletException, IOException {
		servlet.doGet(request, response);
		assertFalse("application/json".equals(response.getContentType()));
	}
	
	@Test
	void aggiuntaMalattia() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "aaa");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals("application/json", response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONtrueInserisciNull() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.getSession().setAttribute("inserisci", null);
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals(null, response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONnullInseriscitrue() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("JSON", null);
		request.getSession().setAttribute("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals(null, response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONtrueInseriscitrue() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("inserisci", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		servlet.doGet(request, response);
		assertEquals(null, response.getContentType());
	}
	
	@Test
	void aggiuntaMalattiaJSONtrueInseriscitrue2() throws ServletException, IOException {
		this.inserimentoMalattie(malattia);
		this.inserimentoMalattie(malattia2);
		this.inserimentoMalattie(malattia3);
		this.inserimentoMalattie(malattia4);
		this.inserimentoMalattie(malattia5);
		this.inserimentoMalattie(malattia6);
		this.inserimentoMalattie(malattia7);
		this.inserimentoMalattie(malattia8);
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "domenico.giordano@vigilfuoco.it");
		request.setParameter("dataInizio", "15-03-2020");
		request.setParameter("dataFine", "20-03-2020");
		
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
		});
	}
	
	@Test 
	void malattiaGiaAggiunta() throws ServletException, IOException {
		request.setParameter("JSON", "aaa");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "mario.buonomo@vigilfuoco.it");
		request.setParameter("dataInizio", "15-05-2020");
		request.setParameter("dataFine", "30-05-2020");
		
		assertDoesNotThrow(() -> {
			servlet.doGet(request, response);
		}); 
	}
	
	@Test
	void setUpBean1() throws Exception {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("JSON", "true");
		request.setParameter("inserisci", "true");
		request.setParameter("emailVF", "gerardo.citarella@vigilfuoco.it");
		request.setParameter("dataInizio", "14-01-2020");
		request.setParameter("dataFine", "14-01-2020");
		
		servlet.doGet(request, response);
		
		assertEquals("application/json", response.getContentType());
	}
	
	private void inserimentoMalattie(GiorniMalattiaBean giorniMalattia) {
		GiorniMalattiaDao.addMalattia(giorniMalattia);
	}
	
	private static GiorniMalattiaBean malattia;
	private static GiorniMalattiaBean malattia2;
	private static GiorniMalattiaBean malattia3;
	private static GiorniMalattiaBean malattia4;
	private static GiorniMalattiaBean malattia5;
	private static GiorniMalattiaBean malattia6;
	private static GiorniMalattiaBean malattia7;
	private static GiorniMalattiaBean malattia8;
}
