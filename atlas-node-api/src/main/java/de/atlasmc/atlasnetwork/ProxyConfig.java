package de.atlasmc.atlasnetwork;

import java.util.concurrent.atomic.AtomicReference;

public interface ProxyConfig {

	AtomicReference<ProxyConfig> DEFAULT_CONFIG = new AtomicReference<>();
	
	public static ProxyConfig getDefault() {
		return DEFAULT_CONFIG.get();
	}
	
	public static void setDefault(ProxyConfig newDefault) {
		DEFAULT_CONFIG.set(newDefault);
	}

}
