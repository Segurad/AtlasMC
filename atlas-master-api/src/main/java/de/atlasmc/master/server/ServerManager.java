package de.atlasmc.master.server;

import java.util.Collection;
import java.util.UUID;

public interface ServerManager {

	ServerGroup getFallBack();

	ServerGroup getServerGroup(String name);

	Collection<ServerGroup> getServerGroups();
	
	Collection<ServerGroup> getServerGroups(Collection<String> names);
	
	ServerGroup createServerGroup(ServerGroupBuilder server);
	
	Server getServer(UUID uuid);

}
