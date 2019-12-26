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
import model.bean.VigileDelFuocoBean;

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

}
