package de.atlasmc.util.configuration;

public class ConfigurationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigurationSection config;
	
	public ConfigurationException(String msg) {
		super(msg);
	}
	
	public ConfigurationException(Throwable cause) {
		super(cause);
	}
	
	public ConfigurationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ConfigurationException(String msg, ConfigurationSection config) {
		super(msg);
		this.config = config;
	}
	
	public ConfigurationException(String msg, Throwable cause, ConfigurationSection config) {
		super(msg, cause);
		this.config = config;
	}
	
	public ConfigurationSection getConfiguration() {
		return config;
	}

}
