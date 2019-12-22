package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import model.ConnessioneDB;
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

	/** La query per l'inserimento di un periodo di malattia */
	
	private static final String selezionaIdVfDiMalattia_QUERY = "SELECT * FROM malattia WHERE id = ?;";
	private static final String addPeriodoDiMalattia_QUERY = "INSERT INTO malattia (dataInizio, dataFine, emailCT, emailVF) "
	                                                        +"values(?, ?, ?, ?);";
	
	/**
     * Metodo per creare una connessione sul DB MySQL
     * @param malattia , è un oggetto di tipo GiorniMalattiaBean
     *  @return true se l'operazione va a buon fine, false altrimenti.
     */
	 public static boolean addMalattia(GiorniMalattiaBean malattia) {
		 Connection con = null;
		 PreparedStatement ps = null;
		 ResultSet res = null;
		 
		 //controllo
		 if(malattia == null) {
			 return false;
		 }
		 
		 try{
			// Esecuzione query
			    con = ConnessioneDB.getConnection();
				ps = con.prepareStatement(selezionaIdVfDiMalattia_QUERY);
				
				ps.setDate(1, malattia.getDataInizio());
				ps.setDate(2, malattia.getDataFine());
				ps.setString(3, malattia.getEmailCT());
				ps.setString(4, malattia.getEmailVF());
			    res = ps.getResultSet();
			    
			    return (ps.executeUpdate() == 1);
			    } 

		 catch (SQLException e) {
				throw new RuntimeException(e);
				}
		 
		 finally {
	            try {
	                res.close();
	            } catch (Exception rse) {
	                rse.printStackTrace();
	            }
	            try {
	                ps.close();
	            } catch (Exception sse) {
	                sse.printStackTrace();
	            }
	            try {
	                con.close();
	            } catch (Exception cse) {
	                cse.printStackTrace();
	            }
	        }
	 }
}
