package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ConnessioneDB;
import model.bean.FerieBean;

/**
 * Classe che si occupa della gestione dei dati 
 * persistenti relativi all'entita' "Ferie"
 * @author Nicola Labanca
 * @author Alfredo Giuliano
 */

public class FerieDao {

	/**
	 * Si occupa del prelevamento dal dataBase, dei periodi di ferie concessi ad un VF.
	 * @param email (String): L'email del VF per il quale si ha bisogno di avere la lista di ferie concesse
	 * @return periodi (List): Lista dei periodi di ferie concessi al VF
	 */
	public static List<FerieBean> ottieniFerieConcesse(String email) {
		
		String emailVF, emailCT;
		Date dataInizio, dataFine;
		int idPeriodo;
		PreparedStatement ps;
		ResultSet rs;
		FerieBean ferie;
		
		List<FerieBean> periodi = new ArrayList<FerieBean>();
		
		String ferieSQL = "SELECT f.id, f.dataInizio, f.dataFine, f.emailCT, f.emailVF " +
							"FROM Ferie f WHERE f.emailVF = ? AND f.dataFine >= CURDATE()"
							+ "ORDER BY dataInizio;";
		
		try(Connection connessione = ConnessioneDB.getConnection()){
			
			emailVF = email;
			
			ps = connessione.prepareStatement(ferieSQL);
			ps.setString(1, emailVF);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ferie = new FerieBean();
				
				idPeriodo = rs.getInt("id");
				emailCT = rs.getString("emailCT");
				dataInizio = rs.getDate("dataInizio");
				dataFine = rs.getDate("dataFine");
				
				ferie.setId(idPeriodo);
				ferie.setEmailVF(emailVF);
				ferie.setEmailCT(emailCT);
				ferie.setDataInizio(dataInizio);
				ferie.setDataFine(dataFine);
				
				periodi.add(ferie);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return periodi;
	}
	
	/**
	 * Si occupa della rimozione dal dataBase, di un periodo o giorno di ferie concesso ad un VF.
	 * @param emailVF (String): L'email del VF per il quale si ha bisogno di rimuovere le ferie
	 * @param dataInizio (Date): Data di inizio delle ferie
	 * @param dataFine (Date): Data di fine dellle ferie
	 * @return rimozione: true se andata a buon fine, false altrimenti
	 */
	public static boolean rimuoviPeriodoFerie(String emailVF, Date dataInizio, Date dataFine) {
		
		boolean rimozione = false;
		PreparedStatement ps;
		
		String rimozioneFerieSQL = "DELETE FROM Ferie WHERE (emailVF = ? AND dataInizio = ? AND dataFine = ?);";
		
		try{
			Connection connessione=null;
			try {
			connessione = ConnessioneDB.getConnection();
			ps = connessione.prepareStatement(rimozioneFerieSQL);
			ps.setString(1, emailVF);
			ps.setDate(2, dataInizio);
			ps.setDate(3, dataFine);
			int righe=ps.executeUpdate();
			connessione.commit();
			if(righe>0)
				rimozione = true;
			}
			finally {
				ConnessioneDB.releaseConnection(connessione);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rimozione;
	}
	
	/**
	 * Si occupa dell'aggiunta nel dataBase, di un periodo o giorni di ferie concessi ad un VF.
	 * @param emailCT (String): L'email del CT che aggiunge le ferie
	 * @param emailVF (String): L'email del VF al quale si concedono le ferie
	 * @param dataInizio (Date): Data di inizio delle ferie
	 * @param dataFine (Date): Data di fine dellle ferie
	 * @return aggiunta: true se andata a buon fine, false altrimenti
	 */
	public static boolean aggiungiPeriodoFerie(String emailCT, String emailVF, Date dataInizio, Date dataFine) {
		
		boolean aggiunta = false;
		PreparedStatement ps;
		
		String aggiuntaFerieSQL = "INSERT INTO Ferie(dataInizio, dataFine, emailCT, emailVF) VALUES(?, ?, ?, ?);";
		
		try {
			Connection connessione = null;
			
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(aggiuntaFerieSQL);
				ps.setDate(1, dataInizio);
				ps.setDate(2, dataFine);
				ps.setString(3, emailCT);
				ps.setString(4, emailVF);
				
				int righe = ps.executeUpdate();
				connessione.commit();
				
				if(righe > 0)
					aggiunta = true;
				
			}finally {
				ConnessioneDB.releaseConnection(connessione);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return aggiunta;
	}
	
	/**
	 * Si occupa di controllare se il periodo di ferie da concedere, contiene giorni già concessi al VF.
	 * @param dataInizio (Date): Data di inizio delle ferie
	 * @param dataFine (Date): Data di fine dellle ferie
	 * @param emailVF (String): L'email del VF oggetto del controllo
	 * @return aggiunta: false se il periodo non contiene giorni già concessi precedentemente, true altrimenti
	 */
	public static boolean contieneGiorniConcessi(Date dataInizio, Date dataFine, String emailVF) {
		boolean verifica = false;
		int righe = 0;
		PreparedStatement ps;
		ResultSet rs;
		String verificaPeriodoSQL = "SELECT id FROM Ferie WHERE emailVF = ? AND (dataInizio BETWEEN ? AND ? " + 
									"OR dataFine BETWEEN ? AND ?);";
		
		try {
			Connection connessione = null;
			
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(verificaPeriodoSQL);
				ps.setString(1, emailVF);
				ps.setDate(2, dataInizio);
				ps.setDate(3, dataFine);
				ps.setDate(4, dataInizio);
				ps.setDate(5, dataFine);
				
				rs = ps.executeQuery();
				
				while(rs.next()) {
					righe = rs.getInt("id");
				}
				
				if(righe > 0)
					verifica = true;
				
			}finally {
				ConnessioneDB.releaseConnection(connessione);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return verifica;
	}
	
	/**
	 * Si occupa di controllare se il periodo di ferie da concedere, contiene giorni già concessi al VF.
	 * @param dataInizio (Date): Data di inizio delle ferie
	 * @param dataFine (Date): Data di fine dellle ferie
	 * @param emailVF (String): L'email del VF oggetto del controllo
	 * @return ferie (List): Lista di periodi in cui sia incluso almeno un giorno compreso nel periodo che va
	 * da dataInizio a dataFine del vigile con emailVF
	 */
	public static List<FerieBean> ferieInRange(Date dataInizio, Date dataFine, String emailVF) {
		PreparedStatement ps;
		ResultSet rs;
		String verificaPeriodoSQL = "SELECT * FROM Ferie WHERE "
				+ "emailVF = ? AND ((dataInizio BETWEEN ? AND ? OR dataFine BETWEEN ? AND ?) "
				+ "OR (dataInizio < ? AND dataFine > ?) OR (dataInizio < ? AND dataFine > ?)) ORDER BY dataInizio;";
		
		List<FerieBean> ferie=new ArrayList<>();

		try {
			Connection connessione = null;
			
			try {
				connessione = ConnessioneDB.getConnection();
				
				ps = connessione.prepareStatement(verificaPeriodoSQL);
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
					FerieBean f = new FerieBean();
					f.setId(rs.getInt("id"));
					f.setEmailVF(rs.getString("emailVF"));
					f.setEmailCT(rs.getString("emailCT"));
					f.setDataInizio(rs.getDate("dataInizio"));
					f.setDataFine(rs.getDate("dataFine"));
					ferie.add(f);
				}
				
				
			}finally {
				ConnessioneDB.releaseConnection(connessione);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return ferie;
	}
}
