package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ConnessioneDB;

public class SquadraDao {

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

}
