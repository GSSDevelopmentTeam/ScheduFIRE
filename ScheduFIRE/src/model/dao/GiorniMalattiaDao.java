package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import model.ConnessioneDB;
import model.bean.FerieBean;
import model.bean.GiorniMalattiaBean;

/**
 * Classe che si occupa della gestione dei dati
 * persistenti relativi ai periodi di malattia
 * @author Biagio Bruno
 * */

public class GiorniMalattiaDao {

	/**
	 * Il metodo aggiunge nel DB un oggetto di tipo GiorniMalattiaBean. Per informazioni sulla 
	 * classe, leggere la documentazione relativa.
	 * @param malattia L'oggetto da salvare sul DB.
	 * @return true se l'operazione va a buon fine, false altrimenti.
	 * @see GiorniMalattiaBean
	 */
	public static boolean addMalattia(GiorniMalattiaBean malattia) {
		boolean aggiunta = false;
		PreparedStatement ps = null;
		ResultSet res = null;

		String query = "INSERT INTO schedufire.malattia (dataInizio, dataFine, emailCT, emailVF) "
				+"values(?, ?, ?, ?);";
		//controllo
		if(malattia == null) {
			return false;
		}
		try {
			Connection con = ConnessioneDB.getConnection();
			// Esecuzione query
			ps = con.prepareStatement(query);

			ps.setDate(1, malattia.getDataInizio());
			ps.setDate(2, malattia.getDataFine());
			ps.setString(3, malattia.getEmailCT());
			ps.setString(4, malattia.getEmailVF());
			res = ps.getResultSet();

			if(ps.executeUpdate() > 0) 
				aggiunta = true;
			con.commit();

			ConnessioneDB.releaseConnection(con);
		}catch(SQLException e) {
			throw new RuntimeException();
		}

		return aggiunta;
	}


	/**
	 * Il metodo restituisce una Lista di GiorniMalattiaBean, con tutti i periodi di Malattia collegati 
	 * al Vigile del Fuoco passato come parametro 
	 * @param email il Vigile del Fuoco del quale si vogliono sapere i Giorni Malattia collegati
	 * @return La lista di giorni malattia legati al vigile
	 */
	public static List<GiorniMalattiaBean> ottieniMalattie(String email) {

		String emailVF;
		String emailCT;
		Date dataInizio;
		Date dataFine;
		PreparedStatement ps;
		ResultSet rs;
		GiorniMalattiaBean malattia;
		int idMalattia;

		List<GiorniMalattiaBean> periodiMalattia = new ArrayList<GiorniMalattiaBean>();

		String malattieSQL = "SELECT m.id, m.dataInizio, m.dataFine, m.emailCT, m.emailVF " +
				"FROM Malattia m WHERE m.emailVF = ? AND m.dataFine >= CURDATE() ORDER BY dataInizio;";
		try{
			Connection con= ConnessioneDB.getConnection();
			emailVF = email;

			ps = con.prepareStatement(malattieSQL);
			ps.setString(1, emailVF);

			rs = ps.executeQuery();

			while(rs.next()) {
				malattia = new GiorniMalattiaBean();

				idMalattia = rs.getInt("id");
				emailCT = rs.getString("emailCT");
				dataInizio = rs.getDate("dataInizio");
				dataFine = rs.getDate("dataFine");

				malattia.setId(idMalattia);
				malattia.setEmailVF(emailVF);
				malattia.setEmailCT(emailCT);
				malattia.setDataInizio(dataInizio);
				malattia.setDataFine(dataFine);

				periodiMalattia.add(malattia);
				con.commit();
				
				ConnessioneDB.releaseConnection(con);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return periodiMalattia;
	}

	
	/**
	 * Il metodo restituisce una Lista di GiorniMalattiaBean, con tutti i periodi di Malattia collegati 
	 * al Vigile del Fuoco passato come parametro nell'arco di tempo selezionato
	 * @param dataInizio Prima data del range (inclusivo)
	 * @param dataFine Ultima data del range (inclusivo)
	 * @param emailVF il Vigile del Fuoco del quale si vogliono sapere i Giorni Malattia collegati
	 * @return La lista di giorni malattia legati al vigile
	 */
	public static List<GiorniMalattiaBean> malattiaInRange(Date dataInizio, Date dataFine, String emailVF) {
		PreparedStatement ps;
		ResultSet rs;
		String verificaPeriodoSQL = "SELECT * FROM schedufire.malattia WHERE "
				+ "emailVF = ? AND ((dataInizio BETWEEN ? AND ? OR dataFine BETWEEN ? AND ?) "
				+ "OR (dataInizio < ? AND dataFine > ?) OR (dataInizio < ? AND dataFine > ?)) ORDER BY dataInizio;";

		List<GiorniMalattiaBean> malattia = new ArrayList<GiorniMalattiaBean>();
		try {
			Connection con = null;

			try {
				con = ConnessioneDB.getConnection();

				ps = con.prepareStatement(verificaPeriodoSQL);
				ps.setString(1, emailVF);
				ps.setDate(2, dataInizio);
				ps.setDate(3, dataFine);
				ps.setDate(4, dataInizio);
				ps.setDate(5, dataFine);
				ps.setDate(6, dataInizio);
				ps.setDate(7, dataInizio);
				ps.setDate(8, dataFine);
				ps.setDate(9, dataFine);

				rs = ps.executeQuery();

				while(rs.next()) {
					GiorniMalattiaBean Gio = new GiorniMalattiaBean();
					Gio.setId(rs.getInt("id"));
					Gio.setDataInizio(rs.getDate("dataInizio"));
					Gio.setDataFine(rs.getDate("dataFine"));
					Gio.setEmailCT(rs.getString("emailCT"));
					Gio.setEmailVF(rs.getString("emailVF"));
					malattia.add(Gio);
				}


			}finally {
				ConnessioneDB.releaseConnection(con);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return malattia;
	}

	
	/**
	 * Il metodo rimuove un periodo di malattia collegato ad un Vigile del Fuoco passato come parametro
	 * @param emailVF Il vigile del fuoco al quale si vuole rimuovere un periodo di malattia
	 * @param dataInizio L'inizio del periodo di malattia
	 * @param dataFine La fine del periodo di malattia
	 * @return true se l'operazione va a buon fine, false altrimenti.
	 */
	public static boolean rimuoviPeriodoDiMalattia(String emailVF, Date dataInizio, Date dataFine) {

		boolean rimozione = false;
		PreparedStatement ps;

		String rimozioneMalattiaSQL = "DELETE FROM Malattia WHERE (emailVF = ? AND dataInizio = ? AND dataFine = ?);";

		try{
			Connection con=null;
			try {
				con = ConnessioneDB.getConnection();
				ps = con.prepareStatement(rimozioneMalattiaSQL);
				ps.setString(1, emailVF);
				ps.setDate(2, dataInizio);
				ps.setDate(3, dataFine);
				int righe=ps.executeUpdate();
				con.commit();
				if(righe>0)
					rimozione = true;
			}
			finally {
				ConnessioneDB.releaseConnection(con);

			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return rimozione;
	}
}