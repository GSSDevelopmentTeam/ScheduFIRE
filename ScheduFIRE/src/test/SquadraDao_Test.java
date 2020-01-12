package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.SquadraBean;
import model.dao.ListaSquadreDao;
import model.dao.SquadraDao;

class SquadraDao_Test {
	SquadraBean squadraTest;
	Date data = Date.valueOf("2019-01-02");
	String emailCt = "capoturno";
	Date dataR = Date.valueOf("2019-01-03");
	

	@BeforeEach
	void setUp() throws Exception {
		squadraTest = new SquadraBean();
		squadraTest.setData(data);
	
		
	}
	
	@AfterEach
	void tearDown() {
		SquadraDao.rimuoviTutti(data);
		ListaSquadreDao.rimuoviTutte(dataR);
	}

	@Test
	void testAggiungiSquadra() {
		ListaSquadreDao.aggiungiSquadre(data, emailCt);
		boolean aggiungiS = SquadraDao.aggiungiSquadra(data);
		assertEquals(true, aggiungiS);
		
	}
	
	@Test
	void testRimuoviTutti() {
		boolean rimuoviSquadre = SquadraDao.rimuoviTutti(data);
		assertEquals(true, rimuoviSquadre);
		
	}

}
