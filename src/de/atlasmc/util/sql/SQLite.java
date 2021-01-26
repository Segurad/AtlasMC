package de.atlasmc.util.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Segurad
 *
 */
public class SQLite extends AbstractSQL {
	
	private String file;
	
	public SQLite(String path, String file) {
		this.host = !path.endsWith("/") ? path : path.substring(0, path.length()-2);
		this.file = file;
	}

	@Override
	public void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:" + host + "/" + file);
			} catch (SQLException e) {
				System.out.println("[UnionCore] SQL<" + host + ">: Connection failed!");
				e.printStackTrace();
			}
		} else
			System.out.println("[UnionCore] SQL<" + host + ">: Already connected!");
	}

	@Override
	public SQLConnection createNewInstance() {
		return new SQLite(host, file);
	}

}
