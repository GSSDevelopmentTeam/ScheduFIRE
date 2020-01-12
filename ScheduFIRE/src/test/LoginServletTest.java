package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.LoginServlet;
import control.ScheduFIREException;

class LoginServletTest {

	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static LoginServlet servlet;
	
	@BeforeEach
	void setUp() {
		
		servlet = new LoginServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		
	}
	
	@Test
	void test1() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "capoturno");
		
		servlet.doGet(request, response);
		
	}
	
	@Test
	void test2() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", "turnoB");
		
		servlet.doGet(request, response);
		
	}
	
	@Test
	void test3() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "");
		request.addParameter("Password", "");
	
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
			});
		
	}
	
	@Test
	void test4() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "username");
		request.addParameter("Password", "password");
		
		servlet.doGet(request, response);
		
	}
	
	@Test
	void test5() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "capoturno");
		request.addParameter("Password", "capoturno");
		
		servlet.doGet(request, response);
		
	}
	
	@Test
	void test6() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "capoturnocapoturnocapoturnocapoturno");
		request.addParameter("Password", "capoturno");
		
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
			});
		
	}
	
	@Test
	void test7() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "capoturno");
		request.addParameter("Password", "");
		
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
			});
		
	}
	
	@Test
	void test8() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "capoturno");
		request.addParameter("Password", "capoturnocapoturnocapoturno");
		
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
			});
		
	}
	
	@Test
	void test9() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "capo turno");
		request.addParameter("Password", "capoturno");
		
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
			});
		
	}
	
	@Test
	void test10() throws ServletException, IOException {
		
		request.setSession(session);
		request.getSession().setAttribute("ruolo", null);
		
		request.addParameter("Username", "capoturno");
		request.addParameter("Password", "capo turno");
		
		assertThrows(ScheduFIREException.class, () -> {
			servlet.doGet(request, response);
			});
		
	}

}
