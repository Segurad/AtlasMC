package de.atlasmc.proxy;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;

public interface ProxyManager {
	
	LocalProxy getProxy(UUID uuid);
	
	boolean registerProxy(LocalProxy proxy);
	
	boolean unregisterProxy(LocalProxy proxy);
	
	Collection<LocalProxy> getProxies();
	
	LocalProxy createProxy(ProxyConfig config);

}
