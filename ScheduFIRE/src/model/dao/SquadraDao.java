package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ConnessioneDB;

public class SquadraDao {

	/**
	 * Aggiunge al DB le 4 squadre alla data passata come parametro
	 * @param data la data in cui aggiungere le squadre
	 * @return TRUE se l'aggiunta va a buon fine, FALSE altrimenti
	 */
	public static boolean aggiungiSquadra(Date data) {
		int count = 0;
		try(Connection con = ConnessioneDB.getConnection()) {
			count += aggiungiPrimaP(con, data);
			count += aggiungiSalaOp(con, data);
			count += aggiungiAutoSc(con, data);
			count += aggiungiAutoBo(con, data);
			con.commit();
			return (count == 4);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static int aggiungiAutoBo(Connection con, Date data) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO squadra (tipologia, "
				+ "giornoLavorativo, caricoLavoro) VALUES (\"Auto Botte\", ?, 1);");
		ps.setDate(1 , data);
		return (ps.executeUpdate());
	}

	private static int aggiungiAutoSc(Connection con, Date data) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO squadra (tipologia, "
				+ "giornoLavorativo, caricoLavoro) VALUES (\"Auto Scala\", ?, 2);");
		ps.setDate(1 , data);
		return (ps.executeUpdate());
	}

	private static int aggiungiSalaOp(Connection con, Date data) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO squadra (tipologia, "
				+ "giornoLavorativo, caricoLavoro) VALUES (\"Sala Operativa\", ?, 3);");
		ps.setDate(1 , data);
		return (ps.executeUpdate());
	}

	private static int aggiungiPrimaP(Connection con, Date data) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO squadra (tipologia, "
				+ "giornoLavorativo, caricoLavoro) VALUES (\"Prima Partenza\", ?, 3);");
		ps.setDate(1 , data);
		return (ps.executeUpdate());
	}


	/**
	 * Rimuove tutte le squadre precedenti a questa data.
	 * @param data verranno cancellate tutte le Listesquadre precedenti a questa data.
	 * @return true se l'azione va a buon fine, false altrimenti.
	 */
	public static boolean rimuoviTutti(Date data) {
		String sql = "DELETE FROM Squadra WHERE giornoLavorativo < ? ;";

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
