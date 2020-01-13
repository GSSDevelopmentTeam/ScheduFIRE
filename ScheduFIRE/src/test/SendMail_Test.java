package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sendmail.SendMail;

import model.bean.VigileDelFuocoBean;

class SendMail_Test {
	
	private Date data = Date.valueOf(LocalDate.now());
	private HashMap<VigileDelFuocoBean, String> squadraDiurno;
	private HashMap<VigileDelFuocoBean, String> squadraNotturno;

	@BeforeEach
	void setUp() throws Exception {
		squadraDiurno = new HashMap<VigileDelFuocoBean, String>();
		squadraNotturno = new HashMap<VigileDelFuocoBean, String>();
		riempiSquadre();
	}

	@Test
	void test0_0() {
		assertDoesNotThrow(() -> {
			SendMail.sendMail(data, squadraDiurno, squadraNotturno);
			});
	}


	
	private void riempiSquadre() {
		VigileDelFuocoBean vf1 = new VigileDelFuocoBean("nome1", "cognome", "email", "B", "mansione", "username", "grado", 0, 0);
		VigileDelFuocoBean vf2 = new VigileDelFuocoBean("nome2", "cognome", "email", "B", "mansione", "username", "grado", 1, 2);
		VigileDelFuocoBean vf3 = new VigileDelFuocoBean("nome3", "cognome", "email", "B", "mansione", "username", "grado", 3, 4);
		
		squadraDiurno.put(vf1, "a");
		squadraDiurno.put(vf2, "b");
		squadraDiurno.put(vf3, "z");
		
		squadraNotturno.put(vf1, "a");
		squadraNotturno.put(vf2, "b");
		squadraNotturno.put(vf3, "z");
	}

}
