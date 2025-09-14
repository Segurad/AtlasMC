package de.atlascore.master.proxy;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.master.proxy.Proxy;
import de.atlasmc.master.proxy.ProxyManager;
import de.atlasmc.network.socket.SocketConfig;

public class CoreProxyManager implements ProxyManager {
	
	private Map<UUID, Proxy> proxies;
	private Map<String, SocketConfig> configs;

	public CoreProxyManager() {
		this.proxies = new ConcurrentHashMap<>();
		this.configs = new ConcurrentHashMap<>();
	}
	
	@Override
	public Proxy getProxy(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		return proxies.get(uuid);
	}

	@Override
	public SocketConfig getProxyConfig(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		return configs.get(name);
	}

	@Override
	public boolean addProxyConfig(SocketConfig config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		return configs.putIfAbsent(config.getName(), config) == null;
	}

	@Override
	public boolean removeProxyConfig(SocketConfig config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		return configs.remove(config.getName(), config);
	}

}
