package de.atlasmc.network.server;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.util.concurrent.future.Future;

public interface ServerManager {
	
	ServerGroup getFallBack();
	
	Future<ServerGroup> getServerGroup(String name);
	
	Future<Collection<? extends ServerGroup>> getServerGroups(Collection<String> names);
	
	Future<Server> getServer(UUID uuid);
	
	Future<Server> getServer(ServerGroup group, UUID uuid);

}
