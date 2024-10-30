package de.atlasmc.util.sql;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLBiFunction<T, U, R> {
	
	R apply(Connection con, T t, U u) throws SQLException;

}
