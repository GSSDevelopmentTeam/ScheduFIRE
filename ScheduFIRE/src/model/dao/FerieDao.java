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


public class FerieDao {

	public FerieDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<FerieBean> ottieniFerieConcesse(String email) {
		
		String emailVF, emailCT;
		Date dataInizio, dataFine;
		int idPeriodo;
		PreparedStatement ps;
		ResultSet rs;
		FerieBean ferie;
		
		List<FerieBean> periodi = new ArrayList<FerieBean>();
		
		String ferieSQL = "SELECT f.id, f.dataInizio, f.dataFine, f.emailCT, f.emailVF " +
							"FROM Ferie f WHERE f.emailVF = ? AND f.dataFine >= CURDATE();";
		
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
	
	public static boolean contieneGiorniConcessi(Date dataInizio, Date dataFine, String emailVF) {
		boolean verifica = false;
		int righe = 0;
		PreparedStatement ps;
		ResultSet rs;
		
		String verificaPeriodoSQL = "SELECT id FROM Ferie WHERE emailVF = ? dataInizio BETWEEN ? AND ? " + 
									"OR dataFine BETWEEN ? AND ?;";
		
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
}
