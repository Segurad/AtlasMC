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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlascore.master.server.CoreServerGroup;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.NetworkInfo.SlotMode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.log.Log;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.AtlasMasterBuilder;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.master.server.ServerGroupBuilder;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.plugin.StartupContext;
import de.atlasmc.plugin.StartupStageHandler;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;

class CoreLoadMasterDataStageHandler implements StartupStageHandler {

	private Log log;
	
	@Override
	public void handleStage(StartupContext context) {
		log = AtlasMaster.getLogger();
		File masterDir = new File(Atlas.getWorkdir(), "master");
		FileUtils.ensureDir(masterDir);
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
	
	private void insertPermissions(Connection con, Collection<PermissionGroup> groups) throws SQLException {
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
		stmt.setInt(5, group.getChatColor().asRGB());
		stmt.setInt(6, group.getNameColor().asRGB());
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
		for (PermissionContext context : group.getPermissionContexts()) {
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
	
	private Collection<PermissionGroup> loadPermissionFile(File file) {
		Configuration cfg = null;
		try {
			cfg = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			log.error("Error while load permissions!", e);
			return List.of();
		}
		Map<String, PermissionGroup> groups = new HashMap<>();
		Map<PermissionGroup, List<String>> unresolvedInheritance = new HashMap<>();
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
			group.setPrefix(ChatUtil.toChat(prefix));
			String suffix = groupCfg.getString("suffix");
			group.setSuffix(ChatUtil.toChat(suffix));
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
						group.set(key, value);
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
			log.debug("registered group: {}", group.getName());
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
		List<ConfigurationSection> proxyCfgs = cfg.getConfigurationList("proxies");
		for (ConfigurationSection proxyCfg : proxyCfgs) {
			ProxyConfig proxy = new ProxyConfig(proxyCfg);
			proxyConfigs.put(proxy.getName(), proxy);
		}
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
		List<ConfigurationSection> nodeCfgs = cfg.getConfigurationList("node-groups");
		for (ConfigurationSection groupCfg : nodeCfgs) {
			NodeConfig group = new NodeConfig(groupCfg);
			nodeConfigs.put(group.getName(), group);
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
		info = loadNetworkInfo(cfg.getConfigurationSection("network-info"), serverIcon);
		infoMaintenance = loadNetworkInfo(cfg.getConfigurationSection("network-info-maintenance"), serverIconMaintenance);
		slots = cfg.getInt("max-slots");
		maintenance = cfg.getBoolean("maintenance");
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
