package de.atlasmc.util.sql;

public class SQLCommandBuilder {
	// https://www.php-einfach.de/mysql-tutorial/uebersicht-sql-befehle/
	// https://www.w3schools.com/sql/sql_update.asp
	// https://www.w3schools.com/sql/sql_datatypes.asp data types
	
	private SQLCommandBuilder() {}
	
	/**
	 * 
	 * @param table the table you update
	 * @param field the attribute you update
	 * @param value the attribute's new value
	 * @param where the condition attribute
	 * @param is the value the condition attribute need to have
	 * @return a new SQLCommand
	 */
	public static String updateWhere(String table, String field, String value, String where, String is) {
		StringBuilder cmd = new StringBuilder("UPDATE ");
		cmd.append(table);
		cmd.append(" SET ");
		cmd.append(field);
		cmd.append("='");
		cmd.append(value);
		cmd.append("' WHERE ");
		cmd.append(where);
		cmd.append("='");
		cmd.append(is);
		cmd.append("';");
		return cmd.toString();
		//UPDATE <table> SET <field>= '<value>' WHERE <where>= '<whereis>';
	}
	/**
	 * 
	 * @param table the table you update
	 * @param field the attributes you update
	 * @param value the attributes new values
	 * @param where the condition attribute
	 * @param is the value the condition attribute need to have
	 * @return a new SQLCommand
	 */
	public static String updateWhere(String table, String[] field, String[] value, String where, String is) {
		StringBuilder cmd = new StringBuilder("UPDATE ");
		cmd.append(table);
		cmd.append(" SET ");
		for (int i = 0; i < field.length; i++) {
			cmd.append(field[i]).append("='").append(value[i]).append("'");
			if (i+1 < field.length) cmd.append(", ");
		}
		cmd.append(" WHERE ");
		cmd.append(where);
		cmd.append("='");
		cmd.append(is);
		cmd.append("';");
		return cmd.toString();
	}
	
	/**
	 * 
	 * @param table the table you create
	 * @param field the attributes the table should have
	 * @param datatype the attribute data types
	 * @return a new SQLCommand
	 */
	public static String createTableIfNotExist(String table, String[] field, String[] datatype) {
		StringBuilder cmd = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		cmd.append(table);
		cmd.append("(");
		for (int i = 0; i < field.length; i++) {
			cmd.append(field[i]).append(" ").append(datatype[i]);
			if (i+1 < field.length) cmd.append(", ");
		}
		cmd.append(");");
		return cmd.toString();
	}
	
	/**
	 * 
	 * @param table the table you insert
	 * @param field the fields you want to set
	 * @param value the fields' values
	 * @return a new SQLCommand
	 */
	public static String insert(String table, String[] field, String[] value) {
		StringBuilder cmd = new StringBuilder("INSERT INTO ");
		cmd.append(table);
		if (field != null) {
			cmd.append(" (");
			for (int i = 0; i < field.length; i++) {
				cmd.append(field[i]);
				if (i+1 < field.length) cmd.append(", ");
			}
			cmd.append(")");
		}
		cmd.append(" VALUES (");
		for (int i = 0; i < value.length; i++) {
			cmd.append("'").append(value[i]).append("'");
			if (i+1 < value.length) cmd.append(", ");
		}
		cmd.append(");");
		return cmd.toString();
	}
	
	/**
	 * 
	 * @param table the table you insert
	 * @param value the fields' values
	 * @return a new SQLCommand
	 */
	public static String insert(String table, String[] value) {
		return insert(table, null, value);
	}
	
	public static String selectWhere(String table, String where, String is) {
		return selectWhere(table, "*", where, is);
	}
	
	public static String selectWhere(String table, String[] field, String where, String is) {
		StringBuilder cmd = new StringBuilder("SELECT ");
		for (int i = 0; i < field.length; i++) {
			cmd.append(field[i]);
			if (i+1 < field.length) cmd.append(", ");
		}
		cmd.append(" FROM ");
		cmd.append(table);
		cmd.append(" WHERE ");
		cmd.append(where);
		cmd.append("='");
		cmd.append(is);
		cmd.append("';");
		return cmd.toString();
	}
	
	public static String selectWhere(String table, String field, String where, String is) {
		StringBuilder cmd = new StringBuilder("SELECT ");
		cmd.append(field);
		cmd.append(" FROM ");
		cmd.append(table);
		cmd.append(" WHERE ");
		cmd.append(where);
		cmd.append("='");
		cmd.append(is);
		cmd.append("';");
		return cmd.toString();
	}
	
	public static String deleteWhere(String table, String where, String is) {
		StringBuilder cmd = new StringBuilder("DELETE FROM ");
		cmd.append(table);
		cmd.append(" WHERE ");
		cmd.append(where);
		cmd.append("='");
		cmd.append(is);
		cmd.append("';");
		return cmd.toString();
	}
	
	public static String selectWhere(String table, String field, String where, int is) {
		StringBuilder cmd = new StringBuilder("SELECT ");
		cmd.append(field);
		cmd.append(" FROM ");
		cmd.append(table);
		cmd.append(" WHERE ");
		cmd.append(where);
		cmd.append("=");
		cmd.append(is);
		cmd.append(";");
		return cmd.toString();
	}
	
	public static String deleteWhere(String table, String where, int is) {
		StringBuilder cmd = new StringBuilder("DELETE FROM ");
		cmd.append(table);
		cmd.append(" WHERE ");
		cmd.append(where);
		cmd.append("=");
		cmd.append(is);
		cmd.append(";");
		return cmd.toString();
	}
	
	public static String deleteContents(String table) {
		return "DELETE FROM " + table + ";";
	}
	
}
