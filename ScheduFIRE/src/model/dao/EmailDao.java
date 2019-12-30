package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ConnessioneDB;
import model.bean.EmailBean;



public class EmailDao {

	public EmailDao() {}
	
	public ArrayList<String> getEmail() {
		
		 
		ArrayList<String> allEmail = new ArrayList<String>();
		
	try {
		
	    Connection conn = ConnessioneDB.getConnection();
	    PreparedStatement stm = conn.prepareStatement("SELECT  email FROM dati");
	    ResultSet res = stm.executeQuery();
	   
	    EmailBean email = new EmailBean();
	    
	    	while(res.next()) {
	    	
	    		allEmail.add(email.setEmail(res.getString(1)));
	    		
	    		
	    		
	    	}
	  
}catch (SQLException e) {

	   e.printStackTrace();
	  }

	return allEmail;
}
}

