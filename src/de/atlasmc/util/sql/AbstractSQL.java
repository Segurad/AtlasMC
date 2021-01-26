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

	private void update(String qry) {
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ResultSet getResult(String qry) {
		try {
			Statement st = connection.createStatement();
			return st.executeQuery(qry);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet perform(SQLCommand cmd) {
		if (!isConnected()) {
			throw new Error("[UnionCore] SQL<" + host + ">: Is not connected!");
		}
		if (cmd.hasResult()) {
			return getResult(cmd.getCommand());
		} else update(cmd.getCommand());
		return null;
	}
	
    public void performAsync(SQLCommand cmd) {
    	performAsync(cmd, null);
    }
	
	public void performAsync(SQLCommand cmd, final AsyncSQLResultHandler handler) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				ResultSet r = perform(cmd);
				if (r != null && handler != null) {
					handler.result(r);
				}
			}
		});
		t.start();
	}
}
