package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import javax.xml.crypto.Data;

import org.junit.jupiter.api.Test;

import util.GiornoLavorativo;
import util.Validazione;

class GiornoLavorativoTest {
	GiornoLavorativo val=new GiornoLavorativo();
	
	@Test
	void test_turno1() {
		Date data = Date.valueOf("2020-01-13");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B5");
	}
	@Test
	void test_turno2() {
		Date data = Date.valueOf("2020-05-17");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B4");
	}
	@Test
	void test_turno3() {
		Date data = Date.valueOf("2019-12-20");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B7");
	}
	@Test
	void test_turno4() {
		Date data = Date.valueOf("2019-12-25");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B8");
	}
	@Test
	void test_turno5() {
		Date data = Date.valueOf("2019-12-28");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B1");
	}
	@Test
	void test_turno6() {
		Date data = Date.valueOf("2020-01-02");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B2");
	}
	@Test
	void test_turno7() {
		Date data = Date.valueOf("2020-01-06");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B3");
	}
	@Test
	void test_turno8() {
		Date data = Date.valueOf("2020-01-17");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B6");
	}
	
	@Test
	void test_turnomeno1() {
		Date data = Date.valueOf("2019-12-01");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B2");
	}
	@Test
	void test_turnomeno2() {
		Date data = Date.valueOf("2019-12-04");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B3");
	}
	@Test
	void test_turnomeno3() {
		Date data = Date.valueOf("2019-12-08");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B4");
	}
	@Test
	void test_turnomeno4() {
		Date data = Date.valueOf("2019-12-12");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B5");
	}
	@Test
	void test_turnomeno5() {
		Date data = Date.valueOf("2019-12-16");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B6");
	}
	@Test
	void test_turnomeno6() {
		Date data = Date.valueOf("2019-12-20");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B7");
	}
	@Test
	void test_turnomeno7() {
		Date data = Date.valueOf("2019-11-26");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B1");
	}
	@Test
	void test_turnomeno8() {
		Date data = Date.valueOf("2019-11-22");
		String risposta=GiornoLavorativo.nomeTurnoB(data);
		assertEquals(risposta, "B8");
	}
	
	@Test
	void test_isLavorativo() {
		Date data = Date.valueOf("2020-01-17");
		boolean risposta=GiornoLavorativo.isLavorativo(data);
		assertEquals(risposta, true);
	}
	@Test
	void test_isDiurno() {
		Date data = Date.valueOf("2020-01-17");
		boolean risposta=GiornoLavorativo.isDiurno(data);
		assertEquals(risposta, true);
	}
	@Test
	void test_nextLavorativo() {
		Date data = Date.valueOf("2020-01-09");
		Date data1 = Date.valueOf("2020-01-10");
		assertEquals(data1, GiornoLavorativo.nextLavorativo(data));
	}
	@Test
	void test_precLavorativo() {
		Date data = Date.valueOf("2020-01-09");
		Date data1 = Date.valueOf("2020-01-10");
		assertEquals(data, GiornoLavorativo.precLavorativo(data1));
	}
	
}
