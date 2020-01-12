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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.AutenticazioneException;
import control.ModificaComposizioneSquadreServlet;
import model.bean.CapoTurnoBean;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;

class ModificaComposizioneSquadreServletTest {

	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static ModificaComposizioneSquadreServlet servlet;
	static Date data;
	static CapoTurnoBean capoturno;
	static HashMap<VigileDelFuocoBean, String> squadra;


	
	
	
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
		
	}
	
	
	@BeforeAll
	static void setUpAll() {
		data=Date.valueOf("2010-06-02");
		capoturno= new CapoTurnoBean("capoturno","capoturno","capoturno","B","capoturno");


		}
	
	@BeforeEach
	void setUp() {
		servlet = new ModificaComposizioneSquadreServlet();
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session=new MockHttpSession();
		squadra=new HashMap<>();
		VigileDelFuocoBean vigile=new VigileDelFuocoBean("Domenico", "Giordano", "domenico.giordano@vigilfuoco.it", "B", "vigile", "turnoB",
				"Esperto", 0, 0);
		vigile.setCaricoLavoro(0);
		squadra.put(vigile, "Sala Operativa");
		squadra.put(vigile, "Prima Partenza");
		squadra.put(vigile, "Auto Scala");
		squadra.put(vigile, "Auto Botte");
		
	}
	
	@AfterEach
	void tearDown() {
		ListaSquadreDao.rimuoviTutte(Date.valueOf("2011-06-03"));
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
	void prendeSquadraDalDBPerMetterlaInSessione() throws ServletException, IOException {
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("data", data.toString());
		servlet.doPost(request, response);
		assertEquals("JSP/SquadraJSP.jsp",response.getForwardedUrl());
	}
	
	
	@Test
	void squadreGiaInSessioneEmailNonSostituisce() throws ServletException, IOException {
		popolaDB();
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("data", data.toString());
		request.setParameter("email", "email");
		session.setAttribute("squadra", squadra);
		servlet.doPost(request, response);
		assertEquals("JSP/SquadraJSP.jsp",response.getForwardedUrl());
	}
	
	
	
	@Test
	void squadreGiaInSessioneEmailSostituisce() throws ServletException, IOException {
		popolaDB();
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("data", data.toString());
		request.setParameter("email", "domenico.giordano@vigilfuoco.it");
		session.setAttribute("squadra", squadra);
		request.setParameter("VFNew", "domenico.giordano@vigilfuoco.it");
		servlet.doPost(request, response);
		assertEquals("JSP/SquadraJSP.jsp",response.getForwardedUrl());
	}
	
	
	
	@Test
	void modificaSquadraDiurno() throws ServletException, IOException {
		popolaDB();
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("data", "parametro non valido");
		request.setParameter("email", "domenico.giordano@vigilfuoco.it");
		request.setParameter("VFNew", "domenico.giordano@vigilfuoco.it");
		request.setParameter("tiposquadra", "1");
		session.setAttribute("squadraDiurno", squadra);
		request.setParameter("dataModifica", data.toString());
		request.setParameter("altroturno", data.toString());
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	
	@Test
	void modificaSquadraNotturno() throws ServletException, IOException {
		popolaDB();
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		request.setParameter("data", "parametro non valido");
		request.setParameter("email", "domenico.giordano@vigilfuoco.it");
		request.setParameter("VFNew", "domenico.giordano@vigilfuoco.it");
		request.setParameter("tiposquadra", "2");
		session.setAttribute("squadraNotturno", squadra);
		request.setParameter("dataModifica", data.toString());
		request.setParameter("altroturno", data.toString());
		servlet.doPost(request, response);
		assertEquals("JSP/GestioneSquadreJSP.jsp",response.getForwardedUrl());
	}
	
	
	
	
	

}
