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

	/**
	 * Salva le squadre sul database
	 * @param data la data in cui si vogliono aggiungere le squadre
	 * @param emailCT la mail del Capo Turno
	 * @return TRUE se vengono aggiunte le squadre, FALSE altrimenti
	 */
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

	/**
	 * Verifica se alla data passata come parametro esistono le squadre sul database
	 * @param data la data per cui si vuole verificare l esistenza delle squadre
	 * @return TRUE se le squadre esistono sul database, FALSE altrimenti
	 */
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
	 * Questo metodo cancella tutte le squadre presenti sul database precedenti alla data passata come parametro
	 * @param data la data di partenza
	 * @return TRUE se va a buon fine
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
