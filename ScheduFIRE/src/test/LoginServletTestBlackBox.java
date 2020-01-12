package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import control.LoginServlet;
import control.ScheduFIREException;

class LoginServletTestBlackBox {

	private LoginServlet servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;

	
	@BeforeEach
	public void setUp()  {
		servlet= new LoginServlet();
		request=new MockHttpServletRequest();
		response=new MockHttpServletResponse();
		session= new MockHttpSession();
		

		
		
		
	}
	
	@Test
	void TC_1_0_1() throws ServletException, IOException {
		request.setParameter("Username", "tu");
		request.setParameter("Password", "turnoB");
		String message="";
		String oracolo="Il campo \"Username\" deve essere formato da almeno 3 caratteri";
		try {
		servlet.doPost(request, response);
		}
		catch (ScheduFIREException s) {
			message=s.getMessage();
		}
		assertTrue(message.equals(oracolo));
	}
	
	
	@Test
	void TC_1_0_2() throws ServletException, IOException {
		request.setParameter("Username", "turnoBturnoBturnoBturnoB");
		request.setParameter("Password", "turnoB");
		String message="";
		String oracolo="Il campo \"Username\" deve essere formato da massimo 20 caratteri";
		try {
		servlet.doPost(request, response);
		}
		catch (ScheduFIREException s) {
			message=s.getMessage();
		}
		assertTrue(message.equals(oracolo));

	}
	
	
	@Test
	void TC_1_0_3() throws ServletException, IOException {
		request.setParameter("Username", "turno B");
		request.setParameter("Password", "turnoB");
		String message="";
		String oracolo="Il campo \"Username\" accetta solo caratteri alfanumerici";
		try {
		servlet.doPost(request, response);
		}
		catch (ScheduFIREException s) {
			message=s.getMessage();
		}
		assertTrue(message.equals(oracolo));

	}
	
	
	@Test
	void TC_1_0_4() throws ServletException, IOException {
		request.setParameter("Username", "turnoB");
		request.setParameter("Password", "tu");
		String message="";
		String oracolo="Il campo \"Password\" deve essere formato da almeno 6 caratteri";
		try {
		servlet.doPost(request, response);
		}
		catch (ScheduFIREException s) {
			message=s.getMessage();
		}
		assertTrue(message.equals(oracolo));

	}
	
	
	
	@Test
	void TC_1_0_5() throws ServletException, IOException {
		request.setParameter("Username", "turnoB");
		request.setParameter("Password", "turnoBturnoBturnoB");
		String message="";
		String oracolo="Il campo \"Password\" deve essere formato da massimo 16 caratteri";
		try {
		servlet.doPost(request, response);
		}
		catch (ScheduFIREException s) {
			message=s.getMessage();
		}

		assertTrue(message.equals(oracolo));

	}
	
	
	
	@Test
	void TC_1_0_6() throws ServletException, IOException {
		request.setParameter("Username", "turnoB");
		request.setParameter("Password", "turno B");
		String message="";
		String oracolo="Il campo \"Password\" accetta solo caratteri alfanumerici";
		try {
		servlet.doPost(request, response);
		}
		catch (ScheduFIREException s) {
			message=s.getMessage();
		}

		assertTrue(message.equals(oracolo));

	}
	
	
	
	@Test
	void TC_1_0_7() throws ServletException, IOException {
		request.setParameter("Username", "turnoB");
		request.setParameter("Password", "turnoB");
		servlet.doPost(request, response);
		assertEquals("CalendarioServlet",response.getRedirectedUrl());

	}
	
	
	

}
