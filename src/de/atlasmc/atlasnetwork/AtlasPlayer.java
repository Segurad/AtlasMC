package de.atlasmc.atlasnetwork;

import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;

public interface AtlasPlayer {
	
	public Proxy getMainProxy();
	public Proxy getProxy();
	public boolean sendToServer(Server server);
	public Server getCurrentServer();
	public UUID getInteranlUUID();
	public boolean hasInternalUUID();
	public void setInternalUUID(UUID uuid);

}
