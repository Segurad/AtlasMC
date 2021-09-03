package de.atlasmc.util.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Segurad
 *
 */
abstract class AbstractSQL implements SQLConnection {

	protected String host;
	protected Connection connection;
	
	@Override
	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				System.out.println("[UnionCore] SQL<" + host + ">: Disconnect failed!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean isConnected() {
		try {
			return (connection == null ? false : !connection.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void update(String qry) {
		if (!isConnected()) {
			throw new Error("[UnionCore] SQL<" + host + ">: Is not connected!");
		}
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getResult(String qry) {
		if (!isConnected()) {
			throw new Error("[UnionCore] SQL<" + host + ">: Is not connected!");
		}
		try {
			Statement st = connection.createStatement();
			st.closeOnCompletion();
			return st.executeQuery(qry);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
