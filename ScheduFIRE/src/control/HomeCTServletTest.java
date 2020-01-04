package control;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HomeCTServletTest {
	
	@InjectMocks HttpServletRequest request;
	@InjectMocks HttpServletResponse response;
	@InjectMocks HttpSession sessione;
	ArgumentCaptor<String> captor;
	
	@Before
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testBranch1() {
		try {
			verify(response).sendRedirect(captor.capture());
			new HomeCTServlet().doGet(request, response);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		assertEquals("Login", captor.getValue());
	}

}

