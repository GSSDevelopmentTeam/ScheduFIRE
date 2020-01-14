package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;

import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

import control.AutenticazioneException;
import control.GeneraSquadreServlet;
import model.ConnessioneDB;
import model.bean.GiorniMalattiaBean;
import model.bean.VigileDelFuocoBean;
import model.dao.FerieDao;
import model.dao.GiorniMalattiaDao;
import model.dao.VigileDelFuocoDao;

class VigileDelFuocoDao_Test {
	private static final Class SQLException = null;
	static MockHttpServletRequest request;
	static MockHttpServletResponse response;
	static MockHttpSession session;
	static GeneraSquadreServlet servlet;
	static HashMap<VigileDelFuocoBean, String> squadraDiurno;
	static VigileDelFuocoBean vf = new VigileDelFuocoBean();
	static String email;
    static ArrayList<VigileDelFuocoBean> controllo, ds, dp,all;
    static Collection<VigileDelFuocoBean> col, inF, inM, cp, au,vvf;
    static GiorniMalattiaBean mal;
    static HashMap<VigileDelFuocoBean, String> squadra= new HashMap<VigileDelFuocoBean, String>() ;
	@BeforeEach
	void setUp(){
						
		vf.setNome("Fabrizio");
		vf.setCognome("Romano");
		vf.setEmail("fabrizio.romano");
		vf.setTurno("B");
		vf.setMansione("Autista");
		vf.setAdoperabile(true);
		vf.setCaricoLavoro(20);
		vf.setGiorniFerieAnnoCorrente(0);
		vf.setGiorniFerieAnnoPrecedente(0);
		vf.setGrado("Coordinatore");
		vf.setUsername("turnoB");
		
		//Prendo i valori di confronto
		Date data= Date.valueOf("2020-02-10");
		Date data2= Date.valueOf("2020-02-14");
		email = vf.getEmail();
		controllo= (ArrayList<VigileDelFuocoBean>) VigileDelFuocoDao.ottieni();
		col = VigileDelFuocoDao.ottieni(5);
		cp = VigileDelFuocoDao.ottieniCapiSquadra(2);
		au = VigileDelFuocoDao.ottieniAutisti(1);
		vvf = VigileDelFuocoDao.ottieniVigili(6);
		ds = VigileDelFuocoDao.getDisponibili(data2);
		dp = VigileDelFuocoDao.getDisponibili(data, 5);
		all = VigileDelFuocoDao.ottieniListaVF();
		
		//Aggiungo Personale in Ferie e in malattia ;
		mal = new GiorniMalattiaBean();
		mal.setDataInizio(data);
		mal.setDataFine(data2);
		mal.setEmailCT("capoturno");
		mal.setEmailVF("rosario.marmo@vigilfuoco.it");
		GiorniMalattiaDao.addMalattia(mal);		
		FerieDao.aggiungiPeriodoFerie("capoturno", "marzia.mancuso@vigilfuoco.it", data, data2);
		inF= VigileDelFuocoDao.ottieniInFerie(3,data);
		inM = VigileDelFuocoDao.ottieniInMalattia(4, data);
		squadra.put(vf,"Sala Operativa");
	
	}
	
	@AfterAll
	static void tearDown(){
		Date data= Date.valueOf("2020-02-10");
		Date data2= Date.valueOf("2020-02-14");
		FerieDao.rimuoviPeriodoFerie("marzia.mancuso@vigilfuoco.it", data, data2);		
		GiorniMalattiaDao.rimuoviPeriodoDiMalattia("rosario.marmo@vigilfuoco.it", data, data2);
		VigileDelFuocoDao.deleteVF(vf.getEmail());
	}


	/**
	 * Si occupa del salvataggio dei dati di un Vigile del Fuoco nel database.
	 * @param VigileDelFuocoBean vf Il vigile da memorizzare del database
	 * @return boolean true se l'operazione va a buon fine, false altrimenti
	 * @precondition vf!=null
	 */
	@Test
	void testSalva() {		
		boolean atteso=true;
		boolean risultato = VigileDelFuocoDao.salva(vf);
		assertEquals(atteso,risultato);
	}
	@Test
	void testSalva2() {		
		boolean atteso = true;
		vf.setEmail("fabrizio.romano@vigilfuoco.it");
		boolean risultato = VigileDelFuocoDao.salva(vf);
		vf.setEmail(email);
		assertEquals(atteso,risultato);
				
	}

