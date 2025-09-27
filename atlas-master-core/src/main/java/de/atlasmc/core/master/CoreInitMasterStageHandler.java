package de.atlasmc.core.master;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import de.atlasmc.Atlas;
import de.atlasmc.core.master.node.CoreNodeManager;
import de.atlasmc.core.master.permission.CoreSQLPermissionManager;
import de.atlasmc.core.master.proxy.CoreSocketManager;
import de.atlasmc.core.master.server.CoreServerManager;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.master.AtlasMasterBuilder;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.sql.SQLConnectionPool;
import de.atlasmc.util.sql.ScriptRunner;

@StartupHandlerRegister({ StartupContext.INIT_MASTER })
class CoreInitMasterStageHandler implements StartupStageHandler {

	private static final int MIN_SCHEMA_VERSION = 0;
	private static final int CURRENT_SCHEMA_VERSION = 0;
	private static final int MAX_SCHEMA_VERSION = 100;

	private Log log;
	private String dbName;
	private SQLConnectionPool con;

	@Override
	public void handleStage(StartupContext context) {
		log = context.getLogger();
		File masterCfgFile = new File(Atlas.getWorkdir(), "master/master.yml");
		YamlConfiguration masterCfg;
		try {
			masterCfg = YamlConfiguration.loadConfiguration(masterCfgFile);
		} catch (Exception e) {
			log.error("Error while loading master.yml!", e);
			context.fail();
			return;
		}
		ConfigurationSection dbConfig = masterCfg.getConfigurationSection("database");
		con = getDBConnection(dbConfig);
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

		Log masterLogger = Logging.getLogger("Atlas-Master", "Atlas-Master");
		masterLogger.sendToConsole(true);
		AtlasMasterBuilder builder = context.getContext("builder");
		builder.setLogger(masterLogger)
				.setDatabase(con)
				.setUUID(nodeID)
				.setNodeManager(new CoreNodeManager())
				.setSocketManager(new CoreSocketManager())
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
		int poolIdleTimeout = dbConfig.getInt("pool-idle-timeout", (int) TimeUnit.MINUTES.toMillis(5));
		String url = "jdbc:mariadb://" + dbHost + ":" + dbPort + "/" + dbName;
		String params = dbConfig.getString("parameters");
		if (params != null && !params.isBlank())
			url += "?" + params;
		MariaDbPoolDataSource src = new MariaDbPoolDataSource();
		try {
			log.info("Connecting to database...");
			try {
				src.setUser(dbUser);
				src.setPassword(dbPassword);
			} catch(SQLException e) { /* Silence no url set */  }
			src.setUrl(url);
			src.getConnection().close();
		} catch (SQLException e) {
			log.error("Unable to establish database connection!", e);
			return null;
		}
		log.info("Database connection established");
		return new SQLConnectionPool(src, poolMinSize, poolMaxIdleCount, poolMaxSize, poolIdleTimeout);
	}

	private boolean prepareDB() {
		try (Connection con = this.con.getConnection(true)) {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME = 'schema_versions';");
			stmt.setString(1, dbName);
			ResultSet result = stmt.executeQuery();
			boolean schemaPresent = result.next();
			stmt.close();
			result.close();
			if (schemaPresent) {
				stmt = con.prepareStatement("SELECT version FROM schema_versions WHERE plugin = 'atlas-master'");
				stmt.setFetchSize(1);
				result = stmt.executeQuery();
				if (result.next()) {
					int schemaVersion = result.getInt(1);
					if (schemaVersion >= MAX_SCHEMA_VERSION) {
						log.error(
								"Datase has a to up to date schema verion {} > {}! Please update Atlas or downgrade the schema version!",
								schemaVersion, CURRENT_SCHEMA_VERSION);
					} else if (schemaVersion > CURRENT_SCHEMA_VERSION) {
						log.warn("Database has newer schema version {} > {}!", schemaVersion, CURRENT_SCHEMA_VERSION);
						return true;
					} else if (schemaVersion < MIN_SCHEMA_VERSION) {
						log.error(
								"Database has outdated incompatible schema version {} < {}! Atlas won't start until the schema is up to date!",
								schemaVersion, CURRENT_SCHEMA_VERSION);
					} else if (schemaVersion < CURRENT_SCHEMA_VERSION) {
						log.warn("Database has outdated schema version {} < {}! (Please update your schema version)",
								schemaVersion, CURRENT_SCHEMA_VERSION);
						return true;
					} else {
						return true;
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
					ScriptRunner runner = new ScriptRunner(con);
					runner.setAutoCommit(true);
					runner.setSendFullScript(false);
					runner.runScript(new InputStreamReader(in));
					PreparedStatement verionStmt = con.prepareStatement(
							"INSERT INTO schema_versions (name, plugin, plugin-version, version) VALUES (?, ?, ?, ?)");
					verionStmt.setString(1, "atlas-master");
					verionStmt.setString(2, "atlas-master");
					verionStmt.setString(3, Atlas.getSystem().getVersion().toString());
					verionStmt.setInt(4, CURRENT_SCHEMA_VERSION);
					verionStmt.execute();
					verionStmt.close();
					return true;
				}
			}
		} catch (SQLException e) {
			log.error("Error while preparing database!", e);
		} catch (InterruptedException e) {
			log.error("Interrupted while waiting for connection!", e);
		}
		return false;
	}

}
