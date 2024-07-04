package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Represents a AtlasNode
 *
 */
public interface AtlasNode {
	
	Future<? extends Server> getServer(UUID uuid);
	
	Future<? extends Proxy> getProxy(UUID uuid);
	
	Future<Collection<? extends ServerGroup>> getServerGroups(); 
	
	NodeStatus getStatus();
	
	PublicKey getPublicKey();

	UUID getUUID();
	
	public static enum NodeStatus {
		OFFLINE,
		STARTING,
		ONLINE
	}
	
}
