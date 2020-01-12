package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import model.ConnessioneDB;
import model.bean.CredenzialiBean;

public class CredenzialiDao {

 public CredenzialiDao() {}


 public CredenzialiBean login(String username) {
  CredenzialiBean credenziali = null;
  try {
   
    Connection conn = ConnessioneDB.getConnection();
    PreparedStatement stm = conn.prepareStatement("SELECT * FROM credenziali WHERE username=?");
    stm.setString(1, username);
    ResultSet res = stm.executeQuery();


    credenziali = new CredenzialiBean();

    //se l'utente esiste
    if(res.next()) {
     credenziali.setUsername(res.getString(1));
     credenziali.setPassword(res.getString(2));
     credenziali.setRuolo(res.getString(3));
    }
    else {
     return null;

   }

  } catch (SQLException e) {

   e.printStackTrace();
  }



  return credenziali;

 }
}

