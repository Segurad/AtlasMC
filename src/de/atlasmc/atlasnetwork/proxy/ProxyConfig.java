package de.atlasmc.atlasnetwork.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.atlasmc.util.configuration.ConfigurationSection;

public class ProxyConfig implements Cloneable {
	
	private int port;
	private boolean auth;
	private boolean internal;
	private boolean matchDomains;
	private List<Pattern> domains;
	private boolean compression;
	private int compressionThreshold;
	private int compressionBufferSize;
	private int keepAliveTimeout;
	private int rateLimit;
	private int chunksPerTick;
	
	public ProxyConfig() {}
	
	public ProxyConfig(String name, ConfigurationSection config) {
		this.port = config.getInt("port", -1);
		this.internal = config.getString("mode").equals("internal");
		ConfigurationSection playerCfg = config.getConfigurationSection("player-connections");
		this.auth = playerCfg.getBoolean("authentication");
		this.matchDomains = playerCfg.getBoolean("match-domains");
		List<String> domains = playerCfg.getStringList("domain-whitelist");
		this.domains = new ArrayList<>(domains.size());
		for (String domain : domains)
			this.domains.add(Pattern.compile(domain));
		this.domains = List.copyOf(this.domains);
		compression = playerCfg.getBoolean("enable-compression");
		compressionThreshold = playerCfg.getInt("compression-threshold");
		compressionBufferSize = playerCfg.getInt("compression-buffer-size");
		keepAliveTimeout = playerCfg.getInt("keep-alive-timeout", 30);
		rateLimit = playerCfg.getInt("rate-limit");
		chunksPerTick = playerCfg.getInt("chunks-per-tick");
	}
	
	public int getPort() {
		return port;
	}
	
	public boolean getPlayerAuthentication() {
		return auth;
	}
	
	public boolean isInternal() {
		return internal;
	}
	
	public boolean getMatchDomains() {
		return matchDomains;
	}
	
	public List<Pattern> getDomains() {
		return domains;
	}
	
	public boolean getEnableCompression() {
		return compression;
	}
	
	public int getCompressionThreshold() {
		return compressionThreshold;
	}
	
	public int getCompressionBufferSize() {
		return compressionBufferSize;
	}
	
	public int getRateLimit() {
		return rateLimit;
	}
	
	public int getChunksPerTick() {
		return chunksPerTick;
	}
	
	public int getKeepAliveTimeout() {
		return keepAliveTimeout;
	}
	
	@Override
	public ProxyConfig clone() {
		try {
			return (ProxyConfig) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

}
