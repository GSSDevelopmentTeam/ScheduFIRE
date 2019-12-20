package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnessioneDB;
import model.bean.CapoTurnoBean;
import model.bean.VigileDelFuocoBean;

/*
 * Classe che si occupa della gestione dei dati 
 * persistenti relativi all'entità 'VigileDelFuoco'
 */

public class VigileDelFuocoDao {

	public static boolean salva(VigileDelFuocoBean vf) {
		
		//controlli
		if(vf == null)
			//lancio eccezione
			;
		
		try(Connection con = ConnessioneDB.getConnection()) {
			
			// Esecuzione query
			PreparedStatement ps = con.prepareStatement("insert into VigileDelFuoco(email, nome, cognome, turno, mansione, "
														+ "giorniferieannocorrente, giorniferieannoprecedente, caricolavoro"
														+ "adoperabile, username) values (?, ?, ? ,? ,? ,? ,? , ?, ?, ?);");
			ps.setString(1, vf.getEmail());
			ps.setString(2, vf.getNome());
			ps.setString(3, vf.getCognome());
			ps.setString(4, vf.getTurno());
			ps.setString(5, vf.getMansione());
			ps.setInt(6, vf.getGiorniFerieAnnoCorrente());
			ps.setInt(7, vf.getGiorniFerieAnnoPrecedente());
			ps.setInt(8, vf.getCaricoLavoro());
			ps.setBoolean(9, vf.isAdoperabile());
			ps.setString(10, vf.getUsername());
			boolean result = ps.executeUpdate() == 1;
			
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	} 

}
