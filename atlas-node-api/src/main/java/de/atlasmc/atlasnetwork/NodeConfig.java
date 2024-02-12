package de.atlasmc.atlasnetwork;

import java.util.Collection;
import java.util.List;

import de.atlasmc.util.configuration.ConfigurationSection;

public class NodeConfig {
	
	private final String name;
	private final Collection<String> proxies;
	private final Collection<String> serverGroups;
	private final Collection<String> coreModules;
	
	public NodeConfig(String name, ConfigurationSection config) {
		this.name = name;
		proxies = List.copyOf(config.getStringList("proxies", List.of()));
		serverGroups = List.copyOf(config.getStringList("server-groups", List.of()));
		coreModules = List.copyOf(config.getStringList("core-modules", List.of()));
	}
	
	public Collection<String> getProxies() {
		return proxies;
	}
	
	public Collection<String> getServerGroups() {
		return serverGroups;
	}
	
	public Collection<String> getCoreModules() {
		return coreModules;
	}
	
	public String getName() {
		return name;
	}

}
