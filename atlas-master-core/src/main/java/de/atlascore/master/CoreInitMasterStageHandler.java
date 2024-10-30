package de.atlascore.master;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import de.atlascore.master.node.CoreNodeManager;
import de.atlascore.master.permission.CoreSQLPermissionManager;
import de.atlascore.master.proxy.CoreProxyManager;
import de.atlascore.master.server.CoreServerManager;
import de.atlasmc.Atlas;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.master.AtlasMasterBuilder;
import de.atlasmc.plugin.StartupContext;
import de.atlasmc.plugin.StartupStageHandler;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.sql.SQLConnectionPool;

class CoreInitMasterStageHandler implements StartupStageHandler {

	private static final int MIN_SCHEMA_VERSION = 1;
	private static final int CURRENT_SCHEMA_VERSION = 1;
	private static final int MAX_SCHEMA_VERSION = 1000;
	
	private Log log;
	private String dbName;
	private SQLConnectionPool con;
	
	@Override
	public void handleStage(StartupContext context) {
		log = Logging.getLogger("Atlas-Master", "Atlas-Master");
		
		YamlConfiguration nodeCfg = null;
		try {
			nodeCfg = YamlConfiguration.loadConfiguration(new File(Atlas.getWorkdir(), "node.yml"));
		} catch (IOException e) {
			context.fail(e);
		}
		ConfigurationSection masterConfig = nodeCfg.getConfigurationSection("master");
		ConfigurationSection dbConfig = masterConfig.getConfigurationSection("database");
		SQLConnectionPool con = getDBConnection(dbConfig);
		if (con == null) {
			log.error("Unable to initialize database connection is not present!");
			context.fail();
		}
		if (!prepareDB()) {
			log.error("A problem occured while preparing the database!");
			context.fail();
		}
		
		File nodeIDFile = new File(Atlas.getWorkdir(), "node-id.yml");
		UUID nodeID = null;
		if (nodeIDFile.exists()) {
			YamlConfiguration nodeIDCfg = null;
			try {
				nodeIDCfg = YamlConfiguration.loadConfiguration(nodeIDFile);
			} catch (IOException e) {
				log.error("Error while loading node id file!", e);
				context.fail(e);
			}
			String rawID = nodeIDCfg.getString("node-id");
			if (rawID != null)
				nodeID = UUID.fromString(rawID);
		}
		if (nodeID == null) {
			nodeID = UUID.randomUUID();
			YamlConfiguration nodeIDCfg = new YamlConfiguration();
			nodeIDCfg.set("node-id", nodeID.toString());
			try {
				nodeIDCfg.save(nodeIDFile);
			} catch (IOException e) {
				log.error("Error while writing node id file!", e);
				context.fail(e);
			}
		}
		
		AtlasMasterBuilder builder = context.getContext("builder");
		builder.setLogger(log)
			.setDatabase(con)
			.setNodeManager(new CoreNodeManager())
			.setProxyManager(new CoreProxyManager())
			.setServerManager(new CoreServerManager())
			.setProfileManager(new CoreSQLProfileManager())
			.setPermissionManager(new CoreSQLPermissionManager());
	}
	
	private SQLConnectionPool getDBConnection(ConfigurationSection dbConfig) {
		dbName = dbConfig.getString("database");
		String dbHost = dbConfig.getString("host");
		int dbPort = dbConfig.getInt("port");
		String dbUser = dbConfig.getString("user");
		String dbPassword = dbConfig.getString("password");
		int poolMinSize = dbConfig.getInt("pool-min-size");
		int poolMaxSize = dbConfig.getInt("pool-max-size");
		int poolMaxIdleCount = dbConfig.getInt("pool-max-idle-count", 1);
		int poolIdleTimeout = dbConfig.getInt("pool-idle-timeout");
		MysqlConnectionPoolDataSource src = new MysqlConnectionPoolDataSource();
		try {
			log.info("Connecting to database...");
			src.setDatabaseName(dbName);
			src.setPort(dbPort);
			src.setServerName(dbHost);
			src.setUser(dbUser);
			src.setPassword(dbPassword);
			src.getConnection().close();
		} catch (SQLException e) {
			log.error("Unable to establish database connection!", e);
			return null;
		}
		log.info("Database connection established");
		return new SQLConnectionPool(src, poolMinSize, poolMaxIdleCount, poolMaxSize, poolIdleTimeout);
	}
	
	private boolean prepareDB() {
		Connection con = null;
		boolean prepared = false;
		try {
			con = this.con.getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME = 'atlas';");
			stmt.setString(1, dbName);
			ResultSet result = stmt.executeQuery();
			boolean schemaPresent = result.next();
			stmt.close();
			result.close();
			if (schemaPresent) {
				stmt = con.prepareStatement("SELECT schema_version FROM atlas");
				stmt.setFetchSize(1);
				result = stmt.executeQuery();
				if (result.next()) {
					int schemaVersion = result.getInt(1);
					if (schemaVersion >= MAX_SCHEMA_VERSION) {
						log.error("Datase has a to up to date schema verion {} > {}! Please update Atlas or downgrade the schema version!", schemaVersion, CURRENT_SCHEMA_VERSION);
					} else if (schemaVersion > CURRENT_SCHEMA_VERSION) {
						log.warn("Database has newer schema version {} > {}!", schemaVersion, CURRENT_SCHEMA_VERSION);
						prepared = true;
					} else if (schemaVersion < MIN_SCHEMA_VERSION) {
						log.error("Database has outdated incompatible schema version {} < {}! Atlas won't start until the schema is up to date!", schemaVersion, CURRENT_SCHEMA_VERSION);
					} else if (schemaVersion < CURRENT_SCHEMA_VERSION) {
						log.warn("Database has outdated schema version {} < {}! (Please update your schema version)", schemaVersion, CURRENT_SCHEMA_VERSION);
						prepared = true;
					} else {
						prepared = true;
					}
				} else {
					log.warn("Unable to read atlas database schema version! (Table emtpy?)");
				}
			} else {
				log.info("Initializing database...");
				InputStream in = getClass().getResourceAsStream("/master/atlas.sql");
				if (in == null) {
					log.error("SQL init file not found!");
				} else {
					List<String> sqls = new ArrayList<>();
					StringBuilder builder = new StringBuilder(1024);
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					boolean begin = true;
					try {
						String line = null;
						while ((line = reader.readLine()) != null) {
							if (line.startsWith("-- atlas")) {
								if (begin) {
									begin = false;
									builder.append(line);
									builder.append('\n');
									continue;
								}
								sqls.add(builder.toString());
								builder.setLength(0);
							}
							builder.append(line);
							builder.append('\n');
						}
						if (builder.length() > 0)
							sqls.add(builder.toString());
					} catch (IOException e) {
						log.error("Error while reading init sql script!", e);
					}
					if (!sqls.isEmpty()) {
						Statement s = con.createStatement();
						s.execute("BEGIN");
						for (String sql : sqls) {
							s.execute(sql);
						}
						s.close();
						PreparedStatement verionStmt = con.prepareStatement("INSERT INTO atlas (atlas_version, schema_version, installation_date) VALUES (?, ?, ?)");
						verionStmt.setString(1, Atlas.FULL_VERSION);
						verionStmt.setInt(2, CURRENT_SCHEMA_VERSION);
						verionStmt.setDate(3, new Date(System.currentTimeMillis()));
						verionStmt.execute();
						verionStmt.close();
						prepared = true;
					}
				}
			}
		} catch (SQLException e) {
			log.error("Error while preparing database!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
		return prepared;
	}

}
