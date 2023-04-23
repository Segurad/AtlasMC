package de.atlascore.plugin;

import java.io.File;
import java.security.KeyPair;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTML.Tag;

import org.slf4j.Logger;

import de.atlasmc.Atlas;
import de.atlasmc.Material;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.entity.EntityType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.plugin.CoremodulPlugin;
import de.atlasmc.plugin.NodeBuilder;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.TickingThread;
import de.atlasmc.util.configuration.Configuration;

public class CoreNodeBuilder implements NodeBuilder {
	
	private final KeyPair keyPair;
	private final Logger log;
	private final AtlasNetwork network;
	private Scheduler scheduler;
	private final File workdir;
	private ProtocolAdapter defaultProtocol;
	private List<ProtocolAdapter> protAdapter;
	private Map<String, NodeConfig> nodeConfigs;
	private Map<String, ProxyConfig> proxyConfigs;
	private Map<String, ServerGroup> serverGroups;
	private PluginManager pluginManager;
	private TickingThread mainThread;
	
	public CoreNodeBuilder(AtlasNetwork network, Logger logger, File workdir, Configuration config, KeyPair keyPair) {
		this.network = network;
		this.log = logger;
		this.keyPair = keyPair;
		this.workdir = workdir;
		this.nodeConfigs = new HashMap<>();
		this.proxyConfigs = new HashMap<>();
		this.serverGroups = new HashMap<>();
		log.info("Loading configuration...");
		for (String group : config.getStringList("node-groups", List.of())) {
			if (nodeConfigs.containsKey(group))
				continue;
			NodeConfig cfg = network.getNodeConfig(group);
			if (cfg == null) {
				log.warn("Unable to load node config: {}", group);
				continue;
			}
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
		for (String groupKey : serverGroups) {
			if (this.serverGroups.containsKey(groupKey))
				continue;
			ServerGroup group = network.getServerGroup(groupKey);
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
	
	public void initByCoreModuls(List<CoremodulPlugin> plugins) {
		List<Class<?>> classes = List.of(
				Material.class,
				EntityType.class,
				Tag.class,
				ContainerFactory.class
		);
		for (Class<?> clazz : classes)
			for (CoremodulPlugin plugin : plugins)
				plugin.initBaseFields(clazz);
		for (CoremodulPlugin plugin : plugins) {
			plugin.addTypes();
			plugin.initNode(this);
		}
	}
	
	public TickingThread getMainThread() {
		return mainThread;
	}
	
	public void setMainThread(TickingThread mainThread) {
		this.mainThread = mainThread;
	}
	
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public void setPluginManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public ProtocolAdapter getDefaultProtocol() {
		return defaultProtocol;
	}
	
	public void setDefaultProtocol(ProtocolAdapter defaultProtocol) {
		this.defaultProtocol = defaultProtocol;
	}
	
	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	public LocalAtlasNode build(boolean initAtlas) {
		if (network == null)
			throw new IllegalStateException("Can not build Atlas without defined AtlasNetwork");
		LocalAtlasNode node = new LocalAtlasNode(log, scheduler, workdir, pluginManager, mainThread, keyPair);
		node.getProtocolAdapterHandler().setProtocol(defaultProtocol);
		if (initAtlas)
			Atlas.init(node, network);
		return node;
	}

	public Collection<ProxyConfig> getProxyConfigs() {
		return proxyConfigs.values();
	}

}
