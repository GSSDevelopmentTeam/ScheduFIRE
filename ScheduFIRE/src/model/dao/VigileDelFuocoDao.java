package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.ConnessioneDB;
import model.bean.VigileDelFuocoBean;

/**
 * Classe che si occupa della gestione dei dati 
 * persistenti relativi all'entita' 'VigileDelFuoco'
 * @author Eugenio Sottile 
 * @author Nicola Labanca
 * @author Alfredo Giuliano
 * @author Emanuele Bombardelli
 */

public class VigileDelFuocoDao {
	
	//Costanti
	
	public static final int ORDINA_PER_NOME = 0;
	
	public static final int ORDINA_PER_COGNOME = 1;
	
	public static final int ORDINA_PER_CARICO_LAVORO = 2;
	
	public static final int ORDINA_PER_GIORNI_FERIE_ANNO_CORRENTE = 3;
	
	public static final int ORDINA_PER_GIORNI_FERIE_ANNI_PRECEDENTI = 4;
	
	public static final int ORDINA_PER_MANSIONE = 5;
	
	public static final int ORDINA_PER_GRADO = 6;
	
	public static final int ORDINA_PER_FERIE_TOTALI = 7;
	
	private static final String[] ORDINAMENTI = {"order by nome", "order by cognome", "order by caricolavoro",
												"order by giorniferieannocorrente", "order by giorniferieannoprecedente",
												"order by mansione", "order by grado", "ORDER BY ferie DESC"};
	
	/**
	 * Si occupa del salvataggio dei dati di un Vigile del Fuoco nel database.
	 * @param vf Il vigile da memorizzare del database
	 * @return TRUE se l'operazione va a buon fine, FALSE altrimenti
	 */
	public static boolean salva(VigileDelFuocoBean vf) {
		
		//controlli
		if(vf == null)
			//lancio eccezione
			;

		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("insert into vigile(email, nome, cognome, turno, mansione, "
														+ "giorniferieannocorrente, giorniferieannoprecedente, caricolavoro, "
														+ "adoperabile, grado, username) values (?, ?, ? ,? ,? ,? ,? , ? ,?, ?, ?);");
			ps.setString(1, vf.getEmail());
			ps.setString(2, vf.getNome());
			ps.setString(3, vf.getCognome());
			ps.setString(4, vf.getTurno());
			ps.setString(5, vf.getMansione());
			ps.setInt(6, vf.getGiorniFerieAnnoCorrente());
			ps.setInt(7, vf.getGiorniFerieAnnoPrecedente());
			ps.setInt(8, vf.getCaricoLavoro());
			ps.setBoolean(9, vf.isAdoperabile());
			ps.setString(10, vf.getGrado());
			ps.setString(11, vf.getUsername());
			ps.executeUpdate();
			con.commit();

			return true;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		
	} 
	
