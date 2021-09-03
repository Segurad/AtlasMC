package de.atlasmc.util.sql;

import java.sql.ResultSet;

public interface SQLConnection {
	
	public boolean isConnected();

	public void connect();

	public void disconnect();
	
	public void update(String qry);
	
	public ResultSet getResult(String qry);
	
	public SQLConnection createNewInstance();
}
