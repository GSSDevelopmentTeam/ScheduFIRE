package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.ListaSquadreBean;

class ListaSquadreBeanTest {
	
	@BeforeEach
	void setUp() {
		test = new ListaSquadreBean(Date.valueOf("2020-03-26"), "capoturno", 20);
	}

	@Test
	void listaSquadreBeanTest() {
		new ListaSquadreBean();
	}
	
	@Test
	void listaSquadreBeanTest2() {
		new ListaSquadreBean(Date.valueOf("2020-03-25"), "capoturno", 8);
	}
	
	@Test
	void getGiornoLavorativoTest() {
		assertEquals(Date.valueOf("2020-03-26"), test.getGiornoLavorativo());
	}
	
	@Test
	void setGiornoLavorativoTest() {
		test.setGiornoLavorativo(Date.valueOf("2020-03-26"));
	}
	
	@Test
	void getEmailCTTest() {
		assertEquals("capoturno", test.getEmailCT());
	}
	
	@Test
	void setEmailCTTest() {
		test.setEmailCT("capoturno");
	}
	
	@Test
	void getOraInizialeTest() {
		assertEquals(20, test.getOraIniziale());
	}
	
	@Test
	void setOraInizialeTest() {
		test.setOraIniziale(20);
	}

	private ListaSquadreBean test;
}
