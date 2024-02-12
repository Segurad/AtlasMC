package de.atlascore.plugin;

import java.io.File;
import java.security.KeyPair;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.factory.ChatFactory;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.NodeBuilder;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.TickingThread;
import de.atlasmc.util.configuration.Configuration;

public class CoreNodeBuilder implements NodeBuilder {
	
	private final KeyPair keyPair;
	private final Log log;
	private final AtlasNetwork network;
	private Scheduler scheduler;
	private final File workdir;
	private DataRepositoryHandler dataHandler;
	private ChatFactory chatFactory;
	private ProtocolAdapter defaultProtocol;
	private List<ProtocolAdapter> protAdapter;
	private Map<String, NodeConfig> nodeConfigs;
	private Map<String, ProxyConfig> proxyConfigs;
	private Map<String, ServerGroup> serverGroups;
	private Set<NamespacedKey> modules;
	private PluginManager pluginManager;
	private TickingThread mainThread;
	private final UUID uuid;
	
	public CoreNodeBuilder(UUID uuid, AtlasNetwork network, Log logger, File workdir, Configuration config, KeyPair keyPair) {
		this.uuid = uuid;
		this.network = network;
		this.log = logger;
		this.keyPair = keyPair;
		this.workdir = workdir;
		this.nodeConfigs = new HashMap<>();
		this.proxyConfigs = new HashMap<>();
		this.serverGroups = new HashMap<>();
		log.info("Loading configuration...");
		this.modules = new HashSet<>();
		for (String group : config.getStringList("node-groups", List.of())) {
			if (nodeConfigs.containsKey(group))
				continue;
			NodeConfig cfg = network.getNodeConfig(group);
			if (cfg == null) {
				log.warn("Unable to load node config: {}", group);
				continue;
			}
			for (String modul : cfg.getCoreModules())
				this.modules.add(NamespacedKey.of(modul));
			nodeConfigs.put(group, cfg);
		}
		resolveProxies(config.getStringList("proxies", List.of()));
		for (NodeConfig cfg : nodeConfigs.values()) {
			resolveProxies(cfg.getProxies());
		}
		resolveServerGroups(config.getStringList("server-groups", List.of()));
		for (NodeConfig cfg : nodeConfigs.values()) {
			resolveServerGroups(cfg.getServerGroups());
		}
	}
	
	private void resolveServerGroups(Collection<String> serverGroups) {
		ServerManager smanager = network.getServerManager();
		for (String groupKey : serverGroups) {
			if (this.serverGroups.containsKey(groupKey))
				continue;
			ServerGroup group = smanager.getServerGroup(groupKey);
			if (group == null) {
				log.warn("Unable to load server group: {}", groupKey);
				continue;
			}
			this.serverGroups.put(groupKey, group);
		}
	}
	
	private void resolveProxies(Collection<String> proxies) {
		for (String proxyKey : proxies) {
			if (proxyConfigs.containsKey(proxyKey))
				continue;
			ProxyConfig proxyCfg = network.getProxyConfig(proxyKey);
			if (proxyCfg == null) {
				log.warn("Unable to load proxy config: {}", proxyKey);
				continue;
			}
			proxyConfigs.put(proxyKey, proxyCfg);
		}
	}
	
	@Override
	public TickingThread getMainThread() {
		return mainThread;
	}
	
	@Override
	public void setMainThread(TickingThread mainThread) {
		this.mainThread = mainThread;
	}
	
	@Override
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	@Override
	public void setPluginManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}
	
	@Override
	public void setDataHandler(DataRepositoryHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	@Override
	public DataRepositoryHandler getDataHandler() {
		return dataHandler;
	}
	
	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	@Override
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	@Override
	public ProtocolAdapter getDefaultProtocol() {
		return defaultProtocol;
	}
	
	@Override
	public void setDefaultProtocol(ProtocolAdapter defaultProtocol) {
		this.defaultProtocol = defaultProtocol;
	}
	
	@Override
	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	public LocalAtlasNode build(boolean initAtlas) {
		if (network == null)
			throw new IllegalStateException("Can not build Atlas without defined AtlasNetwork");
		LocalAtlasNode node = new LocalAtlasNode(uuid, log, scheduler, workdir, pluginManager, mainThread, keyPair, dataHandler);
		node.getProtocolAdapterHandler().setProtocol(defaultProtocol);
		if (initAtlas)
			Atlas.init(node, network);
		ChatUtil.init(chatFactory);
		return node;
	}

	@Override
	public Collection<ProxyConfig> getProxyConfigs() {
		return proxyConfigs.values();
	}

	@Override
	public void setChatFactory(ChatFactory chatFactory) {
		this.chatFactory = chatFactory;
	}
	
	@Override
	public ChatFactory getChatFactory() {
		return chatFactory;
	}
	
	public Set<NamespacedKey> getModules() {
		return modules;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

}
