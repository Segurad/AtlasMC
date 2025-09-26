package de.atlasmc.io.connection;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;

public class ConnectionConfig implements ConfigurationSerializable {

	private String name;
	private int timeout;
	private int rateLimit;
	private boolean authentication;
	private boolean enableCompression;
	private int compressionThreshold;
	private int compressionBufferSize;
	
	public ConnectionConfig(ConfigurationSection config) {
		this.name = config.getString("name");
		this.timeout = config.getInt("timeout");
		this.rateLimit = config.getInt("rate-limit");
		this.authentication = config.getBoolean("authentication");
		this.enableCompression = config.getBoolean("enable-compression");
		this.compressionThreshold = config.getInt("compression-threshold");
		this.compressionBufferSize = config.getInt("compression-buffer-size");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}

	public boolean hasAuthentication() {
		return authentication;
	}

	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}

	public boolean isEnableCompression() {
		return enableCompression;
	}

	public void setEnableCompression(boolean enableCompression) {
		this.enableCompression = enableCompression;
	}

	public int getCompressionThreshold() {
		return compressionThreshold;
	}

	public void setCompressionThreshold(int compressionThreshold) {
		this.compressionThreshold = compressionThreshold;
	}

	public int getCompressionBufferSize() {
		return compressionBufferSize;
	}

	public void setCompressionBufferSize(int compressionBufferSize) {
		this.compressionBufferSize = compressionBufferSize;
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T config) {
		config.set("timeout", timeout);
		config.set("rate-limit", rateLimit);
		config.set("authentication", authentication);
		config.set("enable-compression", enableCompression);
		config.set("compression-threshold", compressionThreshold);
		config.set("compression-buffer-size", compressionBufferSize);
		return config;
	}

}
