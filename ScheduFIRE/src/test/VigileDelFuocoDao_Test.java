package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import model.ConnessioneDB;
import model.bean.VigileDelFuocoBean;
import model.dao.VigileDelFuocoDao;

class VigileDelFuocoDao_Test {
	
	VigileDelFuocoBean vf = new VigileDelFuocoBean();
	String email = vf.getEmail();

	@BeforeAll
	void setUp() throws Exception {
						
		vf.setNome("Fabrizio");
		vf.setCognome("Romano");
		vf.setEmail("Fabrizio.Romano");
		vf.setTurno("B");
		vf.setMansione("Autista");
		vf.setAdoperabile(true);
		vf.setCaricoLavoro(20);
		vf.setGiorniFerieAnnoCorrente(0);
		vf.setGiorniFerieAnnoPrecedente(0);
		vf.setGrado("Coordinatore");
		vf.setUsername("turnoB");
	
	}


	/**
	 * Si occupa del salvataggio dei dati di un Vigile del Fuoco nel database.
	 * @param VigileDelFuocoBean vf Il vigile da memorizzare del database
	 * @return boolean true se l'operazione va a buon fine, false altrimenti
	 * @precondition vf!=null
	 */
	@Test
	void testSalva() {		
		boolean atteso = true;
		boolean risultato = VigileDelFuocoDao.salva(vf);
		assertEquals(atteso,risultato);
				
	}

	/**
	 * Si occupa dell'ottenimento di un Vigile del Fuoco dal database data la sua chiave.
	 * @param chiave ->email una stringa contenente la mail del Vigile
	 * @return il Vigile identificato da chiaveEmail (puo' essere null)
	 */ 
	@Test
	void testOttieniString() {
		
		VigileDelFuocoBean nuovo = VigileDelFuocoDao.ottieni(email);
		assertEquals(vf, nuovo);
	}

	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true.
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 */
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
