package de.atlasmc.util.configuration;

/**
 * Represents a Object that can be deserialized from a {@link ConfigurationSection}
 * and serialized as {@link ConfigurationSection}
 * Every class implementing {@link ConfigurationSerializeable} is expected to have a constructor accepting
 * a {@link ConfigurationSection} as only parameter
 */
public interface ConfigurationSerializeable {
	
	default ConfigurationSection toConfiguration() {
		return toConfiguration(new MemoryConfiguration());
	}
	
	<T extends ConfigurationSection> T toConfiguration(T configuration);
	
}
