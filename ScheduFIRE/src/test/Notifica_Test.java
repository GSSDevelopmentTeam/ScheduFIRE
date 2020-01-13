package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.Notifica;
/**
 * Test per le funzionalità di Notifica.java
 * @author Francesca Pia Perillo
 *
 */
class Notifica_Test {

	static Notifica not;
	@BeforeEach
	void setUp() {
		 not = new Notifica(severita_test, testo_test, path_test, id_test);
	}
	
	@Test
	void test0_5() {
		int oracle = id_test;
		int result = not.getId();
		assertEquals(oracle, result);
	}
	
	@Test
	void test0_6() {
		String oracle = path_test;
		String result = not.getPath();
		assertEquals(oracle, result);
	}
	
	@Test
	void test0_7() {
		int oracle = severita_test;
		int result = not.getSeverita();
		assertEquals(oracle, result);
	}
	
	@Test
	void test0_8() {
		String oracle = testo_test;
		String result = not.getTesto();
		assertEquals(oracle, result);
	}

	@Test
	void test0_1() {
		int oracle = id_test_nuovo;
		not.setId(id_test_nuovo);
		assertEquals(oracle, not.getId());
	}
	
	@Test 
	void test0_2() {
		int oracle = severita_test_nuovo;
		not.setSeverita(severita_test_nuovo);
		assertEquals(oracle, not.getSeverita());
	}
	
	@Test
	void test0_3() {
		String oracle = path_test_nuovo;
		not.setPath(path_test_nuovo);
		assertEquals(oracle,not.getPath());
	}
	
	@Test
	void test0_4() {
		String oracle = testo_test_nuovo;
		not.setTesto(testo_test_nuovo);
		assertEquals(oracle, not.getTesto());
	}


	private static final
	int severita_test = 3;
	private static final
	String testo_test = "xxxxxx xxxxxx ha finito i giorni di ferie a lui disponibili.";
	private static final
	String path_test = "/HomeCTServlet";
	private static final 
	int id_test = 240199;
	private static final
	int severita_test_nuovo = 4;
	private static final
	String testo_test_nuovo = "xxxxxx xxxxxx ha finito i giorni di malattia a lui disponibili.";
	private static final
	String path_test_nuovo = "/GestioneMalattiaServlet";
	private static final 
	int id_test_nuovo = 990124;
	
}
