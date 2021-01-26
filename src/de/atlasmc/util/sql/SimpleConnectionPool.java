package de.atlasmc.util.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleConnectionPool {

	private Timer t;
	private final List<SQLConnection> unused;
	private final List<SQLConnection> used;
	
	private final SQLConnection connection;
	private final long removedelay;
	
	public SimpleConnectionPool(SQLConnection connection, long removedelay) {
		this.connection = connection.createNewInstance();
		this.removedelay = removedelay;
		unused = new ArrayList<SQLConnection>();
		used = new ArrayList<SQLConnection>();
	}
	
	public SQLConnection fetch() {
		SQLConnection con = null;
		if (unused.isEmpty()) {
			con = connection.createNewInstance();
			con.connect();
		} else {
			con = unused.remove(0);
			if (!con.isConnected()) con.connect();
			if (unused.isEmpty() && t != null) { t.cancel(); t = null; }
		}
		used.add(con);
		return con;
	}
	
	public boolean restore(SQLConnection connection) {
		if (!used.contains(connection)) return false;
		used.remove(connection);
		unused.add(0, connection);
		if (t == null) clearClock();
		return true;
	}
	
	private void clearClock() {
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				SQLConnection con = unused.remove(unused.size() - 1);
				con.disconnect();
				if (unused.isEmpty()) { t.cancel(); t = null; };
			}
		}, removedelay, removedelay);
	}
	
	public void closeAll() {
		if (t!= null) t.cancel(); t = null;
		unused.forEach(con -> con.disconnect());
		used.forEach(con -> con.disconnect());
		unused.clear();
		used.clear();
	}
}
