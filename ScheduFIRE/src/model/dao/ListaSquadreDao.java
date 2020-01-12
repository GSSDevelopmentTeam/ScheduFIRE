package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnessioneDB;
import model.bean.ComponenteDellaSquadraBean;
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

	public static boolean isEsistente(Date data) {
		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM schedufire.listasquadre WHERE giornoLAvorativo= ? ;");
			ps.setDate(1, data);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
			else return false;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Rimuove tutte le squadre precedenti a questa data.
	 * @param data verranno cancellate tutte le Listesquadre precedenti a questa data.
	 * @return true se l'azione va a buon fine, false altrimenti.
	 */
	public static boolean rimuoviTutte(Date data) {
		String sql = "DELETE FROM ListaSquadre WHERE giornoLavorativo < ? ;";
		
		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, data);
			ps.executeUpdate();
			con.commit();
			return true;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
