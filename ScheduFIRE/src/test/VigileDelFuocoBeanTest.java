package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.VigileDelFuocoBean;

class VigileDelFuocoBeanTest {

	static VigileDelFuocoBean tester;
	static VigileDelFuocoBean testing;
	
	@BeforeAll
	static void setup() {
		tester = new VigileDelFuocoBean();
		tester = new VigileDelFuocoBean("nome", "cognome", "email", "turno", 
				"mansione", "username", "grado", 0, 0);
	}
	
	@BeforeEach
	void setUp() throws Exception {
		testing = new VigileDelFuocoBean("nome", "cognome", "email", "turno", 
				"mansione", "username", "grado", 0, 0);
	}

	@Test
	void equalNull() {
		assertFalse(tester.equals(null));
	}
	
	@Test
	void equalDiffClass() {
		assertFalse(tester.equals("a"));
	}
	
	@Test
	void equalDiffCarico() {
		testing.setCaricoLavoro(1);
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffCognome() {
		testing.setCognome("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffEmail() {
		testing.setEmail("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffGiorniCorr() {
		testing.setGiorniFerieAnnoCorrente(1);
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffGiorniPrec() {
		testing.setGiorniFerieAnnoPrecedente(1);
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffGrado() {
		testing.setGrado("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffMansione() {
		testing.setMansione("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffNome() {
		testing.setNome("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffTurno() {
		testing.setTurno("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDiffUsername() {
		testing.setUsername("a");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equal() {
		assertTrue(tester.equals(testing));
	}

}
