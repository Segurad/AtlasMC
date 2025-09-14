package de.atlasmc.util.configuration;

public class MemoryConfiguration extends MemoryConfigurationSection implements Configuration {

	private final ConfigurationSettings settings = new ConfigurationSettings();
	
	public MemoryConfiguration() {}

	public MemoryConfiguration(ConfigurationSection configuration) {
		addConfiguration(configuration);
	}

	@Override
	public ConfigurationSettings getSettings() {
		return settings;
	}
	
	
	
}
