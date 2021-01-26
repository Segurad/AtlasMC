package de.atlasmc.util.sql;

import java.sql.ResultSet;

public interface AsyncSQLResultHandler {

	public void result(ResultSet result);
}
