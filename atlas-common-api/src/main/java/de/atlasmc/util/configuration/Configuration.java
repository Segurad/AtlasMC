package de.atlasmc.util.configuration;

/**
 * Represents the root of a configuration
 */
public interface Configuration extends ConfigurationSection {

	ConfigurationSettings getSettings();
	
	public static ConfigurationSection of() {
		return EmptyConfiguration.INSTANCE;
	}

}