	/**
	 * Si occupa dell'ottenimento di un Vigile del Fuoco dal database data la sua chiave.
	 * @param chiaveEmail una stringa contenente la mail del Vigile
	 * @return il Vigile identificato da chiaveEmail (puï¿½ essere null)
	 */
	public static VigileDelFuocoBean ottieni(String chiaveEmail) {
		
		//controlli
		if(chiaveEmail == null)
			throw new NullPointerException();
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select * from Vigile where email = ?;");
			ps.setString(1, chiaveEmail);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) { 
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				//Instanziazione oggetto VigileDelFuocoBean
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				return vf;
			} else { 
				return null;
			}
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true.
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 */
	public static List<VigileDelFuocoBean> ottieni() {
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select * from Vigile where adoperabile = true ORDER BY cognome;");
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			List<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	} 
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 */
	public static Collection<VigileDelFuocoBean> ottieni(int ordinamento) {
		
		if(ordinamento < 0 || ordinamento > 7)
			throw new NullPointerException();
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select *, (giorniferieannocorrente + giorniferieannoprecedente) as ferie from Vigile where adoperabile = true " +
														ORDINAMENTI[ordinamento] + ";");
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			ArrayList<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true e che risultano in malattia alla data passata per parametro.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @param data e' il giorno per cui viene effettuato il controllo
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a TRUE 
	 */
	public static Collection<VigileDelFuocoBean> ottieniInFerie(int ordinamento,Date data) {
		
		if(ordinamento < 0 || ordinamento > 7)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select *, (giorniferieannocorrente + giorniferieannoprecedente) as ferie FROM Vigile v JOIN  Ferie f on v.email=f.emailvf where adoperabile = true And ? BETWEEN DataInizio And DataFine " + 
					" " +
														ORDINAMENTI[ordinamento] + ";");
			ps.setDate(1, data);
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			ArrayList<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con campo 'adoperabile' settato a true e che risultano in malattia alla data passata per parametro.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @param data Ã¨ il giorno per cui viene effettuato il controllo
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 */
	public static Collection<VigileDelFuocoBean> ottieniInMalattia(int ordinamento,Date data) {
		
		if(ordinamento < 0 || ordinamento > 7)
			throw new NullPointerException();
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select *, (giorniferieannocorrente + giorniferieannoprecedente) as ferie FROM Vigile v JOIN  Malattia m on v.email=m.emailvf where adoperabile = true And ? BETWEEN DataInizio And DataFine " + 
					ORDINAMENTI[ordinamento] + ";");
			ps.setDate(1, data);
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			ArrayList<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con mansione 'Capo Squadra' con campo 'adoperabile' settato a true.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * e mansione 'Capo Squadra' 
	 */
	public static Collection<VigileDelFuocoBean> ottieniCapiSquadra(int ordinamento) {
		
		if(ordinamento < 0 || ordinamento > 7)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select *, (giorniferieannocorrente + giorniferieannoprecedente) as ferie from Vigile"
														+ " where mansione = 'Capo Squadra' and adoperabile = true " + ORDINAMENTI[ordinamento] + ";");
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			ArrayList<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con mansione 'Autista' con campo 'adoperabile' settato a true.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * e mansione 'Autista' 
	 */
	public static Collection<VigileDelFuocoBean> ottieniAutisti(int ordinamento) {
		
		if(ordinamento < 0 || ordinamento > 7)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select *, (giorniferieannocorrente + giorniferieannoprecedente) as ferie from Vigile"
														+ " where mansione = 'Autista' and adoperabile = true " + ORDINAMENTI[ordinamento] + ";");
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			ArrayList<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 * Si occupa dell'ottenimento di una collezione di VigileDelFuocoBean dal database
	 * con mansione 'Vigile' con campo 'adoperabile' settato a true.
	 * @param ordinamento e' un intero che determina il tipo di ordinamento della collezione
	 * @return una collezione di VigileDelFuocoBean con campo 'adoperabile' settato a true 
	 * e mansione 'Vigile' 
	 */
	public static Collection<VigileDelFuocoBean> ottieniVigili(int ordinamento) {
		
		if(ordinamento < 0 || ordinamento > 7)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select *, (giorniferieannocorrente + giorniferieannoprecedente) as ferie from Vigile"
														+ " where mansione = 'Vigile' and adoperabile = true " + ORDINAMENTI[ordinamento] + ";");
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			ArrayList<VigileDelFuocoBean> vigili = new ArrayList<VigileDelFuocoBean>();
			
			//Iterazione sui risultati
			while(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);
				
