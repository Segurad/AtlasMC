package de.atlascore.master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.atlascore.atlasnetwork.CoreNetworkInfo;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NetworkInfo.SlotMode;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.log.Log;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.AtlasMasterBuilder;
import de.atlasmc.master.PermissionManager;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.proxy.ProxyManager;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.master.server.ServerGroupBuilder;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;

@StartupHandlerRegister({ StartupContext.LOAD_MASTER_DATA })
class CoreLoadMasterDataStageHandler implements StartupStageHandler {

	private Log log;
	
	@Override
	public void handleStage(StartupContext context) {
		log = context.getLogger();
		File masterDir = new File(Atlas.getWorkdir(), "master");
		FileUtils.ensureDir(masterDir);
		File globalCfgFile = null;
		File nodeGroupsCfgFile = null;
		File permissionCfgFile = null;
		File serverGroupsCfgFile = null;
		File proxyCfgFile = null;
		boolean override = false;
		if (Boolean.getBoolean("atlas.config.override")) {
			override = true;
		}
		try {
			globalCfgFile = FileUtils.extractResource(new File(masterDir, "global.yml"), "/master/global.yml", override);
			nodeGroupsCfgFile = FileUtils.extractResource(new File(masterDir, "node-groups.yml"), "/master/node-groups.yml", override);
			permissionCfgFile = FileUtils.extractResource(new File(masterDir, "permissions.yml"), "/master/permissions.yml", override);
			serverGroupsCfgFile = FileUtils.extractResource(new File(masterDir, "server-groups.yml"), "/master/server-groups.yml", override);
			proxyCfgFile = FileUtils.extractResource(new File(masterDir, "proxy.yml"), "/master/proxy.yml", override);
		} catch (IOException e) {
			log.error("Error while extracting master configurations!", e);
		}
		loadGlobalConfig(globalCfgFile);
		loadServerGroups(serverGroupsCfgFile);
		loadNodeGroups(nodeGroupsCfgFile);
		loadProxyConfig(proxyCfgFile);
		loadPermissions(permissionCfgFile);
	}
	
	private void loadPermissions(File file) {
		log.info("Loading permissions...");
		try (Connection con = AtlasMaster.getDatabase().getConnection()) {
			boolean load = true;
			if (Boolean.getBoolean("atlas.config.permission.reload")) {
				log.warn("Permission reload flag set!");
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
				loadPermissionFile(file);
			}
		} catch (SQLException e) {
			log.error("Error while loading permissions!", e);
		}
	}
	

	
	private void loadPermissionFile(File file) {
		log.info("Loading permissions...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while load permissions!", e);
			return;
		}
		PermissionManager permManager = AtlasMaster.getPermissionManager();
		List<ConfigurationSection> groupCfgs = cfg.getConfigurationList("groups");
		for (ConfigurationSection groupCfg : groupCfgs) {
			if (groupCfg == null)
				continue;
			String name = groupCfg.getString("name");
			if (name == null) {
				log.warn("Group without \"name\" attribute found! (skipped)");
				continue;
			}
			PermissionGroup group;
			try {
				group = permManager.createGroup(name).get();
			} catch (InterruptedException e) {
				log.error("Interrupted while creating group: " + name, e);
				continue;
			} catch (ExecutionException e) {
				log.error("Error while creating group: " + name, e);
				continue;
			}
			int sortWeight = groupCfg.getInt("sort-weight");
			group.setSortWeight(sortWeight);
			String prefix = groupCfg.getString("prefix");
			group.setPrefix(prefix == null ? null : ChatUtil.toChat(prefix));
			String suffix = groupCfg.getString("suffix");
			group.setSuffix(suffix == null ? null : ChatUtil.toChat(suffix));
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
						PermissionContext context;
						try {
							context = permManager.createContext(contextKey, contextKeyValue).get();
						} catch (InterruptedException e) {
							log.error("Interrupted while creating permission context \"" + contextKey + ":" + contextKeyValue + "\" for group: " + name, e);
							continue;
						} catch (ExecutionException e) {
							log.error("Error while creating permission context \"" + contextKey + ":" + contextKeyValue + "\" for group: " + name, e);
							continue;
						}
						for (Permission perm : permissions) {
							context.setPermission(perm);
						}
						group.addPermissionContext(context);
					}
				}
			}
			ConfigurationSection context = groupCfg.getConfigurationSection("context");
			if (context != null) {
				ContextProvider ctxProvider = group.getContext();
				for (String key : context.getKeys()) {
					String value = context.getString(key);
					if (key == null)
						continue;
					ctxProvider.set(key, value);
				}
			}
			permManager.saveGroup(group);
			log.debug("Added permission group: {}", group.getName());
		}
	}
	
	private List<Permission> getPermissions(List<String> raw) {
		if (raw == null || raw.isEmpty())
			return List.of();
		PermissionManager permManager = AtlasMaster.getPermissionManager();
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
			permissions.add(permManager.createPermission(splitt[0], value));
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
	
	private void loadServerGroups(File file) {
		log.info("Loading server groups...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading server groups!", e);
			return;
		}
		List<ConfigurationSection> groupCfgs = cfg.getConfigurationList("server-groups");
		ServerManager manager = AtlasMaster.getServerManager();
		ServerGroupBuilder builder = new ServerGroupBuilder();
		for (ConfigurationSection groupCfg : groupCfgs) {
			builder.setConfiguration(groupCfg);
			ServerGroup group = manager.createServerGroup(builder);
			log.debug("Registered group: {}", group.getName());
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
		ProxyManager proxyManager = AtlasMaster.getProxyManager();
		List<ConfigurationSection> proxyCfgs = cfg.getConfigurationList("proxies");
		for (ConfigurationSection proxyCfg : proxyCfgs) {
			ProxyConfig proxy = new ProxyConfig(proxyCfg);
			proxyManager.addProxyConfig(proxy);
		}
	}
	
	private void loadNodeGroups(File file) {
		log.info("Loading node configs...");
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while loading node configs!", e);
			return;
		}
		NodeManager nodeManager = AtlasMaster.getNodeManager();
		List<ConfigurationSection> nodeCfgs = cfg.getConfigurationList("node-configs");
		for (ConfigurationSection groupCfg : nodeCfgs) {
			NodeConfig group = new NodeConfig(groupCfg);
			nodeManager.addNodeConfig(group);
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
		String serverIcon = loadServerIconBase64(log, "server_icon.png");
		String serverIconMaintenance = loadServerIconBase64(log, "server_icon_maintenance.png");
		NetworkInfo info = loadNetworkInfo(cfg.getConfigurationSection("network-info"), serverIcon);
		NetworkInfo infoMaintenance = loadNetworkInfo(cfg.getConfigurationSection("network-info-maintenance"), serverIconMaintenance);
		int slots = cfg.getInt("max-slots");
		boolean maintenance = cfg.getBoolean("maintenance");
		AtlasMaster.setMaxSlots(slots);
		AtlasMaster.setMaintenace(maintenance);
		AtlasMaster.setNetworkInfo(info);
		AtlasMaster.setNetworkInfoMaintenance(infoMaintenance);
	}
	
	private CoreNetworkInfo loadNetworkInfo(ConfigurationSection config, String serverIcon) {
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
	
	private String loadServerIconBase64(Log log, String filename) {
		File iconFile = new File(Atlas.getWorkdir(), filename);
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
			in = AtlasMasterBuilder.class.getResourceAsStream("/assets/server_icon.png");
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

}