	/**
	 * Si occupa dell'ottenimento di un Vigile del Fuoco dal database data la sua chiave.
	 * @param chiave ->email una stringa contenente la mail del Vigile
	 * @return il Vigile identificato da chiaveEmail (puo' essere null)
	 */ 
	@Test
	void testOttieniString() {
		vf.setCaricoLavoro(23);
		VigileDelFuocoBean nuovo = VigileDelFuocoDao.ottieni(email);
		assertNotNull(nuovo);
	}
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true.
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 */
	@Test
	void testOttieni() {
		ArrayList<VigileDelFuocoBean> vigiliDB = new ArrayList<VigileDelFuocoBean>();
		vigiliDB = (ArrayList<VigileDelFuocoBean>) VigileDelFuocoDao.ottieni();
		
		boolean atteso = true;
		boolean risultato = false; 
		int l = vigiliDB.size();
		if(controllo.equals(vigiliDB)) risultato=true;
		
		assertEquals(risultato, atteso);		
	}

	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true.
	 * se il parametro int per l'ordinamento non e' del formato giusto lancia un eccezione 
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true
	 * @throws NullPointerException
	 * @precondition c>0 && c<7
	 */
	@Test
	void testOttieniIntFail() throws ArrayIndexOutOfBoundsException {
		int c = 9;
		assertThrows(NullPointerException.class,()->VigileDelFuocoDao.ottieni(c));		
	}
	@Test
	void testOttieniInt() {
		int c = 5;
		Collection<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieni(c);
		
		boolean atteso = true;
		boolean risultato = col.equals(db);
		
		assertEquals(atteso, risultato);
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true e che risultano in ferie alla data passata per parametro.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @param data e' il giorno per cui viene effettuato il controllo
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * @precondition i>0 && i<7
	 */
	@Test
	void testottieniInFerie() {
		int i = 3;
		Date data = Date.valueOf("2020-02-10");
		Collection<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieniInFerie(i, data);
		boolean atteso=true; 
		boolean risultato=db.equals(inF);
		
		assertEquals(atteso, risultato);	
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true e che risultano in malattia alla data passata per parametro.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @param data e' il giorno per cui viene effettuato il controllo
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * @precondiction i<0 && i>7
	 */
	@Test
	void testottieniInMalattia() {
		int i = 4;
		Date data = Date.valueOf("2020-02-10");		
		Collection<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieniInMalattia(i, data);
		
		boolean atteso=true;
		boolean risultato= inM.equals(db);
		
		assertEquals(atteso,risultato);
		
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con mansione 'Capo Squadra' con campo 'adoperabile' settato a true.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * e mansione 'Capo Squadra' 
	 * @precondition i>0 && i<7
	 */
	@Test
	void testottieniCapiSquadra() {
		int i=2;
		Collection<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieniCapiSquadra(i);
		
		boolean atteso = true;
		boolean risultato = cp.equals(db);
		
		assertEquals(atteso,risultato);		
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con mansione 'Autista' con campo 'adoperabile' settato a true.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * e mansione 'Autista' 
	 * @precondition i>0 && i<7
	 */
	@Test
	void testottieniAutisti() {
		int i=1;
		Collection<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieniAutisti(i);
		
		boolean atteso=true;
		boolean risultato = au.equals(db);
		
		assertEquals(atteso, risultato);		
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con mansione 'Vigile' con campo 'adoperabile' settato a true.
	 * @param ordinamento � un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * e mansione 'Vigile' 
	 */
	@Test
	void testottieniVigili() {
		int i=6;
		Collection<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieniVigili(i);
		
		boolean atteso=true;
		boolean risultato = vvf.equals(db);
		
		assertEquals(atteso,risultato);		
	}
	
	/**
	 * Si occupa dell'ottenimento del valore minimo di Carico di lavoro
	 * tra i Vigili del Fuoco presenti nel database
	 * @return il minimo Carico di Lavoro
	 */
	@Test
	void testgetCaricoLavoroMinimo() {
		int atteso = 112;
		int risultato = VigileDelFuocoDao.getCaricoLavoroMinimo();
		
		assertEquals(atteso, risultato);
	}
	
	/**
	 * Si occupa del settaggio del campo 'adoperabile' di un Vigile del Fuoco
	 * nel database, identificato dalla sua chiave.
	 * @param chiaveEmail e' una stringa che identifica un VigileDelFuocoBean nel database
	 * @param adoperabile e' un booleano che indica l'adoperabilit� di un VigileDelFuocoBean
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	@Test
	void testSetAdoperabile() {
		boolean atteso=true;
		boolean risultato = VigileDelFuocoDao.setAdoperabile("michele73.sica@vigilfuoco.it", false);
		
		assertEquals(atteso,risultato);
	}
	
	
	/**
	 * Si occupa della modifica dei dati di un Vigile del Fuoco nel database.
	 * @param chiaveEmail e' una stringa che identifica un VigileDelFuocoBean nel database
	 * @param nuovoVF ,  e' un oggetto di tipo VigileDelFuocoBean
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	@Test
	void testModifica() {
		boolean atteso=true;
		boolean risultato= VigileDelFuocoDao.modifica(email,vf);
		
		assertEquals(atteso,risultato);
	}
	
	/**
	 * @param data , la data del giorno di cui si vuole avere la lista dei vigili disponibili
	 * @return una lista di VigileDelFuocoBean che hanno attributo adoperabile=true 
	 * 			e non sono in ferie o malattia nella data passata come parametro
	 */
  @Test
	void testGetDisponibili() {
		Date data= Date.valueOf("2020-02-14");
		ArrayList<VigileDelFuocoBean> db = VigileDelFuocoDao.getDisponibili(data);
		boolean atteso=true;
		boolean risultato = ds.equals(db);
		
		assertEquals(atteso,risultato);
		
	}
	
	/**
	 * @param data , la data del giorno di cui si vuole avere la lista dei vigili disponibili
	 * @return una lista di VigileDelFuocoBean che hanno attributo adoperabile=true 
	 * 			e non sono in ferie o malattia nella data passata come parametro
	 * @precondition i>0 && i<7
	 */
	@Test
	void testGetDisponibiliOrdinamento() {
		Date data= Date.valueOf("2020-02-10");
		int i = 5;
		ArrayList<VigileDelFuocoBean> db = VigileDelFuocoDao.getDisponibili(data,i);
		boolean atteso=true;
		boolean risultato = dp.equals(db);
		
		assertEquals(atteso,risultato);
	}
	
	/**
	 * @param data , la data del giorno di cui si vuole avere la lista dei vigili disponibili
	 * @return una lista di VigileDelFuocoBean che hanno attributo adoperabile=true 
	 * 			e non sono in ferie o malattia nella data passata come parametro
	 */
	@Test 
	void testIsDisponibile() {
		boolean atteso= true;
		Date data= Date.valueOf("2020-02-10");;
		boolean risultato = VigileDelFuocoDao.isDisponibile(email, data);
		
		assertEquals(atteso,risultato);
	}
	
	/**
	 * Questo metodo si occupa di prelevare la lista completa dei VF presenti nel dataBase. 
	 * @return una lista di VigileDelFuocoBean ordinata in base alla mansione più importante.
	 */
	@Test
	void testOttieniListaVF() {
		ArrayList<VigileDelFuocoBean> db = VigileDelFuocoDao.ottieniListaVF();
		boolean atteso= true;
		boolean risultato = all.equals(db);
		
		assertEquals(atteso,risultato);
	}

	/**
	 * Si occupa del prelevamento del numero di ferie accumulate negli anni precedenti dal VF, dal dataBase.
	 * @param emailVF (String): L'email del VF del quale si ha bisogno del numero di ferie degli anni precedenti
	 * @return feriePrecedenti (int): numero di ferie a disposizione degli anni precedenti
	 */
	@Test
	void testOttieniNumeroFeriePrecedenti() throws SQLException {
		
		int risultato = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(email);
		int atteso = vf.getGiorniFerieAnnoPrecedente();
		
		assertEquals(atteso,risultato);	
	}
	
	/**
	 * Si occupa del prelevamento del numero di ferie dell'anno corrente a disposizione del VF, dal dataBase.
	 * @param emailVF (String): L'email del VF del quale si ha bisogno del numero di ferie degli anni precedenti
	 * @return ferieCorrenti (int): numero di ferie a disposizione relative all'anno corrente
	 */
	@Test
	void testOttieniNumeroFerieCorrenti() {
		int risultato = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(email);
		int atteso = vf.getGiorniFerieAnnoCorrente();
		
		assertEquals(atteso,risultato);
	}

	/**
	 * Si occupa dell'aggiornamento nel dataBase, del numero di ferie accumulate negli anni precedenti dal VF
	 * @param emailVF (String): L'email del VF per il quale bisogna aggiornare il numero di ferie degli anni precedenti
	 * @param numeroFerie (int): Il nuovo numero di ferie relative all'anno precedente, da inserire nel dataBase
	 */
	@Test
	void testAggiornaFeriePrecedenti() {
		int ferie= 0;
		VigileDelFuocoDao.aggiornaFeriePrecedenti(email, ferie);
		int risultato = VigileDelFuocoDao.ottieniNumeroFeriePrecedenti(email);
		
		assertEquals(ferie + vf.getGiorniFerieAnnoPrecedente(),risultato);
	}
	
	/**
	 * Si occupa dell'aggiornamento nel dataBase, del numero di ferie relativo all'anno corrente, a disposizione del VF
	 * @param emailVF (String): L'email del VF per il quale bisogna aggiornare il numero di ferie relativo all'anno corrente
	 * @param numeroFerie (int): Il nuovo numero di ferie relativo all'anno corrente, da inserire nel dataBase
	 */
	@Test
	void testAggiornaFerieCorrenti() {
		int ferie= 0;
		VigileDelFuocoDao.aggiornaFerieCorrenti(email, ferie);
		int risultato = VigileDelFuocoDao.ottieniNumeroFerieCorrenti(email);
		
		assertEquals(ferie,risultato);
	}

	/**
	 * Il metodo incrementa per ogni vigile del fuoco (nella mappa 'Key') il suo carico lavorativo
	 * a seconda della squadra assegnata (nella mappa 'Value'). 
	 * @param squadra Una mappa di Vigili del Fuoco e delle relative squadre 
	 * @return true se l'operazione va a buon fine, false altrimenti.
	 */
	@Test
	void testCaricoLavorativo() {
		int atteso = vf.getCaricoLavoro();
		VigileDelFuocoDao.caricoLavorativo(squadra);
	
		int risultato = vf.getCaricoLavoro();
		
		assertEquals(atteso, risultato);
	}

	/**
	 * Il metodo decrementa per ogni vigile del fuoco (nella mappa 'Key') il suo carico lavorativo
	 * a seconda della squadra assegnata (nella mappa 'Value'). 
	 * @param squadra Una mappa di Vigili del Fuoco e delle relative squadre 
	 * @return true se l'operazione va a buon fine, false altrimenti.
	 */
	@Test
	void testRemoveCaricoLavorativo() {
		int atteso = vf.getCaricoLavoro();
		VigileDelFuocoDao.removeCaricoLavorativo(squadra);
		int risultato = vf.getCaricoLavoro();
		
		assertEquals(atteso, risultato);
		
	}
	/**
	 * Si occupa della rimozione di un Vigile del Fuoco dal database data la sua chiave.
	 * @param chiaveEmail una stringa contenente la mail del Vigile
	 * @return true se la cancellazione e' andata a buon fine 
	 */
	@Test
	void testdeleteVF() {
		boolean atteso=true;
		boolean risultato = VigileDelFuocoDao.deleteVF("email");
		
		assertEquals(atteso, risultato);
	}

	
}
