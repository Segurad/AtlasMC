package de.atlasmc.master.server;

import de.atlasmc.NamespacedKey;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.util.Builder;
import de.atlasmc.util.configuration.ConfigurationSection;

public class ServerGroupBuilder implements Builder<ServerGroup> {
	
	private NamespacedKey deploymentMethod;
	private long memoryThreshold;
	private float memoryUtilisation;
	private boolean internal = true;
	private ServerConfig serverConfig;
	private int maxServers;
	private int maxNonFullServers;
	private int minServers;
	private int minNonFullServers;
	private int newServerDelay;
	private float newServerOnUserLoad;
	private boolean isMaintenance;
	private String name;
	
	public ServerGroupBuilder() {}
	
	public ServerGroupBuilder(ConfigurationSection config) {
		setConfiguration(config);
	}
	
	public ServerGroupBuilder setConfiguration(ConfigurationSection config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		name = config.getString("name");
		minServers = config.getInt("min-server");
		maxServers = config.getInt("max-server");
		newServerDelay = config.getInt("new-server-delay");
		newServerOnUserLoad = config.getFloat("new-server-on-user-load");
		maxNonFullServers = config.getInt("max-non-full-server");
		minNonFullServers = config.getInt("min-non-full-server");
		isMaintenance = config.getBoolean("maintenance");
		ConfigurationSection serverCfg = config.getConfigurationSection("server-config");
		serverConfig = new ServerConfig(serverCfg);
		ConfigurationSection selectorCfg = config.getConfigurationSection("node-selector");
		deploymentMethod = NamespacedKey.of(selectorCfg.getString("method", "atlas-master:server/default_deployment"));
		internal = selectorCfg.getBoolean("internal", true);
		memoryThreshold = selectorCfg.getInt("memory-threshold");
		memoryUtilisation = selectorCfg.getFloat("memory-utilisation");
		return this;
	}
	
	public NamespacedKey getDeploymentMethod() {
		return deploymentMethod;
	}
	
	public ServerGroupBuilder setDeploymentMethod(NamespacedKey deploymentMethod) {
		this.deploymentMethod = deploymentMethod;
		return this;
	}
	
	public long getMemoryThreshold() {
		return memoryThreshold;
	}
	
	public ServerGroupBuilder setMemoryThreshold(long memoryTheshold) {
		this.memoryThreshold = memoryTheshold;
		return this;
	}
	
	public float getMemoryUtilisation() {
		return memoryUtilisation;
	}
	
	public ServerGroupBuilder setMemoryUtilisation(float memoryUtilisation) {
		this.memoryUtilisation = memoryUtilisation;
		return this;
	}
	
	public boolean isInternal() {
		return internal;
	}
	
	public ServerGroupBuilder setInternal(boolean internal) {
		this.internal = internal;
		return this;
	}
	
	public ServerConfig getServerConfig() {
		return serverConfig;
	}
	
	public ServerGroupBuilder setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
		return this;
	}
	
	public int getMaxServers() {
		return maxServers;
	}
	
	public ServerGroupBuilder setMaxServers(int maxServers) {
		this.maxServers = maxServers;
		return this;
	}
	
	public int getMaxNonFullServers() {
		return maxNonFullServers;
	}
	
	public ServerGroupBuilder setMaxNonFullServers(int maxNonFullServers) {
		this.maxNonFullServers = maxNonFullServers;
		return this;
	}
	
	public int getMinServers() {
		return minServers;
	}
	
	public ServerGroupBuilder setMinServers(int minServers) {
		this.minServers = minServers;
		return this;
	}
	
	public int getMinNonFullServers() {
		return minNonFullServers;
	}
	
	public ServerGroupBuilder setMinNonFullServers(int minNonFullServers) {
		this.minNonFullServers = minNonFullServers;
		return this;
	}
	
	public int getNewServerDelay() {
		return newServerDelay;
	}
	
	public ServerGroupBuilder setNewServerDelay(int newServerDelay) {
		this.newServerDelay = newServerDelay;
		return this;
	}
	
	public float getNewServerOnUserLoad() {
		return newServerOnUserLoad;
	}
	
	public ServerGroupBuilder setNewServerOnUserLoad(float newServerOnUserLoad) {
		this.newServerOnUserLoad = newServerOnUserLoad;
		return this;
	}
	
	public boolean isMaintenance() {
		return isMaintenance;
	}
	
	public ServerGroupBuilder setMaintenance(boolean isMaintenance) {
		this.isMaintenance = isMaintenance;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public ServerGroupBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public ServerGroup build() {
		return AtlasMaster.getServerManager().createServerGroup(this);
	}

	@Override
	public void clear() {
		name = null;
		minServers = 0;
		maxServers = 0;
		newServerDelay = 0;
		newServerOnUserLoad = 0;
		maxNonFullServers = 0;
		minNonFullServers = 0;
		isMaintenance = false;
		serverConfig = null;
		deploymentMethod = null;
		internal = true;
		memoryThreshold = 0;
		memoryUtilisation = 0;
	}

}
