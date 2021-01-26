package de.atlasmc.util.sql;

import java.sql.ResultSet;

public interface SQLConnection {
	
	public boolean isConnected();

	public void connect();

	public void disconnect();
	
	public ResultSet perform(SQLCommand cmd);
	
	public void performAsync(SQLCommand cmd);
	
	public void performAsync(SQLCommand cmd, AsyncSQLResultHandler handler);
	
	public SQLConnection createNewInstance();
}
