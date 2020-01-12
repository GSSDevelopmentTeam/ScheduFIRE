package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.RimozioneMalattiaServlet;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;
import util.Notifiche;

class RimozioneMalattiaServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static RimozioneMalattiaServlet servlet;
	
	@BeforeAll
	static void setup() {
		servlet = new RimozioneMalattiaServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();	
	}

	@BeforeEach 
	void autentica() {
		servlet = new RimozioneMalattiaServlet();
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
//
//	@Test
//	void 
}
