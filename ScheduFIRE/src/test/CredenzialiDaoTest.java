package test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.CredenzialiBean;
import model.dao.CredenzialiDao;
import util.PasswordSha256;

class CredenzialiDaoTest {

	private static final Class SQLException = null;

	CredenzialiBean c = new CredenzialiBean();
	
	@BeforeEach
	void setUp() throws Exception {
						
		c.setUsername("capoturno");
		c.setPassword(PasswordSha256.getEncodedpassword("capoturno"));
		c.setRuolo("capoturno");
	
	}
	
	@Test
	void testLogin() throws NoSuchAlgorithmException {
		
		CredenzialiBean c = CredenzialiDao.login(this.c.getUsername());
		assertEquals(c,this.c);
		
	}
	
	@Test
	void testLoginNull() {
		
		CredenzialiDao.login(null);
		assertThrows(SQLException, () -> CredenzialiDao.login( this.c.getUsername() ) );
		
	}

}
