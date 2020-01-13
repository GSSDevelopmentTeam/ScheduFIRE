package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.ConnessioneDB;
import model.bean.GiorniMalattiaBean;
import model.dao.GiorniMalattiaDao;

class GiorniMalattiaDaoTest {

	@BeforeAll
	static void setUp() throws Exception {
		@SuppressWarnings("unused")
		GiorniMalattiaDao classe = new GiorniMalattiaDao();
		
		malattiaTestOttieni = new GiorniMalattiaBean();
		malattiaTestOttieni.setDataInizio(Date.valueOf("2020-03-15"));
		malattiaTestOttieni.setDataFine(Date.valueOf("2020-03-20"));
		malattiaTestOttieni.setEmailCT("capoturno");
		malattiaTestOttieni.setEmailVF("mario.buonomo@vigilfuoco.it");
	}

	@Test
	void testAddMalattia_Bean_SenzaEccezioni() {
		assertTrue(GiorniMalattiaDao.addMalattia(malattiaTestOttieni));
	}
	
	@Test
	void testAddMalattia_Bean_BeanNull() {
		GiorniMalattiaBean malattiaTestNull = null;
		
		boolean risultato = GiorniMalattiaDao.addMalattia(malattiaTestNull);
		
		assertFalse(risultato);
	}
	
	@Test
	void testAddMalattia_Bean_ConEccezioni() {
		boolean risultatoAtteso = false;
		
		GiorniMalattiaBean malattia = new GiorniMalattiaBean();
		
		assertThrows(RuntimeException.class, () -> {
			GiorniMalattiaDao.addMalattia(malattia);
		});
	}

	@Test
	void testOttieniMalattie_SenzaEccezioni() {
		List<GiorniMalattiaBean> arrayAtteso = new ArrayList<GiorniMalattiaBean>();
		boolean risultatoAtteso = true;
		
		this.aggiungiMalattia(malattiaTestOttieni);
		
		List<GiorniMalattiaBean> arrayRisultato = GiorniMalattiaDao.ottieniMalattie(malattiaTestOttieni.getEmailVF());
		
		boolean risultato = this.confrontoEquals(arrayAtteso, arrayRisultato);
		
		assertEquals(risultato, risultatoAtteso);
	}

	@Test
	void testOttieniMalattie_ConEccezioni() {
		List<GiorniMalattiaBean> atteso = new ArrayList<GiorniMalattiaBean>();
		atteso.add(malattiaTestOttieni);
		
		List<GiorniMalattiaBean> risultato = GiorniMalattiaDao.ottieniMalattie(null);
		
		assertNotEquals(atteso.size(), risultato.size());
	}
	
	@Test
	void testMalattiaInRange_SenzaEccezioni() {
		boolean risultatoAtteso = true;
		
		List<GiorniMalattiaBean> arrayAtteso = new ArrayList<GiorniMalattiaBean>();
		arrayAtteso.add(malattiaTestOttieni);
		
		List<GiorniMalattiaBean> arrayRisultato = GiorniMalattiaDao.malattiaInRange(malattiaTestOttieni.getDataInizio(), 
								malattiaTestOttieni.getDataFine(), malattiaTestOttieni.getEmailVF());
		
		boolean risultato = this.confrontoEquals(arrayAtteso, arrayRisultato);
		
		assertEquals(risultato, risultatoAtteso);
	}
	
	@Test
	void testMalattiaInRange_ConEccezioni() {
		List<GiorniMalattiaBean> atteso = new ArrayList<GiorniMalattiaBean>();
		atteso.add(malattiaTestOttieni);
		
		List<GiorniMalattiaBean> risultato = GiorniMalattiaDao.malattiaInRange(null, null, null);
		
		assertNotEquals(atteso.size(), risultato.size());
	}
	
	@Test
	void testRimuoviPeriodoDiMalattia_ConEccezioni() {
		assertFalse(GiorniMalattiaDao.rimuoviPeriodoDiMalattia(null, Date.valueOf("3000-04-20"), Date.valueOf("3000-04-20")));
	}
	
	@AfterAll
	static void tearDown() throws Exception {
		//test interno rimozione malattia
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia(malattiaTestOttieni.getEmailVF(), 
				malattiaTestOttieni.getDataInizio(), 
				malattiaTestOttieni.getDataFine());
		
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("ettoreanzano@vigilfuoco.it", Date.valueOf("2020-03-07"), 
				Date.valueOf("2020-03-11"));
	}
	
	private void aggiungiMalattia(GiorniMalattiaBean malattia) {
		PreparedStatement ps = null;
		
		String query = "INSERT INTO Malattia (dataInizio, dataFine, emailCT, emailVF) "
                +"VALUES(?, ?, ?, ?);";

		 try {
			 Connection con = null;
			 	try{
			 		con = ConnessioneDB.getConnection();
			 		
			 		ps = con.prepareStatement(query);
				
			 		ps.setDate(1, malattia.getDataInizio());
			 		ps.setDate(2, malattia.getDataFine());
			 		ps.setString(3, malattia.getEmailCT());
			 		ps.setString(4, malattia.getEmailVF());
			    
			 		ps.executeUpdate(); 
			 			
			 		con.commit();
			 	}finally {
			 		ConnessioneDB.releaseConnection(con);
			 	}
		 }catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean confrontoEquals(List<GiorniMalattiaBean> atteso, List<GiorniMalattiaBean> risultante) {
		boolean risultato = true;
		
		for(int i=0; i < atteso.size(); i++) {
			for(int j=0; j < risultante.size(); j++) {
				if(!atteso.get(i).getEmailVF().equals(risultante.get(j).getEmailVF()) &&
						!atteso.get(i).getDataInizio().equals(risultante.get(j).getDataInizio()) &&
						!atteso.get(i).getDataFine().equals(risultante.get(j).getDataFine()))
					risultato = false;
			}
		}
		
		return risultato;
	}
	
	private static GiorniMalattiaBean malattiaTestOttieni;
	
	
}
