package de.atlascore.master;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.sql.SQLConnectionPool;
import de.atlasmc.util.sql.ScriptRunner;

@StartupHandlerRegister({ StartupContext.INIT_MASTER })
class CoreInitMasterStageHandler implements StartupStageHandler {

	private static final int MIN_SCHEMA_VERSION = 1;
	private static final int CURRENT_SCHEMA_VERSION = 1;
	private static final int MAX_SCHEMA_VERSION = 1000;

	private Log log;
	private String dbName;
	private SQLConnectionPool con;

	@Override
	public void handleStage(StartupContext context) {
		log = context.getLogger();
		File masterCfgFile = new File(Atlas.getWorkdir(), "master.yml");
		try {
			masterCfgFile = FileUtils.extractResource(masterCfgFile, "/master.yml");
		} catch (IOException e) {
			log.error("Error while extracting master.yml!");
		}
		YamlConfiguration masterCfg = null;
		try {
			masterCfg = YamlConfiguration.loadConfiguration(masterCfgFile);
		} catch (IOException e) {
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
			PreparedStatement stmt = con.prepareStatement(
					"SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME = 'atlas';");
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
						log.error(
								"Datase has a to up to date schema verion {} > {}! Please update Atlas or downgrade the schema version!",
								schemaVersion, CURRENT_SCHEMA_VERSION);
					} else if (schemaVersion > CURRENT_SCHEMA_VERSION) {
						log.warn("Database has newer schema version {} > {}!", schemaVersion, CURRENT_SCHEMA_VERSION);
						prepared = true;
					} else if (schemaVersion < MIN_SCHEMA_VERSION) {
						log.error(
								"Database has outdated incompatible schema version {} < {}! Atlas won't start until the schema is up to date!",
								schemaVersion, CURRENT_SCHEMA_VERSION);
					} else if (schemaVersion < CURRENT_SCHEMA_VERSION) {
						log.warn("Database has outdated schema version {} < {}! (Please update your schema version)",
								schemaVersion, CURRENT_SCHEMA_VERSION);
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
					ScriptRunner runner = new ScriptRunner(con);
					runner.setAutoCommit(true);
					runner.setSendFullScript(false);
					runner.runScript(new InputStreamReader(in));
					PreparedStatement verionStmt = con.prepareStatement(
							"INSERT INTO atlas (atlas_version, schema_version, installation_date) VALUES (?, ?, ?)");
					verionStmt.setString(1, Atlas.FULL_VERSION);
					verionStmt.setInt(2, CURRENT_SCHEMA_VERSION);
					verionStmt.setDate(3, new Date(System.currentTimeMillis()));
					verionStmt.execute();
					verionStmt.close();
					prepared = true;
				}
			}
		} catch (SQLException e) {
			log.error("Error while preparing database!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
				}
		}
		return prepared;
	}

}
