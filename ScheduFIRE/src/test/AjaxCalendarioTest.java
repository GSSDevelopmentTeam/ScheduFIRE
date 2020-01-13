package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.AjaxCalendario;
import control.LoginServlet;

class AjaxCalendarioTest {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static AjaxCalendario servlet;
	static LoginServlet servletLogin;
	
	@BeforeEach
	void setUp() {
		
		servlet = new AjaxCalendario();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();

	}
	

	@Test
	void test1() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.addParameter("giorno", "2");
		request.addParameter("mese", "1");
		request.addParameter("anno", "20");
		
		servlet.doGet(request, response);
		
	}
	
	@Test
	void test2() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.addParameter("giorno", "");
		request.addParameter("mese", "");
		request.addParameter("anno", "");
		
		servlet.doGet(request, response);
		
	}
	
	@Test
	void test3() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.addParameter("giorno", "2");
		request.addParameter("mese", "1");
		request.addParameter("anno", "2020");
		
		servlet.doGet(request, response);
		
	}

}
