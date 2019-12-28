package model.dao;

public class FerieDao {

	public FerieDao() {
		// TODO Auto-generated constructor stub
	}
<<<<<<< HEAD
=======
	
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
		ResultSet rs;
		
		String rimozioneFerieSQL = "DELETE FROM Ferie WHERE (emailVF = ? AND dataInizio = ? AND dataFine = ?);";
		
		try(Connection connessione = ConnessioneDB.getConnection()){
			
			ps = connessione.prepareStatement(rimozioneFerieSQL);
			ps.setString(1, emailVF);
			ps.setDate(2, dataInizio);
			ps.setDate(3, dataFine);
			
			if(ps.execute())
				rimozione = true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rimozione;
	}
>>>>>>> parent of 629e0dd... Merge pull request #169 from GSSDevelopmentTeam/alfredo

}
