package de.atlasmc.util.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import de.atlasmc.util.NumberConversion;

public class SQLConnectionPool {
	
	public static final long DEFAULT_FETCH_TIMOUT;
	
	static {
		DEFAULT_FETCH_TIMOUT = NumberConversion.toLong(System.getProperty("de.atlasmc.util.sql.defaultConnectionPoolFetchTimout"), 500);
	}
	
	private ConnectionPoolDataSource source;
	private BlockingQueue<PoolEntry> consAvailable;
	private Set<PoolEntry> consFetched;
	private int size;
	private final int minSize;
	private final int maxSize;
	private final int maxIdleTimeout;
	private final int maxIdleCount;
	private volatile boolean closed;
	
	public SQLConnectionPool(ConnectionPoolDataSource source, int minSize, int maxIdleCount, int maxSize, int maxIdleTimeout) {
		if (source == null)
			throw new IllegalArgumentException("Source can not be null!");
		if (minSize < 0)
			throw new IllegalArgumentException("Min size can not be lower than 0: " + minSize);
		this.source = source;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.maxIdleCount = maxIdleCount;
		this.maxIdleTimeout = maxIdleTimeout;
		this.consAvailable = new LinkedBlockingQueue<>();
		this.consFetched = ConcurrentHashMap.newKeySet();
	}
	
	public Connection getConnection(boolean block) throws SQLException, InterruptedException {
		return block ? getConnection(DEFAULT_FETCH_TIMOUT, TimeUnit.MILLISECONDS) : getConnection();
	}
	
	public synchronized Connection getConnection() throws SQLException {
		ensureOpen();
		PoolEntry con = null;
		final long maxIdleTime = System.currentTimeMillis() - maxIdleTimeout;
		do {
			con = consAvailable.poll();
			if (con == null) {
				if (maxSize == 0 || size < maxSize) { // can create new connections
					con = createConnection();
				} else {
					return null;
				}
			} else if (con.lastActive < maxIdleTime) {
				con.discard();
				con = null;
			}
		} while (con == null);
		consFetched.add(con);
		return con.getConnection();
	}
	
	public synchronized Connection getConnection(long timeout, TimeUnit unit) throws SQLException, InterruptedException {
		ensureOpen();
		PoolEntry con = null;
		final long maxIdleTime = System.currentTimeMillis() - maxIdleTimeout;
		do {
			con = consAvailable.poll();
			if (con == null) {
				if (maxSize == 0 || size < maxSize) { // can create new connections
					con = createConnection();
				} else if (timeout > 0) { // blocks until new connection is available
					con = consAvailable.poll(timeout, unit);
				} else {
					return null;
				}
			}
			if (con.lastActive < maxIdleTime) {
				con.discard();
				con = null;
			}
		} while (con == null);
		consFetched.add(con);
		return con.getConnection();
	}
	
	private synchronized PoolEntry createConnection() throws SQLException {
		PooledConnection con = source.getPooledConnection();
		size++;
		return new PoolEntry(this, con);
	}
	
	public void close() {
		if (closed)
			return;
		synchronized (this) {
			if (closed)
				return;
			closed = true;
			closeAll(consAvailable);
			consAvailable = null;
			closeAll(consFetched);
			consFetched = null;
		}
	}
	
	private void closeAll(Collection<PoolEntry> cons) {
		cons.forEach((con) -> {
			try {
				con.con.close();
			} catch (SQLException e) {}
		});
		cons.clear();
	}
	
	private void ensureOpen() throws SQLException {
		if (closed)
			throw new SQLException("Pool is closed!");
	}
	
	private static final class PoolEntry implements ConnectionEventListener {

		private final SQLConnectionPool pool;
		private final PooledConnection con;
		private volatile long lastActive;
		
		public PoolEntry(SQLConnectionPool pool, PooledConnection con) {
			this.pool = pool;
			this.con = con;
			lastActive = System.currentTimeMillis();
			con.addConnectionEventListener(this);
		}
		
		public Connection getConnection() throws SQLException {
			try {
				return this.con.getConnection();
			} catch (SQLException e) {
				discard();
				throw e;
			}
		}
		
		public void discard() {
			synchronized (pool) {
				pool.size--;
				pool.consFetched.remove(this);
			}
			con.removeConnectionEventListener(this);
			try {
				con.close();
			} catch (Exception e) {}
		}

		@Override
		public synchronized void connectionClosed(ConnectionEvent event) {
			if (pool.size <= pool.minSize || pool.consAvailable.size() < pool.maxIdleCount) {
				pool.consFetched.remove(this);
				pool.consAvailable.add(this);
				lastActive = System.currentTimeMillis();
			} else {
				discard();
			}
		}

		@Override
		public void connectionErrorOccurred(ConnectionEvent event) {
			try {
				con.close();
			} catch (SQLException e) {}
			discard();
		}
		
	}

}
