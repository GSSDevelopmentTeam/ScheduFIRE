package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnessioneDB_Ciro  {

	private static List<Connection> freeDbConnections;

	static {
		freeDbConnections = new LinkedList<Connection>();
	}
	
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String ip = "127.0.0.1";
		String port = "3306";
		String db = "studyme";
		String username = "root";
		String password = "ciruzzo123";

		newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+ "/"+ db + "?useSSL=false", username, password);
		newConnection.setAutoCommit(false);
		return newConnection;
	}


	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;
		
		//Se non è vuota prendiamo la connessione al primo posto e la assegnamo come connessione
		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}

	//Questo metodo quando non serve più una connessione la rinserisce nella lista
	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if(connection != null) freeDbConnections.add(connection);
	}
}