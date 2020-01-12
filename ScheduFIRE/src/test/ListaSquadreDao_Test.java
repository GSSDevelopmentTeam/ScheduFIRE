package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.ComponenteDellaSquadraBean;
import model.dao.ListaSquadreDao;
/**
 * Testing per le classi di ListaSquadreDao21.
 * @author Ciro Cipolletta
 *
 */

class ListaSquadreDao_Test {
	ComponenteDellaSquadraBean componenteSquadra;
	Date data =Date.valueOf("2020-01-02");
	Date data2 = Date.valueOf("2020-01-03");
	String emailVf="capoturno";



	@BeforeEach
	void setUp() throws Exception {
		componenteSquadra=new ComponenteDellaSquadraBean();
		componenteSquadra.setEmailVF(emailVf);
		componenteSquadra.setGiornoLavorativo(data);
		componenteSquadra.setTipologiaSquadra("Sala Operativa");
	}

	@AfterEach
	void tearDown() {
		ListaSquadreDao.rimuoviTutte(data2);
	}

	@Test
	void testAggiungiSquadre() {
		boolean squadre = ListaSquadreDao.aggiungiSquadre(data, emailVf);
		assertEquals(true, squadre);
	}
	
	@Test
	void testIsEsistente () {
		boolean giornoLavorativo = ListaSquadreDao.isEsistente(data);
		assertEquals(false, giornoLavorativo);
	}
	
	@Test 
	void testRimuoviTutte() {
		boolean giornoDaRimuovere = ListaSquadreDao.rimuoviTutte(data2);
		assertEquals(true, giornoDaRimuovere);
	}



}
