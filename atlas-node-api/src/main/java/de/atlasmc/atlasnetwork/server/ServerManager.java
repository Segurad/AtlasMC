package de.atlasmc.atlasnetwork.server;

import java.util.Collection;

import de.atlasmc.util.concurrent.future.Future;

public interface ServerManager {
	
	ServerGroup getFallBack();
	
	Future<ServerGroup> getServerGroup(String name);
	
	Future<Collection<ServerGroup>> getServerGroups(String... name);

}
