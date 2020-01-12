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
import model.bean.FerieBean;
import model.dao.FerieDao;

/**
 * Testing per le classi di FerieDao.
 * @author Francesca Pia Perillo
 *
 */
class FerieDao_Test {
	@BeforeAll
	static void setUp() throws Exception {
		ferieTest = new FerieBean();
		ferieTest.setDataInizio(dataInizio_test);
		ferieTest.setDataFine(dataFine_test);
		ferieTest.setEmailCT(emailCT_test);
		ferieTest.setEmailVF(emailVF_test);
	}

	private void aggiungiFerie(FerieBean ferie) {
		PreparedStatement ps = null;
		
		String aggiuntaFerieSQL =
				"INSERT INTO Ferie(dataInizio, dataFine, emailCT, emailVF)"
				+ " VALUES(?, ?, ?, ?);";

		 try {
			 Connection con = null;
			 	try{
			 		con = ConnessioneDB.getConnection();
			 		
			 		ps = con.prepareStatement(aggiuntaFerieSQL);
				
			 		ps.setDate(1, ferie.getDataInizio());
			 		ps.setDate(2, ferie.getDataFine());
			 		ps.setString(3, ferie.getEmailCT());
			 		ps.setString(4, ferie.getEmailVF());
			    
			 		ps.executeUpdate(); 
			 			
			 		con.commit();
			 	}finally {
			 		ConnessioneDB.releaseConnection(con);
			 	}
		 }catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddFerie_SenzaEccezioni() {
		boolean oracolo = true;
		boolean result = FerieDao.aggiungiPeriodoFerie(emailCT_test, emailVF_test, dataInizio_test, dataFine_test);
		assertEquals(oracolo, result);
	}
	
	@Test
	void testAddFerieNull() {
		boolean result = FerieDao.aggiungiPeriodoFerie(null, null, null, null);
		assertFalse(result);
	}
	
	@Test
	void testAddFerie_ConEccezioni() {
		boolean oracolo = false;
		boolean result = FerieDao.aggiungiPeriodoFerie(emailCT_test, null, dataInizio_test, dataFine_test);
		assertEquals(result,oracolo);
	}

	

	@Test
	void testOttieniFerie_SenzaEccezioni() {
		List<FerieBean> ferieInserite = new ArrayList<FerieBean>();
		boolean result = true;
		boolean oracolo = true;
		
		this.aggiungiFerie(ferieTest);
		ferieInserite.add(ferieTest);
		
		List<FerieBean> ferieInDb = FerieDao.ottieniFerieConcesse(ferieTest.getEmailVF());
		
		for(int i=0; i < ferieInDb.size(); i++) {
			for(int j=0; j < ferieInserite.size() - 1; j++) {
				if(!ferieInDb.get(i).equals(ferieInserite.get(j)))
					result = false;
			}
		}
		
		assertEquals(result, oracolo);
	}

	
	@Test
	void testRimuoviPeriodoFerieMaggioreZero() {
		boolean oracolo = true;
		boolean result = FerieDao.rimuoviPeriodoFerie(emailVF_test, dataInizio_test, dataFine_test);
		assertEquals(oracolo, result);
	}
	
	@Test
	void testRimuoviPeriodoFerieMinoreZero() {
		boolean oracolo = false;
		boolean result = FerieDao.rimuoviPeriodoFerie(emailVF_test, dataInizio_delete, dataFine_delete);
		assertEquals(oracolo, result);
	}
	
	@Test
	void testRimuoviPeriodoFerie_ConEccezione() {
		boolean oracolo = false;
		boolean result = FerieDao.rimuoviPeriodoFerie(emailVF_test, null, dataFine_delete);
		assertEquals(oracolo, result);
	}
	
	@Test
	void contieneGiorniConcessi() {
		boolean oracolo = false;
		boolean result = FerieDao.contieneGiorniConcessi(dataInizio_giusta,dataFine_giusta,emailVF_test);
		assertEquals(result,oracolo);
	}
	
	@Test
	void testFerieInRange() {
		boolean oracolo = true;
		boolean result = false;
		List<FerieBean> risultato = FerieDao.ferieInRange(dataInizio_test, dataFine_test, emailVF_test);
		if(!risultato.isEmpty())
			result = true;
		assertEquals(result, oracolo);
	}
	
	@Test
	void testFerieInRange_conEccezione() {
		boolean oracolo = false;
		boolean result = false;
		List<FerieBean> risultato = FerieDao.ferieInRange(dataInizio_giusta, dataFine_giusta, emailVF_test);
		if(!risultato.isEmpty())
			result = true;
		assertEquals(result, oracolo);
	}
	
	@AfterAll
	static void tearDown() throws Exception {
		FerieDao.rimuoviPeriodoFerie(emailVF_test, dataInizio_test, dataFine_test);
	}
	


	private static final
	Date dataInizio_giusta = Date.valueOf("2020-05-01");
	private static final
	Date dataFine_giusta = Date.valueOf("2020-05-05");
	private static final
	Date dataInizio_test = Date.valueOf("2020-03-07");
	private static final
	Date dataFine_test = Date.valueOf("2020-03-11");
	private static final
	String emailCT_test ="capoturno";
	private static final
	String emailVF_test = "domenico.giordano@vigilfuoco.it";
	private static final
	Date dataInizio_delete = Date.valueOf("2020-03-09");
	private static final
	Date dataFine_delete = Date.valueOf("2020-03-10");
	
	private static FerieBean ferieTest;
	
}
