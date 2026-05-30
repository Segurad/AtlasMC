package de.atlasmc.network;

import java.util.List;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;

public class NodeConfig implements ConfigurationSerializable {
	
	private final String name;
	private final List<String> serverGroups;
	private final List<String> coreModules;
	
	public NodeConfig(ConfigurationSection config) {
		this.name = config.getString("name");
		serverGroups = List.copyOf(config.getStringList("server-groups", List.of()));
		coreModules = List.copyOf(config.getStringList("core-modules", List.of()));
	}
	
	public List<String> getServerGroups() {
		return serverGroups;
	}
	
	public List<String> getCoreModules() {
		return coreModules;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("name", name);
		configuration.set("server-groups", serverGroups);
		configuration.set("core-modules", coreModules);
		return configuration;
	}

}
