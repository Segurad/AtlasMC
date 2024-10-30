package de.atlasmc.util.sql;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLFunction<T, R> {
	
	R apply(Connection con, T t) throws SQLException;

}
