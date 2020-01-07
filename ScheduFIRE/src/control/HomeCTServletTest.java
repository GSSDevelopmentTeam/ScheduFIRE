package control;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HomeCTServletTest {
	
	static HttpServletRequest request;
	static HttpServletResponse response;
	static HttpSession session;
	static HomeCTServlet servlet;
	static ArgumentCaptor<String> captor;
	
	@BeforeAll
	static void setUp() {
		servlet = new HomeCTServlet();
		captor = ArgumentCaptor.forClass(String.class);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
	}

	@Test
	void testBranch1() throws IOException, ServletException {
		when(request.getSession().getAttribute("ruolo")).thenReturn("SBAGLIATO");
		verify(response).sendRedirect(captor.capture());
		servlet.doGet(request, response);
		assertEquals("Login", captor.getValue());
	}

}
