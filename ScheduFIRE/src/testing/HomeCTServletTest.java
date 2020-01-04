package testing;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import control.HomeCTServlet;

class HomeCTServletTest {
	
	@Mock HttpServletRequest request;
	@Mock HttpServletResponse response;
	@Mock HttpSession session;
	HomeCTServlet servlet;
	ArgumentCaptor<String> captor;
	
	@BeforeAll
	void setUp() {
		MockitoAnnotations.initMocks(this);
		servlet = new HomeCTServlet();
		captor = ArgumentCaptor.forClass(String.class);
	}

	@Test
	void testBranch1() throws IOException {
		when(request.getSession().getAttribute("ruolo")).thenReturn("SBAGLIATO");
		verify(response).sendRedirect(captor.capture());
		assertEquals("Login", captor.getValue());
	}

}
