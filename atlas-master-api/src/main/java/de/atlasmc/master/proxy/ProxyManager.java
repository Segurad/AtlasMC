package de.atlasmc.master.proxy;

import java.util.UUID;

import de.atlasmc.network.socket.SocketConfig;

public interface ProxyManager {
	
	Proxy getProxy(UUID uuid);
	
	SocketConfig getProxyConfig(String name);
	
	boolean addProxyConfig(SocketConfig config);
	
	boolean removeProxyConfig(SocketConfig config);

}
