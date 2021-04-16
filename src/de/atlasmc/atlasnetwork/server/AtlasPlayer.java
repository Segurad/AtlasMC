package de.atlasmc.atlasnetwork.server;

import de.atlasmc.atlasnetwork.proxy.Proxy;

public interface AtlasPlayer {
	
	public Proxy getMainProxy();
	public Proxy getProxy();
	public boolean sendToServer(Server server);
	public Server getCurrentServer();

}
