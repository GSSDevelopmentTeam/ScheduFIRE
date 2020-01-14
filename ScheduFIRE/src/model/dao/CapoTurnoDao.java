package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ConnessioneDB;
import model.bean.CapoTurnoBean;

/**
 * Classe che si occupa della gestione dei dati 
 * persistenti relativi all'entità 'CapoTurno'
 * @author Eugenio Sottile
 */

public class CapoTurnoDao {
	
	/**
	 * Ottiene un capoturno dalla base di dati
	 * @param chiaveUsername stringa che identifica un CapoTurnoBean tramite l'username
	 * @return un tipo CapoTurnoBean identificato da chiaveUsername, null altrimenti
	 */
	public static CapoTurnoBean ottieni(String chiaveUsername) {

		try(Connection con = ConnessioneDB.getConnection()) {
				
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("select * from Capoturno where username = ?;");
			ps.setString(1, chiaveUsername);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				// Ottenimento dati dall'interrogazione
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String turno = rs.getString("turno");
				String username = rs.getString("username");
				
				//Instanziazione oggetto CapoTurnoBean
				CapoTurnoBean ct = new CapoTurnoBean();
				ct.setNome(nome);
				ct.setCognome(cognome);
				ct.setEmail(email);
				ct.setTurno(turno);
				ct.setUsername(username);
				
				return ct;
				
			} else {
				return null;
			}
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
