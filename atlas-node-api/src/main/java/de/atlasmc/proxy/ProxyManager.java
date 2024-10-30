package de.atlasmc.proxy;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.util.concurrent.future.Future;

public interface ProxyManager {
	
	LocalProxy getProxy(UUID uuid);
	
	boolean registerProxy(LocalProxy proxy);
	
	boolean unregisterProxy(LocalProxy proxy);
	
	Collection<LocalProxy> getProxies();
	
	Future<LocalProxy> createProxy(ProxyConfig config);
	
	Future<LocalProxy> createProxy(NamespacedKey config);

}
