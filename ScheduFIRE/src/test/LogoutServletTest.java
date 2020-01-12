package test;


import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.ServletLogout;

class LogoutServletTest {
	
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static ServletLogout servlet;

	@BeforeEach
	void setUp() {
		
		servlet = new ServletLogout();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		
	}
	
	@Test
	void test() throws ServletException, IOException {

		servlet.doGet(request, response);
		
	}

}
