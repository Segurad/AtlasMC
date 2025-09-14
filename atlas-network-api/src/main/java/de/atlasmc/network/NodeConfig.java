package de.atlasmc.network;

import java.util.Collection;
import java.util.List;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;

public class NodeConfig implements ConfigurationSerializable {
	
	private final String name;
	private final Collection<String> proxies;
	private final Collection<String> serverGroups;
	private final Collection<String> coreModules;
	
	public NodeConfig(ConfigurationSection config) {
		this.name = config.getString("name");
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

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("name", name);
		configuration.set("proxies", proxies);
		configuration.set("server-groups", serverGroups);
		configuration.set("core-modules", coreModules);
		return configuration;
	}

}
