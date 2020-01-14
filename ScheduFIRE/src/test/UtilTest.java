package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import control.AutenticazioneException;
import control.NotEnoughMembersException;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;
import model.dao.ComponenteDellaSquadraDao;
import model.dao.FerieDao;
import model.dao.VigileDelFuocoDao;
import util.Util;

class UtilTest {

	@BeforeEach
	void setUp() throws Exception {
		@SuppressWarnings("unused")
		Util util = new Util();
	}

	@AfterEach
	void tearDown() throws Exception {
		List<ComponenteDellaSquadraBean> lista = ComponenteDellaSquadraDao.getComponenti(Date.valueOf("2020-03-21"));
		HashMap<VigileDelFuocoBean, String> squadra = Util.ottieniSquadra(Date.valueOf("2020-03-21"));
		
		ComponenteDellaSquadraDao.removeComponenti(lista);
		VigileDelFuocoDao.removeCaricoLavorativo(squadra);
		
		this.rimuoviFerie();
	}

	@Test
	void generaSquadraSenzaEccezioni() throws NotEnoughMembersException {
		int sizeListAtteso = 12;
		
		List<ComponenteDellaSquadraBean> risultato = Util.generaSquadra(Date.valueOf("2020-03-21"));
		
		assertEquals(sizeListAtteso, risultato.size());
	}
	
	@Test
	void abbastanzaPerTurnoTest() {
		assertTrue(Util.abbastanzaPerTurno(6, 4, 12));
	}
	
	@Test
	void abbastanzaPerTurnoCTinsufficienti() {
		assertFalse(Util.abbastanzaPerTurno(1, 4, 12));
	}
	
	@Test
	void abbastanzaPerTurnoAutistiInsufficienti() {
		assertFalse(Util.abbastanzaPerTurno(1, 2, 12));
	}
	
	@Test
	void abbastanzaPerTurnoVigiliInsufficienti() {
		assertFalse(Util.abbastanzaPerTurno(6, 4, 4));
	}
	
	@Test
	void abbastanzaPerTurnoInsufficienti() {
		assertTrue(Util.abbastanzaPerTurno(3, 3, 8));
	}
	
	@Test
	void abbastanzaPerTurnoInsufficienti1() {
		assertTrue(Util.abbastanzaPerTurno(5, 6, 7));
	}
	
	/*@Test
	void SostituisciVigileSenzaEccezioni() throws NotEnoughMembersException {
		
		List<ComponenteDellaSquadraBean> lista = ComponenteDellaSquadraDao.getComponenti(Date.valueOf("2020-01-02"));
		String mailVFDaSostituire = lista.get(0).getEmailVF();
		
		assertDoesNotThrow(() -> {
			Util.sostituisciVigile(Date.valueOf("2020-01-02"), mailVFDaSostituire);
		});
	}*/

	@Test
	void ottieniSquadraTest() throws NotEnoughMembersException {
		int sizeAttesa = 12;
		
		HashMap<VigileDelFuocoBean, String> squadra = Util.ottieniSquadra(Date.valueOf("2020-01-05"));
		
		assertEquals(squadra.size(), sizeAttesa);
	}

	@Test
	void OrdinaComponentiTest() {
		boolean ordinata = true;
		ArrayList<ComponenteDellaSquadraBean> lista = ComponenteDellaSquadraDao.getComponenti(Date.valueOf("2020-01-05"));
		ArrayList<ComponenteDellaSquadraBean> listaOrdinata = Util.ordinaComponenti(lista);
		
		for(int i=0; i<3; i++) {
			if(!listaOrdinata.get(i).getTipologiaSquadra().toUpperCase().equals("SALA OPERATIVA"))
				ordinata = false;
		}
		for(int i=3; i<8; i++) {
			if(!listaOrdinata.get(i).getTipologiaSquadra().toUpperCase().equals("PRIMA PARTENZA"))
				ordinata = false;
		}
		for(int i=8; i<10; i++) {
			if(!listaOrdinata.get(i).getTipologiaSquadra().toUpperCase().equals("AUTO SCALA"))
				ordinata = false;
		}
		for(int i=10; i<12; i++) {
			if(!listaOrdinata.get(i).getTipologiaSquadra().toUpperCase().equals("AUTO BOTTE"))
				ordinata = false;
		}
		
		assertTrue(ordinata);
	}

