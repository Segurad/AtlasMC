package de.atlasmc.core.node;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.core.node.event.command.CoreCommandListener;
import de.atlasmc.core.node.event.inventory.CoreInventoryListener;
import de.atlasmc.core.node.event.player.CorePlayerListener;
import de.atlasmc.core.node.system.init.ContainerFactoryLoader;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.MethodEventExecutor;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.log.Log;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.network.NodeConfig;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.node.server.NodeServerManager;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.configuration.file.JsonConfiguration;
import de.atlasmc.util.configuration.file.YamlConfiguration;

@StartupHandlerRegister({ StartupContext.LOAD_NODE_DATA })
class CoreLoadNodeDataHandler implements StartupStageHandler {

	private Log log;
	private Map<String, SocketConfig> proxyConfigs;
	private Map<String, ServerGroup> serverGroups;
	private Map<String, NodeConfig> nodeConfigs;
	private Set<NamespacedKey> modules;
	
	@Override
	public void handleStage(StartupContext context) {
		proxyConfigs = new HashMap<>();
		serverGroups = new HashMap<>();
		modules = new HashSet<>();
		nodeConfigs = new HashMap<>();
		log = context.getLogger();
		log.info("Loading configuration...");
		File nodeCfgFile = new File(Atlas.getWorkdir(), "node.yml");
		try {
			FileUtils.extractResource(nodeCfgFile, "/node.yml");
		} catch (IOException e) {
			log.error("Error while extracting node.yml!", e);
		}
		YamlConfiguration config;
		try {
			config = YamlConfiguration.loadConfiguration(nodeCfgFile);
		} catch (IOException e) {
			log.error("Error while loading node.yml!", e);
			context.fail();
			return;
		}
		List<String> nodeConfigNames = config.getStringList("node-configs", List.of());
		Set<String> socketConfigNames = new HashSet<>();
		Set<String> serverGroupNames = new HashSet<>();
		if (!nodeConfigNames.isEmpty()) {
			Collection<NodeConfig> nodeConfigs;
			try {
				 nodeConfigs = AtlasNetwork.getNodeConfigs(nodeConfigNames).get();
			} catch (Exception e) {
				log.error("Error while loading node configs!", e);
				context.fail();
				return;
			}
			for (NodeConfig cfg : nodeConfigs) {
				socketConfigNames.addAll(cfg.getProxies());
				serverGroupNames.addAll(cfg.getServerGroups());
				for (String module : cfg.getCoreModules())
					modules.add(NamespacedKey.of(module));
				this.nodeConfigs.put(cfg.getName(), cfg);
			}
		}
		socketConfigNames.addAll(config.getStringList("sockets", List.of()));
		if (!socketConfigNames.isEmpty()) {
			resolveSockets(socketConfigNames);
		}
		serverGroupNames.addAll(config.getStringList("server-groups", List.of()));
		if (!serverGroupNames.isEmpty()) {
			resolveServerGroups(serverGroupNames);
		}
		if (!modules.isEmpty()) {
			// TODO load remote modules
		}
		NodeServerManager serverManager = AtlasNode.getServerManager();
		for (ServerGroup group : serverGroups.values()) {
			serverManager.registerServerGroup(group);
		}
		SocketManager socketManager = AtlasNode.getSocketManager();
		for (SocketConfig cfg : proxyConfigs.values()) {
			socketManager.createSocket(cfg);
		}
		// === init internals 
		initDefaultExecutor(log, new CorePlayerListener());
		initDefaultExecutor(log, new CoreInventoryListener());
		initDefaultExecutor(log, new CoreCommandListener());
		loadData();
		ContainerFactoryLoader.loadContainerFactories();
	}
	
	private void loadData() {
		InputStream indexStream = Atlas.getSystem().getResourceAsStream("/data/data.json");
		if (indexStream == null)
			log.debug("No data index found");
		JsonConfiguration index;
		try {
			index = JsonConfiguration.loadConfiguration(indexStream);
		} catch (IOException e) {
			log.error("Error while loading data index!", e);
			return;
		} finally {
			try {
				indexStream.close();
			} catch (IOException e) {}
		}
		List<String> registries = index.getStringList("registries", List.of());
		for (String regFile : registries) {
			loadRegistry(regFile);
		}
	}
	
	private void loadRegistry(String resource) {
		String file = "/data/registries/" + resource;
		try {
			Registries.loadBulkRegistryEntries(Atlas.getSystem(), file);
			log.debug("Loaded data registry file: {}", file);
		} catch (Exception e) {
			log.error("Error while loading: " + file, e);
		}
	}
	
	private void resolveServerGroups(Collection<String> serverGroupNames) {
		ServerManager smanager = AtlasNetwork.getServerManager();
		Collection<? extends ServerGroup> groups;
		try {
			groups = smanager.getServerGroups(serverGroupNames).get();
		} catch (Exception e) {
			log.error("Error while loading server groups!", e);
			return;
		}
		for (ServerGroup group : groups) {
			serverGroups.put(group.getName(), group);
		}
		for (String group : serverGroupNames) {
			if (serverGroups.containsKey(group))
				continue;
			log.warn("Unable to load server group: {}", group);
		}
	}
	
	private void resolveSockets(Collection<String> proxyNames) {
		Collection<SocketConfig> proxies;
		try {
			proxies = AtlasNetwork.getSocketConfigs(proxyNames).get();
		} catch (Exception e) {
			log.error("Error while loading proxy configs!", e);
			return;
		}
		for (SocketConfig cfg : proxies) {
			proxyConfigs.put(cfg.getName(), cfg);
		}
		for (String name : proxyNames) {
			if (proxyConfigs.containsKey(name))
				continue;
			log.warn("Unable to load proxy config: {}", name);
		}
	}
	
	private static void initDefaultExecutor(Log log, Listener listener) {
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(Atlas.getSystem(), listener);
		for (EventExecutor exe : exes) {
			Class<? extends Event> clazz = exe.getEventClass();
			log.debug("Set default executor for event: {}", clazz.getSimpleName());
			HandlerList handlers = HandlerList.getHandlerListOf(clazz);
			handlers.setDefaultExecutor(exe);
		}
	}

}
