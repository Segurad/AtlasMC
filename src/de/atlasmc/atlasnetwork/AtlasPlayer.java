package de.atlasmc.atlasnetwork;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;

public interface AtlasPlayer {
	
	public Proxy getMainProxy();
	public Proxy getProxy();
	public boolean sendToServer(Server server);
	public Server getCurrentServer();

}