				vigili.add(vf);
				
			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 * Si occupa dell'ottenimento del valore minimo di Carico di lavoro
	 * tra i Vigili del Fuoco presenti nel database
	 * @return il minimo Carico di Lavoro
	 */
	public static int getCaricoLavoroMinimo() {
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select MIN(caricolavoro) as minimo from Vigile where adoperabile = true;");
			ResultSet rs = ps.executeQuery();
			int minimo = 0;
			if(rs.next())
				minimo = rs.getInt("minimo");

			return minimo;
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	/**
	 * Si occupa del settaggio del campo 'adoperabile' di un Vigile del Fuoco
	 * nel database, identificato dalla sua chiave.
	 * @param chiaveEmail e' una stringa che identifica un VigileDelFuocoBean nel database
	 * @param adoperabile e' un booleano che indica l'adoperabilitï¿½ di un VigileDelFuocoBean
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public static boolean setAdoperabile(String chiaveEmail, boolean adoperabile) {
		
		//controlli
		if(chiaveEmail == null)
			throw new NullPointerException();
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("update Vigile set adoperabile = ? where email = ?;");
			ps.setBoolean(1, adoperabile);
			ps.setString(2, chiaveEmail);
			ps.executeUpdate();
			con.commit();
			
			return true;
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**
	 * Si occupa della modifica dei dati di un Vigile del Fuoco nel database.
	 * @param chiaveEmail e' una stringa che identifica un VigileDelFuocoBean nel database
	 * @param nuovoVF un oggetto di tipo VigileDelFuocoBean
	 * @return TRUE se l'operazione va a buon fine, FALSE altrimenti
	 */
	public static boolean modifica(String chiaveEmail, VigileDelFuocoBean nuovoVF) {
		
		//controlli
		if(chiaveEmail == null || nuovoVF == null)
			throw new NullPointerException();
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("update Vigile set email = ?, nome = ?, cognome = ?,"
														+ " mansione = ?, turno = ?, giorniferieannocorrente = ?,"
														+ " giorniferieannoprecedente = ?, caricolavoro = ?,"
														+ " grado = ?, username = ? where email = ?;");		
			ps.setString(1, nuovoVF.getEmail());
			ps.setString(2, nuovoVF.getNome());
			ps.setString(3, nuovoVF.getCognome());
			ps.setString(4, nuovoVF.getMansione());
			ps.setString(5, nuovoVF.getTurno());
			ps.setInt(6, nuovoVF.getGiorniFerieAnnoCorrente());
			ps.setInt(7, nuovoVF.getGiorniFerieAnnoPrecedente());
			ps.setInt(8, nuovoVF.getCaricoLavoro());
			ps.setString(9, nuovoVF.getGrado());
			ps.setString(10, nuovoVF.getUsername());
			ps.setString(11, chiaveEmail);
			ps.executeUpdate();
			con.commit();
			
			return true;
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
	/**
	 * @param data la data del giorno di cui si vuole avere la lista dei vigili disponibili
	 * @return una lista di VigileDelFuocoBean che hanno attributo adoperabile=true 
	 * 			e non sono in ferie o malattia nella data passata come parametro
	 */
	public static ArrayList<VigileDelFuocoBean> getDisponibili(Date data){
		try(Connection con = ConnessioneDB.getConnection()) {

			// Query di ricerca
			PreparedStatement ps = con.prepareStatement("(SELECT v.email, v.nome, v.cognome, v.turno, v.mansione, "
					+ "v.giorniferieannocorrente, v.giorniferieannoprecedente, v.caricolavoro, v.adoperabile, v.grado, v.username " + 
					" FROM Vigile v " + 
					" WHERE v.adoperabile=true AND NOT EXISTS " + 
					" (SELECT *" + 
					" FROM Malattia m " + 
					" WHERE m.emailVF= v.email AND ? BETWEEN m.dataInizio AND m.dataFine)" + 
					" AND NOT EXISTS" + 
					" (SELECT *" + 
					" FROM Ferie f " + 
					" WHERE f.emailVF= v.email AND ? BETWEEN f.dataInizio AND f.dataFine))"
					+ " ORDER BY v.cognome;");
			ps.setDate(1, data);
			ps.setDate(2, data);
			ResultSet rs = ps.executeQuery();
			ArrayList<VigileDelFuocoBean> vigili=new ArrayList<>();

			//Iterazione dei risultati
			while(rs.next()) {

				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);

				vigili.add(vf);

			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * Restituisce una lista di vigili disponibili nella data richiesta
	 * @param ordinamento come si vuole avere ordinata la lista
	 * @param data , la data del giorno di cui si vuole avere la lista dei vigili disponibili
	 * @return una lista di VigileDelFuocoBean che hanno attributo adoperabile=true 
	 * e non sono in ferie o malattia nella data passata come parametro
	 */
	public static ArrayList<VigileDelFuocoBean> getDisponibili(Date data,int ordinamento){
		try(Connection con = ConnessioneDB.getConnection()) {

			// Query di ricerca
			PreparedStatement ps = con.prepareStatement("(SELECT v.email, v.nome, v.cognome, v.turno, v.mansione, "
					+ "v.giorniferieannocorrente, v.giorniferieannoprecedente, v.caricolavoro, v.adoperabile, v.grado, v.username " + 
					" FROM Vigile v " + 
					" WHERE v.adoperabile=true AND NOT EXISTS " + 
					" (SELECT *" + 
					" FROM Malattia m " + 
					" WHERE m.emailVF= v.email AND ? BETWEEN m.dataInizio AND m.dataFine)" + 
					" AND NOT EXISTS" + 
					" (SELECT *" + 
					" FROM Ferie f " + 
					" WHERE f.emailVF= v.email AND ? BETWEEN f.dataInizio AND f.dataFine))"
					+ ORDINAMENTI[ordinamento]+";");
			ps.setDate(1, data);
			ps.setDate(2, data);
			ResultSet rs = ps.executeQuery();
			ArrayList<VigileDelFuocoBean> vigili=new ArrayList<>();

			//Iterazione dei risultati
			while(rs.next()) {

				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				String mansione = rs.getString("mansione");
				int giorniFerieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				int giorniFerieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				int caricoLavoro = rs.getInt("caricolavoro");
				boolean adoperabile = rs.getBoolean("adoperabile");
				String grado = rs.getString("grado");
				VigileDelFuocoBean vf = new VigileDelFuocoBean();
				vf.setNome(nome);
				vf.setCognome(cognome);
				vf.setEmail(email);
				vf.setTurno(turno);
				vf.setUsername(username);
				vf.setMansione(mansione);
				vf.setGiorniFerieAnnoCorrente(giorniFerieAnnoCorrente);
				vf.setGiorniFerieAnnoPrecedente(giorniFerieAnnoPrecedente);
				vf.setCaricoLavoro(caricoLavoro);
				vf.setAdoperabile(adoperabile);
				vf.setGrado(grado);

				vigili.add(vf);

			}
			
			return vigili;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Metodo per sapere se un vigile è disponibile
	 * @param email la mail del Vigile del quale si vuole conoscere la disponibilità
	 * @param data la data del giorno di cui si vuole conoscere la disponibilità
	 * @return true se il vigile è disponibile, false altrimenti-
	 */
	public static boolean isDisponibile(String email, Date data){
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Query di ricerca
			PreparedStatement ps = con.prepareStatement("(SELECT v.email, v.nome, v.cognome, v.turno, v.mansione, "
					+ "v.giorniferieannocorrente, v.giorniferieannoprecedente, v.caricolavoro, v.adoperabile, v.grado, v.username " + 
					" FROM Vigile v " + 
					" WHERE v.adoperabile=true AND v.email=? AND NOT EXISTS " + 
					" (SELECT *" + 
					" FROM Malattia m " + 
					" WHERE m.emailVF= v.email AND ? BETWEEN m.dataInizio AND m.dataFine)" + 
					" AND NOT EXISTS" + 
					" (SELECT *" + 
					" FROM Ferie f " + 
					" WHERE f.emailVF= v.email AND ? BETWEEN f.dataInizio AND f.dataFine))"
					+ " ORDER BY v.cognome;");
			ps.setString(1, email);
			ps.setDate(2, data);
			ps.setDate(3, data);
			ResultSet rs = ps.executeQuery();

			boolean disponibile=false;
			if(rs.next()) {
				disponibile=true;
			}
			
			return disponibile;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**
	 * Questo metodo si occupa di prelevare la lista completa dei VF presenti nel dataBase. 
	 * @return una lista di VigileDelFuocoBean ordinata in base alla mansione piÃ¹ importante.
	 */
	public static ArrayList<VigileDelFuocoBean> ottieniListaVF() {
		
		//Instanziazione variabili utili;
		String nome, cognome, email, mansione, grado, turno, username;
		int ferieAnnoCorrente, ferieAnnoPrecedente;
		PreparedStatement ps;
		ResultSet rs;
		VigileDelFuocoBean vigile;
		
		
		//ArrayList da ritornare
		ArrayList<VigileDelFuocoBean> listaVigili = new ArrayList<VigileDelFuocoBean>();;
		
		//Query da eseguire
		String capiSquadraSQL = "SELECT * FROM Vigile WHERE mansione = 'Capo Squadra' ORDER BY cognome;";
		String autistiSQL = "SELECT * FROM Vigile WHERE mansione = 'Autista' ORDER BY cognome;";
		String vigiliSQL = "SELECT * FROM Vigile WHERE mansione = 'Vigile' ORDER BY cognome;";
		
		try(Connection connessione = ConnessioneDB.getConnection()){
			
			ps = connessione.prepareStatement(capiSquadraSQL);
			rs = ps.executeQuery();
			
			
			
			//Prelevamento risultati
			while(rs.next()) {
				vigile = new VigileDelFuocoBean();
				
				nome = rs.getString("nome");
				cognome = rs.getString("cognome");
				email = rs.getString("email");
				mansione = rs.getString("mansione");
				ferieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				ferieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				grado = rs.getString("grado");
				turno = rs.getString("turno");
				username = rs.getString("username");
				
				
				//Aggiunta dati del VF
				vigile.setNome(nome);
				vigile.setCognome(cognome);
				vigile.setEmail(email);
				vigile.setMansione(mansione);
				vigile.setGiorniFerieAnnoCorrente(ferieAnnoCorrente);
				vigile.setGiorniFerieAnnoPrecedente(ferieAnnoPrecedente);
				vigile.setGrado(grado);
				vigile.setTurno(turno);
				vigile.setUsername(username);
				
				//Aggiunta del VF in lista di ritorno
				listaVigili.add(vigile);
			}
			
			ps = connessione.prepareStatement(autistiSQL);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				vigile = new VigileDelFuocoBean();
				
				nome = rs.getString("nome");
				cognome = rs.getString("cognome");
				email = rs.getString("email");
				mansione = rs.getString("mansione");
				ferieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				ferieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				grado = rs.getString("grado");
				turno = rs.getString("turno");
				username = rs.getString("username");
				
				vigile.setNome(nome);
				vigile.setCognome(cognome);
				vigile.setEmail(email);
				vigile.setMansione(mansione);
				vigile.setGiorniFerieAnnoCorrente(ferieAnnoCorrente);
				vigile.setGiorniFerieAnnoPrecedente(ferieAnnoPrecedente);
				vigile.setGrado(grado);
				vigile.setTurno(turno);
				vigile.setUsername(username);
				
				listaVigili.add(vigile);
			}
			
			ps = connessione.prepareStatement(vigiliSQL);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				vigile = new VigileDelFuocoBean();
				
				nome = rs.getString("nome");
				cognome = rs.getString("cognome");
				email = rs.getString("email");
				mansione = rs.getString("mansione");
				ferieAnnoCorrente = rs.getInt("giorniferieannocorrente");
				ferieAnnoPrecedente = rs.getInt("giorniferieannoprecedente");
				grado = rs.getString("grado");
				turno = rs.getString("turno");
				username = rs.getString("username");
				
				vigile.setNome(nome);
				vigile.setCognome(cognome);
				vigile.setEmail(email);
				vigile.setMansione(mansione);
				vigile.setGiorniFerieAnnoCorrente(ferieAnnoCorrente);
				vigile.setGiorniFerieAnnoPrecedente(ferieAnnoPrecedente);
				vigile.setGrado(grado);
				vigile.setTurno(turno);
				vigile.setUsername(username);
				
				listaVigili.add(vigile);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return listaVigili;
	}

	/**
	 * Si occupa del prelevamento del numero di ferie accumulate negli anni precedenti dal VF, dal dataBase.
	 * @param emailVF l'email del VF del quale si ha bisogno del numero di ferie degli anni precedenti
	 * @return numero di ferie a disposizione degli anni precedenti
	 */
	public static int ottieniNumeroFeriePrecedenti(String emailVF) {
		PreparedStatement ps;
		ResultSet rs;
		int feriePrecedenti = 0;
		
		String FerieAnnoPSQL = "SELECT giorniFerieAnnoPrecedente FROM Vigile WHERE email = ?;";
		
		try{
			Connection connessione=null;
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(FerieAnnoPSQL);
				ps.setString(1, emailVF);
				rs = ps.executeQuery();
				rs.next();
				feriePrecedenti = rs.getInt("giorniFerieAnnoPrecedente");
			}
			finally {
				ConnessioneDB.releaseConnection(connessione);	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return feriePrecedenti;
	}

	/**
	 * Si occupa del prelevamento del numero di ferie dell'anno corrente a disposizione del VF, dal dataBase.
	 * @param emailVF l'email del VF del quale si ha bisogno del numero di ferie degli anni precedenti
	 * @return numero di ferie a disposizione relative all'anno corrente
	 */
	public static int ottieniNumeroFerieCorrenti(String emailVF) {
		PreparedStatement ps;
		ResultSet rs;
		int ferieCorrenti = 0;
		
		String FerieAnnoCSQL = "SELECT giorniFerieAnnoCorrente FROM Vigile WHERE email = ?;";
		
		try{
			Connection connessione=null;
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(FerieAnnoCSQL);
				ps.setString(1, emailVF);
				rs = ps.executeQuery();
				rs.next();
				ferieCorrenti = rs.getInt("giorniFerieAnnoCorrente");
			}
			finally {
				ConnessioneDB.releaseConnection(connessione);	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ferieCorrenti;
	}
	
	/**
	 * Si occupa dell'aggiornamento nel dataBase, del numero di ferie accumulate negli anni precedenti dal VF
	 * @param emailVFl'email del VF per il quale bisogna aggiornare il numero di ferie degli anni precedenti
	 * @param il nuovo numero di ferie relative all'anno precedente, da inserire nel dataBase
	 */
	public static void aggiornaFeriePrecedenti(String emailVF, int numeroFerie) {
		PreparedStatement ps;
		
		String aggiornaFeriePSQL = "UPDATE Vigile SET giorniFerieAnnoPrecedente = ? WHERE email = ?;";
		
		try{
			Connection connessione=null;
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(aggiornaFeriePSQL);
				ps.setInt(1, numeroFerie);
				ps.setString(2, emailVF);
				ps.executeUpdate();
				connessione.commit();
			}
			finally {
				ConnessioneDB.releaseConnection(connessione);	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Si occupa dell'aggiornamento nel dataBase, del numero di ferie relativo all'anno corrente, a disposizione del VF
	 * @param emailVF l'email del VF per il quale bisogna aggiornare il numero di ferie relativo all'anno corrente
	 * @param numeroFerie il nuovo numero di ferie relativo all'anno corrente, da inserire nel dataBase
	 */
	public static void aggiornaFerieCorrenti(String emailVF, int numeroFerie) {
		PreparedStatement ps;
		
		String aggiornaFerieCSQL = "UPDATE Vigile SET giorniFerieAnnoCorrente = ? WHERE email = ?;";
		
		try{
			Connection connessione=null;
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(aggiornaFerieCSQL);
				ps.setInt(1, numeroFerie);
				ps.setString(2, emailVF);
				ps.executeUpdate();
				connessione.commit();
			}
			finally {
				ConnessioneDB.releaseConnection(connessione);	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Il metodo incrementa per ogni vigile del fuoco (nella mappa 'Key') il suo carico lavorativo
	 * a seconda della squadra assegnata (nella mappa 'Value'). 
	 * @param squadra Una mappa di Vigili del Fuoco e delle relative squadre 
	 * @return TRUE se l'operazione va a buon fine, FALSE altrimenti.
	 */
	public static boolean caricoLavorativo(HashMap<VigileDelFuocoBean, String> squadra) {
		try (Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps;
			String incrementaCaricoLavorativo = "UPDATE Vigile Set caricolavoro = ? WHERE email = ?;";
			int count = 0;

			Iterator i = squadra.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry<VigileDelFuocoBean, String> pair = (Map.Entry<VigileDelFuocoBean, String>) i.next();

				int toAdd = (	pair.getValue().equals("Prima Partenza") || 
								pair.getValue().equals("Sala Operativa")) ? 3 :
								(pair.getValue().equals("Auto Scala")) ? 2 : 1;

				ps = con.prepareStatement(incrementaCaricoLavorativo);
				VigileDelFuocoBean vigile=VigileDelFuocoDao.ottieni(pair.getKey().getEmail());
				ps.setInt(1, vigile.getCaricoLavoro() + toAdd);
				ps.setString(2, pair.getKey().getEmail());
				count += ps.executeUpdate();
				con.commit();
			}
			return (count == squadra.size());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Il metodo decrementa per ogni vigile del fuoco (nella mappa 'Key') il suo carico lavorativo
	 * a seconda della squadra assegnata (nella mappa 'Value'). 
	 * @param squadra Una mappa di Vigili del Fuoco e delle relative squadre 
	 * @return TRUE se l'operazione va a buon fine, FALSE altrimenti.
	 */
	public static boolean removeCaricoLavorativo(HashMap<VigileDelFuocoBean, String> squadra) {
		try (Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps;
			String incrementaCaricoLavorativo = "UPDATE Vigile Set caricolavoro = ? WHERE email = ?;";

			Iterator i = squadra.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry<VigileDelFuocoBean, String> pair = (Map.Entry<VigileDelFuocoBean, String>) i.next();
				int toSub = (	pair.getValue().equals("Prima Partenza") || 
								pair.getValue().equals("Sala Operativa")) ? 3 :
								(pair.getValue().equals("Auto Scala")) ? 2 : 1;
				ps = con.prepareStatement(incrementaCaricoLavorativo);
				ps.setInt(1, pair.getKey().getCaricoLavoro() - toSub);
				ps.setString(2, pair.getKey().getEmail());
				ps.executeUpdate();
				con.commit();
//				i.remove();
			}
			
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean removeVigileDelFuoco(String string) {
		try (Connection con = ConnessioneDB.getConnection()){
			PreparedStatement ps = con.prepareStatement("DELETE FROM vigile WHERE email = ?;");
			ps.setString(1, string);
			return (ps.executeUpdate() == 1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



/**
 * Si occupa della rimozione di un Vigile del Fuoco dal database data la sua chiave.
 * @param chiaveEmail una stringa contenente la mail del Vigile
 * @return TRUE se la cancellazione e' andata a buon fine 
 */
public static boolean deleteVF(String chiaveEmail) {
	
		
	try(Connection con = ConnessioneDB.getConnection()) {
		//Eseguo la rimozione del VF
		PreparedStatement p = con.prepareStatement("Delete from Vigile where email = ?;");
		p.setString(1, chiaveEmail);
		p.execute();
		con.commit();
		
		// Esecuzione query per controllare che l'eliminazione sia andata a buon fine
		PreparedStatement ps = con.prepareStatement("select * from Vigile where email = ?;");
		ps.setString(1, chiaveEmail);
		ResultSet rs = ps.executeQuery();
		

		if(rs.next()) return false;
		else return true;
			
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	
}
}
