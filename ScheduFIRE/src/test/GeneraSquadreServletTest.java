package test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;

import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import model.bean.CapoTurnoBean;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.FerieDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;
import model.dao.VigileDelFuocoDao;

class GeneraSquadreServletTest {
	
	 
	static MockHttpServletRequest request; 
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static GeneraSquadreServlet servlet;
	static HashMap<VigileDelFuocoBean, String> squadra;
	VigileDelFuocoBean vigile;
	static CapoTurnoBean capoturno;
	static Date data;
	static Date dataNonInDB;
	static Date giornoSuccessivo;
	private void popolaDB() {
		ListaSquadreDao.aggiungiSquadre(data, capoturno.getEmail());
		SquadraDao.aggiungiSquadra(data);
		List<ComponenteDellaSquadraBean> lista = new ArrayList<>();
		Iterator i = squadra.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<VigileDelFuocoBean, String> coppia = (Entry<VigileDelFuocoBean, String>) i.next();
			lista.add(new ComponenteDellaSquadraBean(coppia.getValue(), coppia.getKey().getEmail(), data));
		}
		ComponenteDellaSquadraDao.setComponenti(lista);
		ListaSquadreDao.aggiungiSquadre(giornoSuccessivo, capoturno.getEmail());
		SquadraDao.aggiungiSquadra(giornoSuccessivo);
		lista = new ArrayList<>();
		i = squadra.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry<VigileDelFuocoBean, String> coppia = (Entry<VigileDelFuocoBean, String>) i.next();
			lista.add(new ComponenteDellaSquadraBean(coppia.getValue(), coppia.getKey().getEmail(), giornoSuccessivo));
		}
		ComponenteDellaSquadraDao.setComponenti(lista);
		
	}
	
	@BeforeAll
	static void setUpAll() {
		servlet = new GeneraSquadreServlet();
		data=Date.valueOf("2010-06-02");
		dataNonInDB=Date.valueOf("2010-06-06");
		//Abilito il testing
		ReflectionTestUtils.setField(servlet, "testing", "testing");

		
		giornoSuccessivo=Date.valueOf("2010-06-03");
		capoturno= new CapoTurnoBean("capoturno","capoturno","capoturno","B","capoturno");
		squadra=new HashMap<>();
		VigileDelFuocoBean vigile=new VigileDelFuocoBean("Domenico", "Giordano", "domenico.giordano@vigilfuoco.it", "B", "vigile", "turnoB",
				"Esperto", 0, 0);
		vigile.setCaricoLavoro(0);
		squadra.put(vigile, "Sala Operativa");
		squadra.put(vigile, "Prima Partenza");
		squadra.put(vigile, "Auto Scala");
		squadra.put(vigile, "Auto Botte");
	}
	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(servlet, "testing", "testing");
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		
	}
	
	
	@AfterEach
	void tearDown() {
		ListaSquadreDao.rimuoviTutte(Date.valueOf("2011-06-03"));
	}
	
	@AfterAll
	static void cancellaFerie() {
		int ferie=VigileDelFuocoDao.ottieniNumeroFerieCorrenti("domenico.giordano@vigilfuoco.it");
		VigileDelFuocoDao.aggiornaFerieCorrenti("domenico.giordano@vigilfuoco.it", ferie-3);
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
	void squadreGiaInSessioneNoTesting() throws ServletException, IOException {
		ReflectionTestUtils.setField(servlet, "testing", null);
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void squadreGiaInSessione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	
	@Test
	void squadreGiaInSessioneMaVigileNonPiuDisponibile() throws ServletException, IOException {
		FerieDao.aggiungiPeriodoFerie("capoturno", "domenico.giordano@vigilfuoco.it", data, Date.valueOf("2010-06-15"));
		ReflectionTestUtils.setField(servlet, "data", Date.valueOf("2010-06-02"));
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		servlet.doPost(request, response);
		FerieDao.rimuoviPeriodoFerie("domenico.giordano@vigilfuoco.it", data, Date.valueOf("2010-06-15"));
		assertEquals("GeneraSquadreServlet",response.getForwardedUrl());
	}
	
	
	@Test
	void squadreGiaInDatabase() throws ServletException, IOException {
		popolaDB();
		ReflectionTestUtils.setField(servlet, "data", Date.valueOf("2010-06-1"));

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	@Test
	void squadreNonInDatabase() throws ServletException, IOException {
		ReflectionTestUtils.setField(servlet, "data", dataNonInDB);

		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	
	
	@Test
	void squadraSalvataggioDaCalendario()throws ServletException, IOException {
		popolaDB();
		ReflectionTestUtils.setField(servlet, "data", data);
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		request.setParameter("calendario", "calendario");
		request.setParameter("data", "2010-06-02");
		session.setAttribute("squadra", squadra);
		request.addHeader("referer", "ModificaComposizioneSquadreServlet");
		servlet.doPost(request, response);
		assertEquals("ModificaComposizioneSquadreServlet&squadraSalvata",response.getRedirectedUrl());
	}
	
	
	
	@Test
	void squadraSalvataggioDaGenerazione()throws ServletException, IOException {
		ReflectionTestUtils.setField(servlet, "data", data);
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("capoturno", capoturno);
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		request.setParameter("data", "2010-06-02");
		session.setAttribute("squadra", squadra);
		request.addHeader("referer", "ModificaComposizioneSquadreServlet");
		servlet.doPost(request, response);
		assertEquals("GeneraSquadreServlet?squadraSalvata",response.getRedirectedUrl());
	}
	
	
	
	@Test
	void squadraSalvataggioDaGenerazioneGiaPresentiSuDB()throws ServletException, IOException, InterruptedException {
		data=Date.valueOf("2010-06-06");
		giornoSuccessivo=Date.valueOf("2010-06-07");
		popolaDB();
		data=Date.valueOf("2010-06-02");
		giornoSuccessivo=Date.valueOf("2010-06-03");
		ReflectionTestUtils.setField(servlet, "data", data);
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("capoturno", capoturno);
		request.setParameter("salva", "salva");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		request.setParameter("data", "2010-06-02");
		session.setAttribute("squadra", squadra);
		request.addHeader("referer", "ModificaComposizioneSquadreServlet");
		servlet.doPost(request, response);
		assertEquals("GeneraSquadreServlet?squadraSalvata",response.getRedirectedUrl());
	}
	
	
	@Test
	void squadreNonGiaInSessione() throws ServletException, IOException {
		request.setSession(session);
		ReflectionTestUtils.setField(servlet, "data", data);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		session.setAttribute("squadraDiurno", squadra);
		session.setAttribute("squadraNotturno", squadra);
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	
	
	
	
	
	

}
