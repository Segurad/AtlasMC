package de.atlasmc.io.atlasnetwork.server;

import de.atlasmc.io.atlasnetwork.Proxy;

public interface AtlasPlayer {
	
	public Proxy getMainProxy();
	public Proxy getProxy();
	public boolean sendToServer(Server server);
	public Server getCurrentServer();

}
