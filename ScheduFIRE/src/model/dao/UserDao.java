package model.dao;





import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import model.ConnessioneDB_Ciro;
import model.bean.CredenzialiBean;

public class UserDao {

	public UserDao() {}


	public CredenzialiBean login(String username, String password, String ruolo) {
		CredenzialiBean credenziali = null;
		try {
			if(ruolo.equalsIgnoreCase("capoturno"))
			{



				Connection conn = ConnessioneDB_Ciro.getConnection();
				PreparedStatement stm = conn.prepareStatement("SELECT * FROM credenziali WHERE username=? AND password=? AND ruolo='capoturno'");

				stm.setString(1, username);
				stm.setString(2, password);
				ResultSet res = stm.executeQuery();


				credenziali = new CredenzialiBean();

				//se il capoturno esiste
				if(res.next()) {
					credenziali.setUsername(res.getString(1));
					credenziali.setPassword(res.getString(2));
				}
				else
					return null;

			}
			else {
				Connection conn = ConnessioneDB_Ciro.getConnection();
				PreparedStatement stm = conn.prepareStatement("SELECT * FROM credenziali WHERE username=? AND password=? AND ruolo='vigiledelfuoco'");
				stm.setString(1, username);
				stm.setString(2, password);
				ResultSet res = stm.executeQuery();


				credenziali= new CredenzialiBean();

				//se esiste il vigile del fuoco
				if(res.next()) {
					credenziali.setUsername(res.getString(1));
					credenziali.setPassword(res.getString(2));
				}
				else {
					return null;
				}

			}



		} catch (SQLException e) {

			e.printStackTrace();
		}



		return credenziali;


	}

}








