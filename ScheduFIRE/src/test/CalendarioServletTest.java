package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.mysql.cj.util.Util;

import control.CalendarioServlet;

class CalendarioServletTest {
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static CalendarioServlet servlet;
	static Date data;

	@BeforeEach
	void setUp() throws Exception {
		servlet = new CalendarioServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		
	}


}
