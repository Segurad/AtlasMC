package de.atlasmc.util.configuration;

public class MemoryConfiguration extends MemoryConfigurationSection implements Configuration {

	public MemoryConfiguration() {}

	public MemoryConfiguration(ConfigurationSection configuration) {
		copySection(this, configuration);
	}
	
	private void copySection(ConfigurationSection parent, ConfigurationSection section) {
		if (section == null)
			return;
		section.getValues().forEach((key, value) -> {
			if (value instanceof ConfigurationSection) {
				ConfigurationSection newSection = parent.createSection(key);
				copySection(newSection, (ConfigurationSection) value);
			} else {
				parent.set(key, value);
			}
		});
	}
	
}
