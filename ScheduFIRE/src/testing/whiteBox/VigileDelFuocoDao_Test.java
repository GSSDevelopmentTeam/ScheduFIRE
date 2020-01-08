package testing.whiteBox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import model.ConnessioneDB;

class VigileDelFuocoDao_Test {

	/*@BeforeEach
	void setUp() throws Exception {

	}*/

	@Test
	void testSalva() {
		fail("Not yet implemented");
	}

	@Test
	void testOttieniString() {
		fail("Not yet implemented");
	}

	@Test
	void testOttieni() {
		fail("Not yet implemented");
	}

	@Test
	void testOttieniInt() {
		fail("Not yet implemented");
	}

	@Test
	void testSetAdoperabile() {
		fail("Not yet implemented");
	}

	@Test
	void testModifica() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDisponibili() {
		fail("Not yet implemented");
	}

	@Test
	void testIsDisponibile() {
		fail("Not yet implemented");
	}

	@Test
	void testOttieniListaVF() {
		fail("Not yet implemented");
	}

	@Test
	void testOttieniNumeroFeriePrecedenti() throws SQLException {
		//PowerMockito.mockStatic(ConnessioneDB.class);
		
		when(ConnessioneDB.getConnection()).thenReturn(connessioneMock);
		when(connessioneMock.prepareStatement(anyString())).thenReturn(psMock);
		doNothing().when(psMock).setString(anyInt(), anyString());
		when(psMock.executeQuery()).thenReturn(rsMock);
		when(rsMock.next()).thenReturn(true);
		when(rsMock.getInt(anyString())).thenReturn(anyInt());
		
	
	}

	@Test
	void testOttieniNumeroFerieCorrenti() {
		fail("Not yet implemented");
	}

	@Test
	void testAggiornaFeriePrecedenti() {
		fail("Not yet implemented");
	}

	@Test
	void testAggiornaFerieCorrenti() {
		fail("Not yet implemented");
	}

	@Test
	void testCaricoLavorativo() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveCaricoLavorativo() {
		fail("Not yet implemented");
	}

	@Mock
	Connection connessioneMock;
	
	@Mock
	PreparedStatement psMock;
	
	@Mock
	ResultSet rsMock;
}
