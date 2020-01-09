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
	
	public static String getSquadra(String mailVF, Date data) {
		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT tipologia FROM componentedellasquadra"
					+ " WHERE emailVF = ? AND giornoLavorativo = ?;");
			ps.setString(1, mailVF);
			ps.setDate(2, data);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			return rs.getString("tipologia");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
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
			Collections.sort(componenti, new ComponenteComparator());;
			return componenti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean removeComponenti(List<ComponenteDellaSquadraBean> componenti) {
		try(Connection con = ConnessioneDB.getConnection()) {
			for(ComponenteDellaSquadraBean comp : componenti) {
				rimuoviDalDb(comp, con);
				con.commit();
			}
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	private static int rimuoviDalDb(ComponenteDellaSquadraBean comp, Connection con) throws SQLException {
		System.out.println("Componente della squadra: "+comp.getEmailVF()+" , "+comp.getTipologiaSquadra()+" , "+comp.getGiornoLavorativo());
		PreparedStatement ps = con.prepareStatement("DELETE FROM ComponenteDellaSquadra WHERE emailVF = ? AND tipologia = ? AND giornoLavorativo = ? ;");
		ps.setString(1, comp.getEmailVF());
		ps.setString(2, comp.getTipologiaSquadra());
		ps.setDate(3, comp.getGiornoLavorativo());
		int righe=ps.executeUpdate();
		System.out.println("righe modificate: "+righe);
		return righe;

	}

	public static boolean setComponenti(List<ComponenteDellaSquadraBean> componenti) {
		try(Connection con = ConnessioneDB.getConnection()) {
			int count = 0;
			for(ComponenteDellaSquadraBean comp : componenti) {
				count += aggiungiAlDb(comp, con);
				con.commit();
			}
			System.out.println("Conto set componente "+count);
			return (count == componenti.size());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



	private static int aggiungiAlDb(ComponenteDellaSquadraBean comp, Connection con) throws SQLException {
		System.out.println("Componente della squadra: "+comp.getEmailVF()+" , "+comp.getTipologiaSquadra()+" , "+comp.getGiornoLavorativo());
		PreparedStatement ps = con.prepareStatement("insert into ComponenteDellaSquadra(emailVF, tipologia, giornoLavorativo) "
				+ "values (?, ?, ?);");
		ps.setString(1, comp.getEmailVF());
		ps.setString(2, comp.getTipologiaSquadra());
		ps.setDate(3, comp.getGiornoLavorativo());

		return ps.executeUpdate();

	}

	public static boolean isComponente(String emailVF, Date giornoLavorativo) {

		PreparedStatement ps;
		boolean schedulato = false;

		String componenteSQL = "SELECT * FROM ComponenteDellaSquadra WHERE emailVF = ? AND giornoLavorativo = ?;";

		try(Connection connessione = ConnessioneDB.getConnection()){

			ps = connessione.prepareStatement(componenteSQL);
			ps.setString(1, emailVF);
			ps.setDate(2, giornoLavorativo);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
				schedulato = true;

		}catch(SQLException e) {
			e.printStackTrace();
		}

		return schedulato;
	}

	public static List<ComponenteDellaSquadraBean> getSquadreRelative(Date from, Date to, VigileDelFuocoBean vigile) {
		String mailVF = vigile.getEmail();
		String sql = "SELECT * "
				+ "FROM componentedellasquadra "
				+ "WHERE emailVF = ? "
				+ "AND (giornoLavorativo BETWEEN ? AND ?);";
		try(Connection con = ConnessioneDB.getConnection()) {
			List<ComponenteDellaSquadraBean> toReturn = new ArrayList<>();

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, mailVF);
			ps.setDate(2, from);
			ps.setDate(3, to);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String tipologiaSquadra = rs.getString("tipologia");
				String emailVF = rs.getString("emailVF");
				Date data = rs.getDate("giornoLavorativo");
				toReturn.add(new ComponenteDellaSquadraBean(tipologiaSquadra, emailVF, data));
			}
			return toReturn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	/**
	 * Serve a cancellare tutti i componentiDellaSquadra precedenti a questa data
	 * @param data la data di partenza
	 */
	public static void rimuoviTutti(Date data) {
		String sql = "DELETE FROM ComponenteDellaSquadra WHERE giornoLavorativo < ? ;";
		
		try(Connection con = ConnessioneDB.getConnection()) {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, data);
			ps.executeUpdate();
			con.commit();
			return;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}










	/*
	 * Per ordinare l'array di componenti della squadra in base alla tipologia della squadra di appartenenza
	 * con priorit� a sala operativa, poi prima partenza, poi auto scala e infine auto botte.
	 * In caso di tipologia uguale, ordina in base al cognome che ricava dalla mail
	 * essendo la mail composta sempre da nome<numero>.cognome
	 * 
	 */
	static class ComponenteComparator implements Comparator<ComponenteDellaSquadraBean> {
		@Override
		public int compare(ComponenteDellaSquadraBean o1, ComponenteDellaSquadraBean o2) {
			String tipologia1=o1.getTipologiaSquadra();
			String tipologia2=o2.getTipologiaSquadra();
			int comparazione=tipologia1.compareTo(tipologia2);
			if (comparazione==0) {
				String cognome1=o1.getEmailVF().substring(o1.getEmailVF().indexOf(".")+1);
				String cognome2=o2.getEmailVF().substring(o2.getEmailVF().indexOf(".")+1);
				comparazione=cognome1.compareTo(cognome2);
				return comparazione;
			}
			return -comparazione;
		}
	}






}
