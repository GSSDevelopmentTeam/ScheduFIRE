package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import util.Validazione;

class ValidazioneTest {
	Validazione val=new Validazione();
	
	@Test
	void usernameNull() {
		boolean risposta=Validazione.username(null);
		assertFalse(risposta);
	}
	
	@Test
	void usernamePatternErrato() {
		boolean risposta=Validazione.username("turno B");
		assertFalse(risposta);
	}
	
	@Test
	void usernameCorretto() {
		boolean risposta=Validazione.username("turnoB");
		assertTrue(risposta);
	}
	
	@Test
	void passwordNull() {
		boolean risposta=Validazione.password(null);
		assertFalse(risposta);
	}
	
	@Test
	void passwordPatternErrato() {
		boolean risposta=Validazione.password("turno B");
		assertFalse(risposta);
	}
	
	@Test
	void passwordCorretto() {
		boolean risposta=Validazione.password("turnoB");
		assertTrue(risposta);
	}
	
	
	
	
	
	@Test
	void parametroNomeNull() {
		boolean risposta=Validazione.nome(null);
		assertFalse(risposta);
	}
	
	
	@Test
	void inizialeNomeMinuscola() {
		boolean risposta=Validazione.nome("mario");
		assertFalse(risposta);
	}
	
	@Test
	void nomeTroppoLungo() {
		boolean risposta=Validazione.nome("Mariomariomariomariomario");
		assertFalse(risposta);
	}
	
	@Test
	void nomeCorretto() {
		boolean risposta=Validazione.nome("Mario");
		assertTrue(risposta);
	}

	
	//Cognome
	@Test
	void parametroCognomeNull() {
		boolean risposta=Validazione.cognome(null);
		assertFalse(risposta);
	}
	
	
	@Test
	void inizialeMinuscola() {
		boolean risposta=Validazione.cognome("rossi");
		assertFalse(risposta);
	}
	
	@Test
	void cognomeTroppoLungo() {
		boolean risposta=Validazione.cognome("Rossirossirossirossirossi");
		assertFalse(risposta);
	}
	
	@Test
	void cognomeCorretto() {
		boolean risposta=Validazione.cognome("Rossi");
		assertTrue(risposta);
	}
	
	
	//email
	@Test
	void parametroEmailNull() {
		boolean risposta=Validazione.email(null);
		assertFalse(risposta);
	}
	
	
	@Test
	void emailErrata() {
		boolean risposta=Validazione.email("12mario.rossi");
		assertFalse(risposta);
	}
	
	
	@Test
	void emailCorretta() {
		boolean risposta=Validazione.email("mario12.rossi");
		assertTrue(risposta);
	}
	
	
	//Mansione
	@Test
	void mansioneNull() {
		boolean risposta=Validazione.mansione(null);
		assertFalse(risposta);
	}
	
	
	@Test
	void mansioneErrata() {
		boolean risposta=Validazione.mansione("mansioneinventata");
		assertFalse(risposta);
	}
	
	
	@Test
	void mansioneCorretta() {
		boolean risposta=Validazione.mansione("Autista");
		assertTrue(risposta);
	}
	
	//giorni ferie anno corrente
	
	@Test
	void ferieCorrentiNegative() {
		boolean risposta=Validazione.giorniFerieAnnoCorrente(-2);
		assertFalse(risposta);
	}
	
	
	@Test
	void ferieCorrentiCorrette() {
		boolean risposta=Validazione.giorniFerieAnnoCorrente(2);
		assertTrue(risposta);
	}
	
	
	//giorni ferie anni precedenti
	
	@Test
	void feriePrecedentiNegative() {
		boolean risposta=Validazione.giorniFerieAnniPrecedenti(-2);
		assertFalse(risposta);
	}
	
	
	@Test
	void feriePrecedentiCorrette() {
		boolean risposta=Validazione.giorniFerieAnniPrecedenti(2);
		assertTrue(risposta);
	}
	
	
	//grado
	
	@Test
	void gradoNull() {
		boolean risposta=Validazione.grado(null);
		assertFalse(risposta);
	}
	
	@Test
	void gradoErrato() {
		boolean risposta=Validazione.grado("Grado Inventato");
		assertFalse(risposta);
	}
	
	
	@Test
	void gradoCorretto() {
		boolean risposta=Validazione.grado("Qualificato");
		assertTrue(risposta);
	}
	
	
	//turno
	
	@Test
	void turnoNull() {
		boolean risposta=Validazione.turno(null);
		assertFalse(risposta);
	}
	
	@Test
	void turnoErrato() {
		boolean risposta=Validazione.turno("Turno Inventato");
		assertFalse(risposta);
	}
	
	
	@Test
	void turnoCorretto() {
		boolean risposta=Validazione.turno("B");
		assertTrue(risposta);
	}
}
