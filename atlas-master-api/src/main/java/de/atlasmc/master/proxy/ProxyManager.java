package de.atlasmc.master.proxy;

import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;

public interface ProxyManager {
	
	Proxy getProxy(UUID uuid);
	
	ProxyConfig getProxyConfig(String name);
	
	boolean addProxyConfig(ProxyConfig config);
	
	boolean removeProxyConfig(ProxyConfig config);

}
