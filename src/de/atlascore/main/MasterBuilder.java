package de.atlascore.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.PooledConnection;

import org.slf4j.Logger;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import de.atlascore.atlasnetwork.CoreAtlasNetwork;
import de.atlascore.atlasnetwork.CoreNetworkInfo;
import de.atlascore.atlasnetwork.master.CoreSQLPlayerProfileHandler;
import de.atlascore.atlasnetwork.master.CoreServerManager;
import de.atlascore.plugin.CoreNodeBuilder;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NetworkInfo.SlotMode;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlFileConfiguration;

public class MasterBuilder {
	
	private Logger log;
	private File workDir;
	private PooledConnection con;
	private ConfigurationSection masterConfig;
	private NetworkInfo info, infoMaintenance;
	private int slots;
	private boolean maintenance;
	private Map<String, ServerGroup> serverGroups;
	private Map<String, NodeConfig> nodeConfigs;
	private Map<String, ProxyConfig> proxyConfigs;
	
	public MasterBuilder(Logger log, File workDir, ConfigurationSection masterConfig) {
		this.log = log;
		this.workDir = workDir;
		this.masterConfig = masterConfig;
		this.serverGroups = new HashMap<>();
		this.nodeConfigs = new HashMap<>();
		this.proxyConfigs = new HashMap<>();
	}

	public AtlasNetwork build() {
		ConfigurationSection dbConfig = masterConfig.getConfigurationSection("database");
		con = getDBConnection(dbConfig);
		if (con == null)
			return null;
		File masterDir = new File(workDir, "master");
		if (!masterDir.exists())
			masterDir.mkdirs();
		File globalCfgFile = null;
		File nodeGroupsCfgFile = null;
		File permissionCfgFile = null;
		File serverGroupsCfgFile = null;
		File proxyCfgFile = null;
		try {
			globalCfgFile = Main.extractConfiguration(masterDir, "global.yml", "/master/global.yml");
			nodeGroupsCfgFile = Main.extractConfiguration(masterDir, "node-groups.yml", "/master/node-groups.yml");
			permissionCfgFile = Main.extractConfiguration(masterDir, "permissions.yml", "/master/permissions.yml");
			serverGroupsCfgFile = Main.extractConfiguration(masterDir, "server-groups.yml", "/master/server-groups.yml");
			proxyCfgFile = Main.extractConfiguration(masterDir, "proxy.yml", "/master/proxy.yml");
		} catch (IOException e) {
			log.error("Error while extracting master configurations!", e);
		}
		loadGlobalConfig(globalCfgFile);
		loadServerGroups(serverGroupsCfgFile);
		loadNodeGroups(nodeGroupsCfgFile);
		loadProxyConfig(proxyCfgFile);
		
		CoreServerManager smanager = new CoreServerManager(serverGroups);
		CoreSQLPlayerProfileHandler profileHandler = new CoreSQLPlayerProfileHandler(con);
		return new CoreAtlasNetwork(profileHandler, info, infoMaintenance, slots, maintenance, smanager, nodeConfigs, proxyConfigs);
	}
	
	private void loadServerGroups(File file) {
		log.info("Loading server groups...");
		Configuration cfg = null;
		try {
			cfg = YamlFileConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading server groups!", e);
			return;
		}
		for (String groupKey : cfg.getKeys()) {
			ConfigurationSection groupCfg = cfg.getConfigurationSection(groupKey);
			if (groupCfg == null)
				continue;
			serverGroups.put(groupKey, new ServerGroup(groupKey, groupCfg));
		}
	}
	
	private void loadProxyConfig(File file) {
		log.info("Loading proxies...");
		Configuration cfg = null;
		try {
			cfg = YamlFileConfiguration.loadConfiguration(file);
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
			cfg = YamlFileConfiguration.loadConfiguration(file);
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
			cfg = YamlFileConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading global config!", e);
			return;
		}
		String serverIcon = loadServerIconBase64(workDir, "server_icon.png");
		String serverIconMaintenance = loadServerIconBase64(workDir, "server_icon_maintenance.png");
		info = loadNetworkInfo(cfg.getConfigurationSection("network-info"), false, serverIcon);
		infoMaintenance = loadNetworkInfo(cfg.getConfigurationSection("network-info-maintenance"), true, serverIconMaintenance);
		slots = cfg.getInt("max-slots");
		maintenance = cfg.getBoolean("maintenance");
	}
	
	private NetworkInfo loadNetworkInfo(ConfigurationSection config, boolean maintenance, String serverIcon) {
		int slots = config.getInt("slots");
		int slotDistance = config.getInt("slot-distance");
		String rawSlotMode = config.getString("slot-mode");
		SlotMode mode = SlotMode.valueOf(rawSlotMode.toUpperCase());
		String motdFirstLine = config.getString("motd-first-line");
		String motdSecondLine = config.getString("motd-second-line");
		String motd = motdFirstLine + motdSecondLine; // TODO implement motd lining
		String protocolText = config.getString("protocol-text");
		List<String> playerInfo = config.getStringList("player-info");
		return new CoreNetworkInfo(slots, slotDistance, mode, motd, protocolText, serverIcon, playerInfo, maintenance);
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
			log.info("Server icon bytes read: {}", in.read(data));
			in.close();
		} catch (IOException e) {
			log.error("Error while loading server icon!", e);
		}
		return Base64.getEncoder().encodeToString(data);
	}
	
	private PooledConnection getDBConnection(ConfigurationSection dbConfig) {
		String dbName = dbConfig.getString("database");
		String dbHost = dbConfig.getString("host");
		int dbPort = dbConfig.getInt("port");
		String dbUser = dbConfig.getString("user");
		String dbPassword = dbConfig.getString("password");
		MysqlConnectionPoolDataSource src = new MysqlConnectionPoolDataSource();
		src.setDatabaseName(dbName);
		src.setUser(dbUser);
		src.setPassword(dbPassword);
		src.setPort(dbPort);
		src.setServerName(dbHost);
		PooledConnection con = null;
		try {
			log.info("Connecting to database...");
			con = src.getPooledConnection();
			log.info("Connection established");
		} catch (SQLException e) {
			log.error("Unable to establish a connection!", e);
		}
		return con;
	}

}
