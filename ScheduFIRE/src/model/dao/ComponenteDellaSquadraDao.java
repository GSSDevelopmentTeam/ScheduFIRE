package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.ConnessioneDB;
import model.bean.ComponenteDellaSquadraBean;
import model.bean.VigileDelFuocoBean;

/**
 * 
 * @author Alfredo Giuliano
 *
 */
public class ComponenteDellaSquadraDao {
	/**
	 * @param data , la data del giorno di cui si vuole avere la lista dei vigili presenti nelle varie squadre
	 * @return una lista di ComponenteDellaSquadraBean presenti nelle varie squadre al giorno passato come parametro, 
	 * 			ordinati in base alla squadra assegnata e al cognome del vigile
	 */
	public static ArrayList<ComponenteDellaSquadraBean> getComponenti(Date data){
		try(Connection con = ConnessioneDB.getConnection()) {

			// Query di ricerca
			PreparedStatement ps = con.prepareStatement("SELECT  cds.emailVF, cds.tipologia, cds.giornoLavorativo " + 
					" FROM Vigile v " + 
					" JOIN ComponenteDellaSquadra cds ON v.email=cds.emailVF " + 
					" WHERE cds.giornolavorativo=?;");
			ps.setDate(1, data);
			ResultSet rs = ps.executeQuery();
			ArrayList<ComponenteDellaSquadraBean> componenti=new ArrayList<>();
			
			//Iterazione dei risultati
			while(rs.next()) {
				String email = rs.getString("emailVF");
				String tipologia = rs.getString("tipologia");
				Date giornoLavorativo = rs.getDate("giornoLavorativo");

				ComponenteDellaSquadraBean cds = new ComponenteDellaSquadraBean();
				cds.setEmailVF(email);
				cds.setTipologiaSquadra(tipologia);
				cds.setGiornoLavorativo(giornoLavorativo);

				componenti.add(cds);
			}
			return componenti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean setComponenti(List<ComponenteDellaSquadraBean> componenti) {
		try(Connection con = ConnessioneDB.getConnection()) {
			int count = 0;
			for(ComponenteDellaSquadraBean comp : componenti) {
				count += aggiungiAlDb(comp, con);
			}
			return (count == componenti.size());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static int aggiungiAlDb(ComponenteDellaSquadraBean comp, Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement("INSERT INTO componentedellasquadra(emailVF, tipologia, giornoLavorativo) "
				+ "values (?, ?, ?);");
		ps.setString(1, comp.getTipologiaSquadra());
		ps.setString(2, comp.getEmailVF());
		ps.setDate(3, comp.getGiornoLavorativo());
		
		
		return ps.executeUpdate();

	}
	

}
