package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.bean.CapoTurnoBean;
import model.dao.CapoTurnoDao;

class CapoTurnoDaoTest {

	private static final Class SQLException = null;

	CapoTurnoBean ct = new CapoTurnoBean();
	
	@BeforeEach
	void setUp() throws Exception {
						
		ct.setNome("capoturno");
		ct.setCognome("capoturno");
		ct.setEmail("capoturno");
		ct.setTurno("B");
		ct.setUsername("capoturno");
	
	}
	
	@Test
	void testOttieni() {
		
		CapoTurnoBean ct = CapoTurnoDao.ottieni(this.ct.getEmail());
		assertEquals(ct,this.ct);
		
	}
	
	@Test
	void testOttieniNull() {
		
		CapoTurnoDao.ottieni(null);
		assertThrows(SQLException, () -> CapoTurnoDao.ottieni( this.ct.getEmail() ) );
		
	}


}
