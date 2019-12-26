package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ConnessioneDB;

public class ListaSquadreDao {

	public static boolean aggiungiSquadra(Date giornoLavorativo, int oraIniziale, String emailCT) {
		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps = con.prepareStatement("INSERT INTO listasquadre(giornoLavorativo, "
					+ "oraIniziale, emailCT) values (?, ?, ?);");
			ps.setDate(1, giornoLavorativo);
			ps.setInt(2, oraIniziale);
			ps.setString(3, emailCT);
			
			return(ps.executeUpdate() == 1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
