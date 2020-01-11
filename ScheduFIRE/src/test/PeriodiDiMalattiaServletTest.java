package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import control.PeriodiDiMalattiaServlet;

class PeriodiDiMalattiaServletTest {

	@BeforeEach
	void setUp() throws Exception {
		servlet = new PeriodiDiMalattiaServlet();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	private static PeriodiDiMalattiaServlet servlet;
	private static MockHttpServletRequest request;
	private static MockHttpServletRequest response;
	private static MockHttpSession sessione;
}
