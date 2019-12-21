package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import model.ConnessioneDB;
import model.bean.VigileDelFuocoBean;

/**
 * Classe che si occupa della gestione dei dati 
 * persistenti relativi all'entità 'VigileDelFuoco'
 * @author Eugenio Sottile 
 */

public class VigileDelFuocoDao {

	
	/**
	 * @param vf , è un oggetto di tipo VigileDelFuocoBean
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public static boolean salva(VigileDelFuocoBean vf) {
		
		//controlli
		if(vf == null)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("insert into Vigile(email, nome, cognome, turno, mansione, "
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

			return (ps.executeUpdate() == 1);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	} 
	
	/**
	 * @param chiaveEmail , stringa che identifica un VigileDelFuocoBean
	 * @return Un tipo VigileDelFuocoBean identificato da chiaveEmail, null altrimenti
	 */
	public static VigileDelFuocoBean ottieni(String chiaveEmail) {
		
		//controlli
		if(chiaveEmail == null)
			//lancio eccezione
			;
		
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
							
				//Instanziazione oggetto CapoTurnoBean
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
	 * @return una collezione di VigileDelFuocoBean
	 */
	public static Collection<VigileDelFuocoBean> ottieni() {
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select * from Vigile where adoperabile = true;");
			ResultSet rs = ps.executeQuery();
			
			//Instanziazione del set dei Vigili del Fuoco
			HashSet<VigileDelFuocoBean> vigili = new HashSet<VigileDelFuocoBean>();
			
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
	 * @param chiaveEmail , stringa che identifica un VigileDelFuocoBean
	 * @param adoperabile, booleano che indica l'adoperabilità di un VigileDelFuocoBean
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public static boolean setAdoperabile(String chiaveEmail, boolean adoperabile) {
		
		//controlli
		if(chiaveEmail == null)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("update Vigile set adoperabile = ? where email = ?;");
			ps.setBoolean(1, adoperabile);
			ps.setString(2, chiaveEmail);
			
			return (ps.executeUpdate() == 1);
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * @param chiaveEmail , stringa che identifica un VigileDelFuocoBean
	 * @param nuovoVF ,  è un oggetto di tipo VigileDelFuocoBean
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public static boolean modifica(String chiaveEmail, VigileDelFuocoBean nuovoVF) {
		
		//controlli
		if(chiaveEmail == null)
			//lancio eccezione
			;
		
		if(nuovoVF == null)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("update Vigile set email = ?, nome = ?, cognome = ?,"
														+ " mansione = ?, giorniferieannocorrente = ?,"
														+ " giorniferieannoprecedente = ?,  grado = ? where email = ?;");		
			ps.setString(1, nuovoVF.getEmail());
			ps.setString(2, nuovoVF.getNome());
			ps.setString(3, nuovoVF.getCognome());
			ps.setString(4, nuovoVF.getMansione());
			ps.setInt(5, nuovoVF.getGiorniFerieAnnoCorrente());
			ps.setInt(6, nuovoVF.getGiorniFerieAnnoPrecedente());
			ps.setString(7, nuovoVF.getGrado());
			ps.setString(8, chiaveEmail);
			
			return (ps.executeUpdate() == 1);
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
