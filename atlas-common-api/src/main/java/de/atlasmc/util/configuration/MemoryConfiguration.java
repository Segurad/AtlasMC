package de.atlasmc.util.configuration;

public class MemoryConfiguration extends MemoryConfigurationSection implements Configuration {

	private final ConfigurationSettings settings;
	
	public MemoryConfiguration() {
		this(null, null);
	}
	
	public MemoryConfiguration(ConfigurationSettings settings) {
		this(settings, null);
	}
	
	public MemoryConfiguration(ConfigurationSection config) {
		this(null, config);
	}
	
	public MemoryConfiguration(ConfigurationSettings settings, ConfigurationSection config) {
		this.settings = settings == null ? new ConfigurationSettings() : settings;
		if (config != null)
			addConfiguration(config);
	}

	@Override
	public ConfigurationSettings getSettings() {
		return settings;
	}
	
	
	
}
