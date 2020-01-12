package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.ComponenteDellaSquadraBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.SquadraDao;

class ComponeneteDellaSquadraDao_Test {
	ComponenteDellaSquadraBean componenteSquadraTest;
	Date data = Date.valueOf("2019-01-02");
	SquadraDao squadra;
	String emailVF = "domenico.giordano@vigilfuoco.it";
	ArrayList<ComponenteDellaSquadraBean> componenti;
	

	@BeforeEach
	void setUp() throws Exception {
		componenteSquadraTest = new ComponenteDellaSquadraBean();
		componenteSquadraTest.setGiornoLavorativo(data);
		componenteSquadraTest.setEmailVF(emailVF);
		componenteSquadraTest.setTipologiaSquadra("Sala Operativa");
		componenti = new ArrayList<ComponenteDellaSquadraBean>();
		componenti.add(componenteSquadraTest);
		
	}

	@Test
	void testGetComponenti() {
		componenti = ComponenteDellaSquadraDao.getComponenti(data);
		assertEquals(componenteSquadraTest, componenti);
		
		
	}

}
