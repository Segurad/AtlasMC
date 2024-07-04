package de.atlascore.atlasnetwork.master;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import de.atlascore.atlasnetwork.CoreNetworkInfo;
import de.atlascore.atlasnetwork.CorePermissionProvider;
import de.atlascore.atlasnetwork.master.server.CoreServerGroup;
import de.atlascore.atlasnetwork.master.server.CoreServerManager;
import de.atlascore.chat.CoreChat;
import de.atlascore.permission.CorePermission;
import de.atlascore.permission.CorePermissionContext;
import de.atlascore.permission.CorePermissionGroup;
import de.atlascore.plugin.CoreNodeBuilder;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NetworkInfo.SlotMode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.log.Log;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.util.Builder;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.sql.SQLConnectionPool;

public class CoreMasterBuilder implements Builder<CoreAtlasNetwork> {
	
	private static final int MIN_SCHEMA_VERSION = 1;
	private static final int CURRENT_SCHEMA_VERSION = 1;
	private static final int MAX_SCHEMA_VERSION = 1000;
	
	private final Log log;
	private final File workDir;
	private final Map<String, String> arguments;
	private SQLConnectionPool con;
	private String schema;
	private final ConfigurationSection masterConfig;
	private NetworkInfo info, infoMaintenance;
	private int slots;
	private boolean maintenance;
	private CoreServerGroup fallBack;
	private Map<String, CoreServerGroup> serverGroups;
	private Map<String, NodeConfig> nodeConfigs;
	private Map<String, ProxyConfig> proxyConfigs;
	boolean override = false;
	private final KeyPair keyPair;
	private DataRepositoryHandler repoHandler;
	
	public CoreMasterBuilder(Log log, File workDir, ConfigurationSection masterConfig, Map<String, String> arguments, KeyPair keyPair) {
		this.log = log;
		this.workDir = workDir;
		this.arguments = arguments;
		this.masterConfig = masterConfig;
		this.keyPair = keyPair;
		this.serverGroups = new HashMap<>();
		this.nodeConfigs = new HashMap<>();
		this.proxyConfigs = new HashMap<>();
	}
	
	public CoreMasterBuilder setRepoHandler(DataRepositoryHandler repoHandler) {
		this.repoHandler = repoHandler;
		return this;
	}

	public CoreAtlasNetwork build() {
		ConfigurationSection dbConfig = masterConfig.getConfigurationSection("database");
		con = getDBConnection(dbConfig);
		if (con == null) {
			log.error("Unable to initialize database connection is not present!");
			System.exit(1);
		}
		if (!prepareDB()) {
			log.error("A problem occured while preparing the database!");
			System.exit(1);
		}
		
		File masterDir = new File(workDir, "master");
		if (!masterDir.exists())
			masterDir.mkdirs();
		File globalCfgFile = null;
		File nodeGroupsCfgFile = null;
		File permissionCfgFile = null;
		File serverGroupsCfgFile = null;
		File proxyCfgFile = null;
		boolean override = false;
		if (arguments.containsKey("atlas.config.override")) {
			override = true;
		}
		try {
			globalCfgFile = FileUtils.extractConfiguration(masterDir, "global.yml", "/master/global.yml", override, getClass());
			nodeGroupsCfgFile = FileUtils.extractConfiguration(masterDir, "node-groups.yml", "/master/node-groups.yml", override, getClass());
			permissionCfgFile = FileUtils.extractConfiguration(masterDir, "permissions.yml", "/master/permissions.yml", override, getClass());
			serverGroupsCfgFile = FileUtils.extractConfiguration(masterDir, "server-groups.yml", "/master/server-groups.yml", override, getClass());
			proxyCfgFile = FileUtils.extractConfiguration(masterDir, "proxy.yml", "/master/proxy.yml", override, getClass());
		} catch (IOException e) {
			log.error("Error while extracting master configurations!", e);
		}
		loadGlobalConfig(globalCfgFile);
		loadServerGroups(serverGroupsCfgFile);
		loadNodeGroups(nodeGroupsCfgFile);
		loadProxyConfig(proxyCfgFile);
		loadPermissions(permissionCfgFile);
		
		File nodeIDFile = new File(workDir, "node-id.yml");
		UUID nodeID = null;
		if (nodeIDFile.exists()) {
			YamlConfiguration nodeIDCfg = null;
			try {
				nodeIDCfg = YamlConfiguration.loadConfiguration(nodeIDFile);
			} catch (IOException e) {
				log.error("Error while loading node id file!", e);
				System.exit(1);
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
				System.exit(1);
			}
		}
		
		CoreServerManager smanager = new CoreServerManager(fallBack, serverGroups);
		CoreSQLPlayerProfileHandler profileHandler = new CoreSQLPlayerProfileHandler(con);
		CorePermissionProvider permProvider = new CoreSQLPermissionProvider(con);
		return new CoreAtlasNetwork(profileHandler, permProvider, info, infoMaintenance, slots, maintenance, smanager, nodeConfigs, proxyConfigs, repoHandler, keyPair, nodeID);
	}
	
