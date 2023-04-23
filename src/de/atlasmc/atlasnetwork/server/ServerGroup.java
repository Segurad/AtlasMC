package de.atlasmc.atlasnetwork.server;

import java.util.Collection;
import java.util.List;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.util.configuration.ConfigurationSection;

public class ServerGroup {
	
	private String name;
	private ServerConfig config;
	private int maxServers;
	private int minServers;
	private int newServerDelay;
	private float newServerOnUserLoad;
	private boolean maintenance;
	
	public ServerGroup() {}
	
	public ServerGroup(String name, ConfigurationSection config) {
		this.name = name;
		minServers = config.getInt("min-server");
		maxServers = config.getInt("max-server");
		newServerDelay = config.getInt("new-server-delay");
		newServerOnUserLoad = config.getFloat("new-server-on-user-load");
		maintenance = config.getBoolean("maintenance");
		ConfigurationSection serverCfg = config.getConfigurationSection("server-config");
		this.config = new ServerConfig(serverCfg);
	}
	
	// config
	
	public ServerConfig getServerConfig() {
		return config;
	}
	
	public int getMaxServers() {
		return maxServers;
	}
	
	public int getMinServers() {
		return minServers;
	}
	
	public int getNewServerDelay() {
		return newServerDelay;
	}
	
	public float getNewServerOnUserLoad() {
		return newServerOnUserLoad;
	}
	
	public boolean isMaintenance() {
		return maintenance;
	}
	
	public String getName() {
		return name;
	}
	
	// runtime
	
	public Collection<Server> getServers() {
		return List.of();
	}
	
	public int getPlayerCount() {
		return 0;
	}
	
	public Collection<AtlasPlayer> getPlayers() {
		return List.of();
	}

}
