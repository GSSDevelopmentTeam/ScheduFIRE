package model.bean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class DbConnection {

	  /**
	   * Costruttore.
	   */
	  public DbConnection() {
		  this.setCredenziali();

		  try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
	      
			  String url = "jdbc:mysql://" + this.nomePortaHost + ":" + this.portaHost + "/" 
			  + this.nomeDatabase;
			  
			  connessione = DriverManager.getConnection(url, this.userName, this.password);
			  connessione.setAutoCommit(false);
			  
		  } catch (Exception e) {
			  System.out.println(e.getMessage());
		  }  
	  }

	  /**
	   * Get dell'istanza del database.
	   */
	  public static Connection getIstanza() {
		  Connection con;
		  
		  if (istanza == null) {
	    	istanza = new DbConnection();
		  }
		  
		  con = getConnessione();
		  
		  return con;
	  }

	  /**
	   * Ritorna l'oggetto di tipo Connection.
	   */
	  private static Connection getConnessione() {
		  Connection con = connessione;
		  
		  return con;
	  }
	  
	  /**
	   * Setta le variabili di istanza mediante la lettura del file contenente
	   * le credenziali necessarie alla connessione con il database.
	   */
	  private void setCredenziali() {
		  FileReader file = null;
		  BufferedReader buffer;
		  String temporanea;
		  
		  try {
			file = new FileReader("CredenzialiDb.txt");
		  } catch (FileNotFoundException e) {
			e.printStackTrace();
		  }
		  
		  buffer = new BufferedReader(file);
		  
		  if(buffer!=null) {
			  try {
				  temporanea = buffer.readLine();
				  int indice = temporanea.indexOf(':');
				  this.nomePortaHost = temporanea.substring(indice+2);
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.portaHost = Integer.valueOf(temporanea.substring(indice+2));
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.nomeDatabase = temporanea.substring(indice+2);
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.userName = temporanea.substring(indice+2);
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.password = temporanea.substring(indice+2);
				  
			  }catch(Exception e) {
			  	e.printStackTrace();
			  }
		  }
		  
		  try {
			buffer.close();
			file.close();
			
		  }catch (Exception e) {
			e.printStackTrace();
		  }
	  }

	  //Variabili d'istanza
	  private static DbConnection istanza = null;
	  private static Connection connessione;
	  private String nomeDatabase;
	  private String userName;
	  private String password;
	  private int portaHost;
	  private String nomePortaHost;
}