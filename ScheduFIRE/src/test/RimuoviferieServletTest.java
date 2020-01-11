package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import junit.framework.TestCase;

import model.dao.FerieDao;

class RimuoviferieServletTest extends Mockito{
	/*  private MockHttpServletRequest request;
	  private MockHttpServletResponse response;
	*/
	@BeforeAll
	/*static void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new ServletSecretary();
		
		Date dataInizio = Date.valueOf("2020-03-2");
		Date dataFine = Date.valueOf("2020-03-6");
		FerieDao.aggiungiPeriodoFerie("capoturno", "luca.raimondi@vigilfuoco.it", dataInizio, dataFine);
		
		
	}*/
	

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