	private void loadServerGroups(File file) {
		log.info("Loading server groups...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading server groups!", e);
			return;
		}
		for (String groupKey : cfg.getKeys()) {
			ConfigurationSection groupCfg = cfg.getConfigurationSection(groupKey);
			if (groupCfg == null)
				continue;
			serverGroups.put(groupKey, new CoreServerGroup(groupKey, groupCfg));
			log.debug("registered group: {}", groupKey);
		}
	}
	
	private void loadProxyConfig(File file) {
		log.info("Loading proxies...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading proxies!", e);
			return;
		}
		for (String groupKey : cfg.getKeys()) {
			ConfigurationSection groupCfg = cfg.getConfigurationSection(groupKey);
			if (groupCfg == null)
				continue;
			proxyConfigs.put(groupKey, new ProxyConfig(groupKey, groupCfg));
		}
	}
	
	private void loadServerConfig(File file) {
		
	}
	
	private void loadNodeGroups(File file) {
		log.info("Loading node groups...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading node groups!", e);
			return;
		}
		for (String groupKey : cfg.getKeys()) {
			ConfigurationSection nodeCfg = cfg.getConfigurationSection(groupKey);
			if (nodeCfg == null)
				continue;
			nodeConfigs.put(groupKey, new NodeConfig(groupKey, nodeCfg));
		}
	}
	
	private void loadGlobalConfig(File file) {
		log.info("Loading global config...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading global config!", e);
			return;
		}
		String serverIcon = loadServerIconBase64(workDir, "server_icon.png");
		String serverIconMaintenance = loadServerIconBase64(workDir, "server_icon_maintenance.png");
		info = loadNetworkInfo(cfg.getConfigurationSection("network-info"), serverIcon);
		infoMaintenance = loadNetworkInfo(cfg.getConfigurationSection("network-info-maintenance"), serverIconMaintenance);
		slots = cfg.getInt("max-slots");
		maintenance = cfg.getBoolean("maintenance");
	}
	
	private NetworkInfo loadNetworkInfo(ConfigurationSection config, String serverIcon) {
		int slots = config.getInt("slots");
		int slotDistance = config.getInt("slot-distance");
		String rawSlotMode = config.getString("slot-mode");
		SlotMode mode = SlotMode.valueOf(rawSlotMode.toUpperCase());
		String motdFirstLine = config.getString("motd-first-line");
		String motdSecondLine = config.getString("motd-second-line");
		String motd = motdFirstLine + motdSecondLine; // TODO implement motd lining
		String protocolText = config.getString("protocol-text");
		boolean useProtocolText = config.getBoolean("use-protocol-text");
		List<String> playerInfo = config.getStringList("player-info");
		return new CoreNetworkInfo(slots, slotDistance, mode, motd, protocolText, serverIcon, playerInfo, useProtocolText);
	}
	
	private String loadServerIconBase64(File workdir, String filename) {
		File iconFile = new File(workdir, filename);
		InputStream in = null;
		int size = -1;
		if (iconFile.exists()) {
			try {
				size = (int) iconFile.length();
				in = new FileInputStream(iconFile);
			} catch (FileNotFoundException e) {
				log.error("Unable to find server icon!", e);
			}
		}
		if (in == null) {
			in = CoreNodeBuilder.class.getResourceAsStream("/assets/server_icon.png");
			try {
				size = in.available();
			} catch (IOException e) {
				log.error("Error while loading server icon!", e);
				try {
					in.close();
				} catch (IOException e1) {}
			}
		}
		if (size == -1) {
			return null;
		}
		byte[] data = new byte[size];
		try {
			log.debug("Server icon bytes read: {}", in.read(data));
			in.close();
		} catch (IOException e) {
			log.error("Error while loading server icon!", e);
		}
		return Base64.getEncoder().encodeToString(data);
	}
	
	private SQLConnectionPool getDBConnection(ConfigurationSection dbConfig) {
		String dbName = dbConfig.getString("database");
		this.schema = dbName;
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
			stmt.setString(1, schema);
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
	
	private void loadPermissions(File file) {
		log.info("Loading permissions...");
		Connection con = null;
		try {
			con = this.con.getConnection();
			boolean load = true;
			if (arguments.containsKey("atlas.config.permission.reload")) {
				if (!dropPermissions(con)) {
					con.close();
					return;
				}
			} else {
				PreparedStatement stmt = con.prepareStatement("SELECT COUNT(group_id) as count FROM perm_groups");
				ResultSet result = stmt.executeQuery();
				if (!result.next() || result.getInt(1) != 0)
					load = false;
				stmt.close();
				result.close();
			}
			if (load) {
				Collection<CorePermissionGroup> group = loadPermissionFile(file);
				insertPermissions(con, group);
			}
		} catch (SQLException e) {
			log.error("Error while loading permissions!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}
	
	private void insertPermissions(Connection con, Collection<CorePermissionGroup> groups) throws SQLException {
		HashMap<PermissionGroup, Integer> inserted = new HashMap<>();
		for (PermissionGroup group : groups) {
			insertGroup(con, group, inserted);
		}
	}
	
	private void insertGroup(Connection con, PermissionGroup group, HashMap<PermissionGroup, Integer> inserted) throws SQLException {
		if (inserted.containsKey(group))
			return;
		Collection<PermissionGroup> parents = group.getParents();
		for (PermissionGroup parent : parents) {
			insertGroup(con, parent, inserted);
		}
		if (inserted.containsKey(group)) {
			log.warn("Permission group present after resolving inheritance! Circular inheritance possible? Group: {}", group.getName());
			return;
		}
		PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_groups (name, sort_weight, prefix, suffix, chat_color, name_color, power, is_default) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, group.getName());
		stmt.setInt(2, group.getSortWeight());
		stmt.setString(3, group.getPrefix().toText());
		stmt.setString(4, group.getPrefix().toText());
		stmt.setInt(5, group.getChatColor().getColor().asRGB());
		stmt.setInt(6, group.getNameColor().getID());
		stmt.setInt(7, group.getPower());
		stmt.setBoolean(8, group.isDefault());
		stmt.execute();
		ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
		if (!result.next()) {
			stmt.close();
			result.close();
			log.error("Error while inserting group {}! (No id returned)", group.getName());
			return;
		}
		final int groupID = result.getInt(1);
		stmt.close();
		result.close();
		inserted.put(group, groupID);
		stmt = con.prepareStatement("INSERT INTO perm_group_perm (group_id, perm, power) VALUES (?, ?, ?)");
		stmt.setInt(1, groupID);
		for (Permission perm : group.getPermissions()) {
			stmt.setString(2, perm.permission());
			stmt.setInt(3, perm.value());
			stmt.execute();
		}
		stmt.close();
		stmt = con.prepareStatement("INSERT INTO perm_group_context (group_id, ctx_key, ctx_value) VALUES (?, ?, ?)");
		stmt.setInt(1, groupID);
		for (Entry<String, String> entry : group.getContext().entrySet()) {
			stmt.setString(2, entry.getKey());
			stmt.setString(3, entry.getValue());
			stmt.execute();
		}
		stmt.close();
		stmt = con.prepareStatement("INSERT INTO perm_group_inherit (group_id, parent_id) VALUES (?, ?)");
		stmt.setInt(1, groupID);
		for (PermissionGroup parent : parents) {
			stmt.setInt(2, inserted.get(parent));
			stmt.execute();
		}
		stmt.close();
		PreparedStatement ctxStmt = con.prepareStatement("INSERT INTO perm_context (ctx_key, ctx_value) VALUES (?, ?)");
		PreparedStatement ctxPermStmt = con.prepareStatement("INSERT INTO perm_context_perm (context_id, perm, power) VALUES (?, ?, ?)");
		PreparedStatement groupCTXStmt = con.prepareStatement("INSERT INTO perm_group_perm_context (group_id, context_id) VALUES (?, ?)");
		groupCTXStmt.setInt(1, groupID);
		for (PermissionContext context : group.getContexts()) {
			ctxStmt.setString(1, context.getContextKey());
			ctxStmt.setString(2, context.getContext());
			ctxStmt.execute();
			result = ctxStmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (!result.next()) {
				result.close();
				log.error("Error while inserting group {} context {}:{}! (No id returned)", group.getName(), context.getContextKey(), context.getContext());
				continue;
			}
			final int ctxID = result.getInt(1);
			result.close();
			groupCTXStmt.setInt(2, ctxID);
			groupCTXStmt.execute();
			ctxPermStmt.setInt(1, ctxID);
			for (Permission perm : context.getPermissions()) {
				ctxPermStmt.setString(2, perm.permission());
				ctxPermStmt.setInt(3, perm.value());
				ctxPermStmt.execute();
			}
		}
		ctxStmt.close();
		ctxPermStmt.close();
		groupCTXStmt.close();
	}
	
	private Collection<CorePermissionGroup> loadPermissionFile(File file) {
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while load permissions!", e);
			return List.of();
		}
		Map<String, CorePermissionGroup> groups = new HashMap<>();
		Map<CorePermissionGroup, List<String>> unresolvedInheritance = new HashMap<>();
		for (String groupKey : cfg.getKeys()) {
			ConfigurationSection groupCfg = cfg.getConfigurationSection(groupKey);
			if (groupCfg == null)
				continue;
			String name = groupCfg.getString("name");
			CorePermissionGroup group = new CorePermissionGroup(name);
			groups.put(groupKey, group);
			int sortWeight = groupCfg.getInt("sort-weight");
			group.setSortWeight(sortWeight);
			String prefix = groupCfg.getString("prefix");
			group.setPrefix(prefix.charAt(0) == '{' ? new CoreChat(prefix, null) : new CoreChat(null, prefix));
			String suffix = groupCfg.getString("suffix");
			group.setSuffix(suffix.charAt(0) == '{' ? new CoreChat(suffix, null) : new CoreChat(null, suffix));
			ChatColor chatColor = ChatColor.getByFormatID(groupCfg.getString("chat-color").charAt(1));
			group.setChatColor(chatColor);
			ChatColor nameColor = ChatColor.getByFormatID(groupCfg.getString("name-color").charAt(1));
			group.setNameColor(nameColor);
			int power  = groupCfg.getInt("power");
			group.setPower(power);
			boolean isDefault = groupCfg.getBoolean("default");
			group.setDefault(isDefault);
			for (Permission perm : getPermissions(groupCfg.getStringList("permissions"))) {
				group.setPermission(perm);
			}
			ConfigurationSection contextPermsCfg = groupCfg.getConfigurationSection("context-permissions");
			if (contextPermsCfg != null) {
				for (String contextKey : contextPermsCfg.getKeys()) {
					ConfigurationSection contextPermsValuesCfg = contextPermsCfg.getConfigurationSection(contextKey);
					if (contextPermsValuesCfg == null)
						continue;
					for (String contextKeyValue : contextPermsValuesCfg.getKeys()) {
						List<Permission> permissions = getPermissions(contextPermsValuesCfg.getStringList(contextKeyValue));
						if (permissions.isEmpty())
							continue;
						CorePermissionContext context = new CorePermissionContext(contextKey, contextKeyValue);
						for (Permission perm : permissions) {
							context.setPermission(perm);
						}
						group.addPermissionContext(context);
					}
				}
			}
			ConfigurationSection context = groupCfg.getConfigurationSection("context");
			if (context != null) {
				for (String key : context.getKeys()) {
					String value = context.getString(key);
					if (key != null)
						group.setContext(key, value);
				}
			}
			List<String> parents = groupCfg.getStringList("inherited-groups");
			if (parents != null)
				unresolvedInheritance.put(group, parents);
		}
		unresolvedInheritance.forEach((group, parents) -> {
			for (String rawParent : parents) {
				PermissionGroup parent = groups.get(rawParent);
				if (parent != null)
					group.addParent(group);
				else
					log.warn("Unresolved parent permission group \"{}\" for group \"{}\"", rawParent, group.getName());
			}
		});
		return groups.values();
	}
	
	private List<Permission> getPermissions(List<String> raw) {
		if (raw == null || raw.isEmpty())
			return List.of();
		List<Permission> permissions = new ArrayList<>(raw.size());
		for (String permission : raw) {
			int value = -1;
			String[] splitt = permission.split(":");
			if (splitt.length > 1) {
				value = NumberConversion.toInt(splitt[1], -1);
				if (value == -1) {
					boolean bool = Boolean.parseBoolean(splitt[1]);
					value = bool ? 1 : 0;
				}
			} else
				value = 1;
			permissions.add(new CorePermission(splitt[0], value));
		}
		return permissions;
	}
	
	private boolean dropPermissions(Connection con) {
		log.info("Removing old permissions...");
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_context WHERE context_id in (SELECT context_id FROM perm_group_perm_context)");
			stmt.execute();
			stmt.close();
			stmt = con.prepareStatement("DELETE FROM perm_groups");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error while removing permissions!", e);
			return false;
		}
		return true;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
