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
import java.util.concurrent.ExecutionException;

import de.atlascore.CoreLocalAtlasNode;
import de.atlasmc.Atlas;
import de.atlasmc.AtlasNode;
import de.atlasmc.LocalAtlasNode;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.command.ConsoleCommandSender;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.NodeBuilder;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.util.configuration.Configuration;

public class CoreNodeBuilder implements NodeBuilder {
	
	private Log log;
	private AtlasNetwork network;
	private File workdir;
	private ProtocolAdapter defaultProtocol;
	private List<ProtocolAdapter> protAdapter;
	private Map<String, NodeConfig> nodeConfigs;
	private Map<String, ProxyConfig> proxyConfigs;
	private Map<String, ServerGroup> serverGroups;
	private Set<NamespacedKey> modules;
	private ConsoleCommandSender console;
	private NodeServerManager serverManager;
	private final UUID uuid;
	
	public CoreNodeBuilder(UUID uuid, AtlasNetwork network, Log logger, File workdir, Configuration config, KeyPair keyPair) {
		this.uuid = uuid;
		this.network = network;
		this.log = Atlas.getLogger();
		this.workdir = workdir;
		this.nodeConfigs = new HashMap<>();
		this.proxyConfigs = new HashMap<>();
		this.serverGroups = new HashMap<>();
		log.info("Loading configuration...");
		this.modules = new HashSet<>();
		for (String group : config.getStringList("node-groups", List.of())) {
			if (nodeConfigs.containsKey(group))
				continue;
			NodeConfig cfg = AtlasNetwork.getNodeConfig(group).get();
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
	
	public NodeServerManager getServerManager() {
		return serverManager;
	}
	
	public CoreNodeBuilder setServerManager(NodeServerManager serverManager) {
		this.serverManager = serverManager;
		return this;
	}
	
	private void resolveServerGroups(Collection<String> serverGroups) {
		ServerManager smanager = AtlasNetwork.getServerManager();
		if (serverGroups.isEmpty())
			return;
		for (String groupKey : serverGroups) {
			if (this.serverGroups.containsKey(groupKey))
				continue;
			ServerGroup group = null;
			try {
				group = smanager.getServerGroup(groupKey).get();
			} catch (InterruptedException | ExecutionException e) {
				log.error("Error while loading server group!", e);
				continue;
			}
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
	public ProtocolAdapter getDefaultProtocol() {
		return defaultProtocol;
	}
	
	@Override
	public CoreNodeBuilder setDefaultProtocol(ProtocolAdapter defaultProtocol) {
		this.defaultProtocol = defaultProtocol;
		return this;
	}
	
	@Override
	public LocalAtlasNode build() {
		if (network == null)
			throw new IllegalStateException("Can not build Atlas without defined AtlasNetwork");
		LocalAtlasNode node = new CoreLocalAtlasNode(this);
		node.getProtocolAdapterHandler().setProtocol(defaultProtocol);
		AtlasNode.init(node, network);
		return node;
	}

	@Override
	public Collection<ProxyConfig> getProxyConfigs() {
		return proxyConfigs.values();
	}
	
	public Set<NamespacedKey> getModules() {
		return modules;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	public void setConsoleSender(ConsoleCommandSender console) {
		this.console = console;
	}
	
	public ConsoleCommandSender getConsoleSender() {
		return console;
	}
	
	public Log getLogger() {
		return log;
	}
	
	public File getWorkdir() {
		return workdir;
	}

	@Override
	public void clear() {
		// TODO clear
	}

}
