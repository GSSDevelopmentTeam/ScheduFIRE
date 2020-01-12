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
	void test0_1() {
		not.setId(id_test_nuovo);
		not.setSeverita(severita_test_nuovo);
		not.setPath(path_test_nuovo);
		not.setTesto(testo_test_nuovo);
	}
	
	@Test
	void test0_2() {
		not.getId();
		not.getPath();
		not.getSeverita();
		not.getTesto();
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
