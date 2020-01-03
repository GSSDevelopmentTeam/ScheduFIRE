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
 * persistenti relativi alla 'malattia'
 * @author Biagio Bruno
 * */

public class GiorniMalattiaDao {

	public GiorniMalattiaDao() {
		// TODO Auto-generated constructor stub
	}

	
	
	/**
     * Metodo per creare una connessione sul DB MySQL
     * @param malattia , � un oggetto di tipo GiorniMalattiaBean
     *  @return true se l'operazione va a buon fine, false altrimenti.
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
				Connection con = null;
		 
		 try{
			con = ConnessioneDB.getConnection();
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
		 }finally {
					ConnessioneDB.releaseConnection(con);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			return aggiunta;
		}
	  
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
					"FROM Malattia m WHERE m.emailVF = ? AND m.dataFine >= CURDATE();";

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
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			return periodiMalattia;
		}
}