package de.atlascore.proxy;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.io.netty.channel.ChannelInitHandler;
import de.atlasmc.Atlas;
import de.atlasmc.AtlasNode;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.log.Log;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.proxy.LocalProxyFactory;
import de.atlasmc.proxy.ProxyManager;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;

public class CoreProxyManager implements ProxyManager {
	
	private static final int MAX_RANDOM_PORT_TRIES = 10;
	private static final int RANDOM_PORT_RANGE_START = 25000;
	private static final int RANDOM_PORT_RANGE_END = 35000;
	
	private Map<UUID, LocalProxy> proxies;
	
	public CoreProxyManager() {
		proxies = new ConcurrentHashMap<>();
	}
	
	@Override
	public LocalProxy getProxy(UUID uuid) {
		return proxies.get(uuid);
	}

	@Override
	public boolean registerProxy(LocalProxy proxy) {
		return proxies.put(proxy.getUUID(), proxy) != proxy;
	}

	@Override
	public boolean unregisterProxy(LocalProxy proxy) {
		return proxies.remove(proxy.getUUID(), proxy);
	}

	@Override
	public Collection<LocalProxy> getProxies() {
		return proxies.values();
	}

	@Override
	public LocalProxy createProxy(ProxyConfig config) {
		Log log = Atlas.getLogger();
		Registry<LocalProxyFactory> proxyFactories = Registries.getInstanceRegistry(LocalProxyFactory.class);
		final int portRange = RANDOM_PORT_RANGE_END - RANDOM_PORT_RANGE_START;
		NamespacedKey proxyKey = config.getFactory();
		LocalProxyFactory factory = null;
		if (proxyKey != null) {
			factory = proxyFactories.get(proxyKey);
			if (factory == null)
				log.warn("No proxy factory found with key: {}", proxyKey);
		}
		if (factory == null) {
			factory = proxyFactories.getDefault();
			if (factory == null)
				log.warn("No default proxy factory found!");
			return null;
		}
		int port = config.getPort();
		if (port == -1) {
			int maxTries = MAX_RANDOM_PORT_TRIES;
			Random rand = new Random();
			while (maxTries > 0) {
				maxTries--;
				if (rand == null)
					rand = new Random();
				port = RANDOM_PORT_RANGE_START + rand.nextInt(portRange);
				if (isPortAvailable(port)) {
					break;
				}
				port = -1;
			}
			if (port == -1) {
				log.warn("Unable to fetch random open port! ({} tries)", MAX_RANDOM_PORT_TRIES);
				return null;
			}
		}
		UUID proxyUUID = UUID.randomUUID();
		LocalProxy proxy = factory.createProxy(proxyUUID, AtlasNode.getAtlas(), port, config);
		proxy.setChannelInitHandler(new ChannelInitHandler(proxy));
		proxy.run();
		registerProxy(proxy);
		return proxy;
	}
	
	private static boolean isPortAvailable(int port) {
		Socket s = null;
	    try {
	        s = new Socket("localhost", port);
	        return false; 
	    } catch (IOException e) {
	        return true;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {}
	        }
	    }
	}

}
