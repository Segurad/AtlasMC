package de.atlasmc.core.node;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.core.node.system.init.ContainerFactoryLoader;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.log.Log;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.network.NodeConfig;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.io.socket.SocketManager;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.configuration.file.JsonConfiguration;
import de.atlasmc.util.configuration.file.JsonConfigurationFactory;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.configuration.file.YamlConfigurationFactory;

@StartupHandlerRegister({ StartupContext.LOAD_NODE_DATA })
class CoreLoadNodeDataHandler implements StartupStageHandler {

	private Log log;
	private Map<String, NodeConfig> nodeConfigs;
	private Set<NamespacedKey> modules;
	
	@Override
	public void handleStage(StartupContext context) {
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
			config = YamlConfigurationFactory.INSTANCE.load(nodeCfgFile);
		} catch (IOException e) {
			log.error("Error while loading node.yml!", e);
			context.fail();
			return;
		}
		List<String> nodeConfigNames = config.getStringList("node-configs", List.of());
		Set<String> serverGroupNames = new HashSet<>();
		if (!nodeConfigNames.isEmpty()) {
			Collection<NodeConfig> nodeConfigs;
			try {
				 nodeConfigs = AtlasNetwork.getMasterClient().getNodeConfigs(nodeConfigNames).get();
			} catch (Exception e) {
				log.error("Error while loading node configs!", e);
				context.fail();
				return;
			}
			for (NodeConfig cfg : nodeConfigs) {
				serverGroupNames.addAll(cfg.getServerGroups());
				for (String module : cfg.getCoreModules())
					modules.add(NamespacedKey.of(module));
				this.nodeConfigs.put(cfg.getName(), cfg);
			}
		}
		serverGroupNames.addAll(config.getStringList("server-groups", List.of()));
		if (!modules.isEmpty()) {
			// TODO load remote modules
		}
		SocketManager socketManager = AtlasNode.getSocketManager();
		List<ConfigurationSection> sockets = config.getConfigurationList("socket");
		if (sockets != null && !sockets.isEmpty()) {
			for (ConfigurationSection socketCfg : sockets) {
				var socketKey = socketCfg.getString("config");
				SocketConfig socket;
				if (socketKey == null) {
					socket = resolveSocket(socketKey);
				} else {
					socket = ConfigurationSerializable.deserialize(socketCfg.getConfigurationSection("config"), SocketConfig.class);
				}
				InetSocketAddress hostOverride = null;
				InetSocketAddress externalHost = null;
				var rawHost = socketCfg.getString("host");
				if (rawHost != null)
					hostOverride = InetSocketAddress.createUnresolved(rawHost, socketCfg.getInt("port", 25565));
				var rawExternal = socketCfg.getString("external-host");
				if (rawExternal != null) {
					externalHost = InetSocketAddress.createUnresolved(rawExternal, socketCfg.getInt("external-port", 25565));
				}
				log.info("Creating socket: {}", socket.getName());
				socketManager.createSocket(socket, hostOverride, externalHost);
			}
		}
		// === init internals 
		loadData();
		ContainerFactoryLoader.loadContainerFactories();
	}
	
	private void loadData() {
		InputStream indexStream = Atlas.getSystem().getResourceAsStream("/data/data.json");
		if (indexStream == null)
			log.debug("No data index found");
		JsonConfiguration index;
		try {
			index = JsonConfigurationFactory.INSTANCE.load(indexStream);
		} catch (IOException e) {
			log.error("Error while loading data index!", e);
			return;
		} finally {
			try {
				if (indexStream != null)
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
	
	private SocketConfig resolveSocket(String configName) {
		SocketConfig socket;
		try {
			socket = AtlasNetwork.getMasterClient().getSocketConfig(configName).get();
		} catch (Exception e) {
			log.error("Error while loading socket configs!", e);
			return null;
		}
		if (socket == null)
			log.warn("Unable to load socket config: {}", configName);
		return socket;
	}

}
