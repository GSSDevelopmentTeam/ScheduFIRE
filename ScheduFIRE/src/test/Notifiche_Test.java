package test;

import static org.junit.jupiter.api.Assertions.*;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import model.bean.VigileDelFuocoBean;
import util.Notifica;
import util.Notifiche;

class Notifiche_Test {
	
	static List<util.Notifica> listaNotifiche;
	static Notifiche not;
	static VigileDelFuocoBean vf;

	@BeforeEach
	void setUp() {
		listaNotifiche = new ArrayList<Notifica>();
		for(int i=1; i<10; i++)
			listaNotifiche.add(new Notifica(i, "", "", i));
	}


	@Test
	@Order(1)
	void test0_0() {
		not = new Notifiche();
	}
	
	@Test
	@Order(2)
	void test0_1() {
		assertDoesNotThrow(() -> {
			Notifiche.update(not.UPDATE_PER_AVVIO);
			Notifiche.update(not.UPDATE_PER_MALATTIA);
		});
	}
	
	@Test
	@Order(3)
	void test0_2() {
		assertDoesNotThrow(() -> {
			Notifiche.update(not.UPDATE_PER_FERIE, from, to, mail);
			Notifiche.update(not.UPDATE_PER_MALATTIA, from, to, mail);
			Notifiche.update(not.UPDATE_SQUADRE_PER_FERIE, from, to, mail);
			Notifiche.update(not.UPDATE_SQUADRE_PER_MALATTIA, from, to, mail);
			Notifiche.update(6,from,to,mail);
		});
	}
	
	@Test
	@Order(4)
	void test0_3() {
		assertDoesNotThrow(() -> {
			not.getListaNotifiche();
		});	
	}
	
	
	@Test
	void test () {
		assertDoesNotThrow(() -> {
			not.rimuovi(listaNotifiche.get(1));
		});
	}
	

	private static final
	Date from = Date.valueOf("2020-01-01");
	private static final
	Date to = Date.valueOf("2020-02-20");
	private static final
	String mail = "domenico.giordano@vigilfuoco.it";
	
}


