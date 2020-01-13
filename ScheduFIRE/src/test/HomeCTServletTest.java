package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.*;


import control.HomeCTServlet;
import util.Notifiche;

class HomeCTServletTest {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static HomeCTServlet servlet;
	
	@BeforeAll
	static void setup() {
		servlet = new HomeCTServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
	}

	@Test
	void autenticatoNoIDNotifiche() throws ServletException, IOException {
		session.setAttribute("ruolo", "capoturno");
		session.setAttribute("notifiche", new Notifiche());
		request.setSession(session);
		servlet.doGet(request, response);
		assertEquals("text/html", response.getContentType());
	}

}
