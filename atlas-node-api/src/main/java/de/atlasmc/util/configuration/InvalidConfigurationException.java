package de.atlasmc.util.configuration;

public class InvalidConfigurationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigurationSection config;
	
	public InvalidConfigurationException(String msg) {
		super(msg);
	}
	
	public InvalidConfigurationException(Throwable cause) {
		super(cause);
	}
	
	public InvalidConfigurationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public InvalidConfigurationException(String msg, ConfigurationSection config) {
		super(msg);
		this.config = config;
	}
	
	public ConfigurationSection getConfiguration() {
		return config;
	}

}
