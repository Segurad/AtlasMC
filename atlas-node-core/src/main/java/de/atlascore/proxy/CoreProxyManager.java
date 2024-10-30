package de.atlascore.proxy;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.proxy.ProxyManager;
import de.atlasmc.util.concurrent.future.Future;

public class CoreProxyManager implements ProxyManager {

	@Override
	public LocalProxy getProxy(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean registerProxy(LocalProxy proxy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unregisterProxy(LocalProxy proxy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<LocalProxy> getProxies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<LocalProxy> createProxy(ProxyConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<LocalProxy> createProxy(NamespacedKey config) {
		// TODO Auto-generated method stub
		return null;
	}

}
