package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ConnessioneDB;
import util.GiornoLavorativo;

public class ListaSquadreDao {
	
	public static boolean aggiungiSquadre(Date data, String emailCT) {
		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps = con.prepareStatement("INSERT INTO listasquadre (giornoLavorativo, oraIniziale, "
					+ "emailCT) VALUES (?, ?, ?);");
			ps.setDate(1, data);
			ps.setInt(2, (GiornoLavorativo.isDiurno(data) ? 8 : 20));
			ps.setString(3, emailCT);
			boolean done=(ps.executeUpdate() == 1);
			con.commit();
			return done;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
