package controller;

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
		  
		  System.out.println(this.nomeDatabase);

		  try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
	      
			  String url = "jdbc:mysql://" + this.nomePortaHost + ":" + this.portaHost + "/" 
			  + this.nomeDatabase;
			  
			  this.connessione = DriverManager.getConnection(url, this.userName, this.password);
			  this.connessione.setAutoCommit(false);
			  
		  } catch (Exception e) {
			  System.out.println(e.getMessage());
		  }
		  
	  }

	  /**
	   * Get dell'istanza del database.
	   */
	  public static DbConnection getIstanza() {
	    if (istanza == null) {
	    	istanza = new DbConnection();
	    }
	    return istanza;
	  }

	  /**
	   * Ritorna l'oggetto di tipo Connection.
	   */
	  public Connection getConnessione() {
	    return this.connessione;
	  }

	  /**
	   * Setta la connessione con il database
	   * 
	   * @param connessione è la variabile che contiene l'oggetto che consente
	   * 		di connettere il database al codice.
	   */
	  public void setConn(Connection connessione) {
	    this.connessione = connessione;
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
				  this.nomePortaHost = temporanea.substring(indice++);
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.portaHost = Integer.valueOf(temporanea.substring(indice++));
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.nomeDatabase = temporanea.substring(indice++);
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.userName = temporanea.substring(indice++);
				  
				  temporanea = buffer.readLine();
				  indice = temporanea.indexOf(':');
				  this.password = temporanea.substring(indice++);
				  
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
	    
	  public String getNomeDatabase() {
		  return nomeDatabase;
	  }

	  public String getUserName() {
		  return userName;
	  }

	  public String getPassword() {
		  return password;
	  }

	  public int getPortaHost() {
		  return portaHost;
	  }

	  public String getNomePortaHost() {
		  return nomePortaHost;
	  }

	  //Variabili d'istanza
	  private static DbConnection istanza = null;
	  private Connection connessione;
	  private String nomeDatabase;
	  private String userName;
	  private String password;
	  private int portaHost;
	  private String nomePortaHost;
}

