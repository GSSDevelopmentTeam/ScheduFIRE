package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.CredenzialiBean;

class CredenzialiBeanTest {
	
	static CredenzialiBean tester;
	static CredenzialiBean testing;
	
	@BeforeAll
	static void setup() {
		tester = new CredenzialiBean();
		tester = new CredenzialiBean("username", "password", "ruolo");
	}
	
	@BeforeEach
	void set() {
		testing = new CredenzialiBean("username", "password", "ruolo");
	}

	@Test
	void equalNull() {
		assertFalse(tester.equals(null));
	}
	
	@Test
	void equalNotClass() {
		assertFalse(tester.equals("false"));
	}
	
	@Test
	void equalDifferentPass() {
		testing.setPassword("no");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDifferentRuolo() {
		testing.setRuolo("no");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalDifferentUsername() {
		testing.setUsername("no");
		assertFalse(tester.equals(testing));
	}
	
	@Test
	void equalTrue() {
		assertTrue(tester.equals(testing));
	}

}
