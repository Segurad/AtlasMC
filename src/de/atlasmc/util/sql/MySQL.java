package de.atlasmc.util.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySQL extends AbstractSQL {

	private final String port;
	private final String database;
	private final String username;
	private final String password;
	
	public MySQL(String host, String port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	@Override
	public void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username,password);
			} catch (SQLException e) {
				System.out.println("[UnionCore] SQL<" + host + ">: Connection failed!");
				e.printStackTrace();
			}
		} else System.out.println("[UnionCore] SQL<" + host + ">: Already connected!");
	}

	@Override
	public SQLConnection createNewInstance() {
		return new MySQL(host, port, database, username, password);
	}

}