	@Test
	void isAutenticatoSessioneNull() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isAutenticato(request);
		});
	}
	
	@Test
	void isAutenticatoRuoloNull() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", null);
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isAutenticato(request);
		});
	}
	
	@Test
	void isAutenticatoSessioneRuoloTrue() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		
		assertDoesNotThrow(() -> {
			Util.isAutenticato(request);
		});
	}

	@Test
	void isCapoTurnoSessioneNull() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isCapoTurno(request);
		});
	}
	
	@Test
	void isCapoTurnoRuoloNull() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", null);
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isCapoTurno(request);
		});
	}
	
	@Test
	void isCapoTurnoRuoloVigile() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "vigile");
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isCapoTurno(request);
		});
	}
	
	@Test
	void isCapoTurnoRuoloCapoturno() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isCapoTurno(request);
		});
	}
	
	
	@Test
	void isCapoTurnoNotificheNull() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", null);
		
		assertThrows(AutenticazioneException.class, () -> {
			Util.isCapoTurno(request);
		});
	}
	
	@Test
	void isCapoTurnoNotificheTrue() {
		request = new MockHttpServletRequest();
		sessione = new MockHttpSession();
		request.setSession(sessione);
		request.getSession().setAttribute("ruolo", "capoturno");
		request.getSession().setAttribute("notifiche", "notifiche");
		
		assertDoesNotThrow(() -> {
			Util.isCapoTurno(request);
		});
	}

	@Test
	void compareVigileTest() {
		boolean ordinata = true;
		List<VigileDelFuocoBean> lista = VigileDelFuocoDao.ottieni();
		List<VigileDelFuocoBean> listaOrdinata = Util.compareVigile(lista);
		
		for(int i=0; i<6; i++) {
			if(!listaOrdinata.get(i).getMansione().toUpperCase().equals("CAPO SQUADRA"))
				ordinata = false;
		}
		for(int i=6; i<10; i++) {
			if(!listaOrdinata.get(i).getMansione().toUpperCase().equals("AUTISTA"))
				ordinata = false;
		}
		for(int i=10; i<22; i++) {
			if(!listaOrdinata.get(i).getMansione().toUpperCase().equals("VIGILE"))
				ordinata = false;
		}
		
		assertFalse(ordinata);
	}
	
	private void rimuoviFerie() {
		FerieDao.rimuoviPeriodoFerie("franco.mammato@vigilfuoco.it", 
				Date.valueOf("2020-03-20"), Date.valueOf("2020-03-22"));
		FerieDao.rimuoviPeriodoFerie("mario.delregno@vigilfuoco.it",
				Date.valueOf("2020-03-20"), Date.valueOf("2020-03-22"));
		FerieDao.rimuoviPeriodoFerie("rosario.marmo@vigilfuoco.it",
				Date.valueOf("2020-03-20"), Date.valueOf("2020-03-22"));
		FerieDao.rimuoviPeriodoFerie("salvatore.malaspina@vigilfuoco.it",
				Date.valueOf("2020-03-20"), Date.valueOf("2020-03-22"));
		FerieDao.rimuoviPeriodoFerie("michele.granato@vigilfuoco.it",
				Date.valueOf("2020-03-20"), Date.valueOf("2020-03-22"));
	}
	
//	private void rimuoviSquadra() {
//		List<ComponenteDellaSquadraBean> lista1 = ComponenteDellaSquadraDao.getComponenti(Date.valueOf("2020-03-25"));
//		HashMap<VigileDelFuocoBean, String> squadra1 = Util.ottieniSquadra(Date.valueOf("2020-03-25"));
//		
//		ComponenteDellaSquadraDao.removeComponenti(lista1);
//		VigileDelFuocoDao.removeCaricoLavorativo(squadra1);
//	}


	private MockHttpServletRequest request;
	private MockHttpSession sessione;
}
