package de.atlascore.atlasnetwork.master;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.server.ServerGroup;

public class CoreServerManager {

	private final Map<String, ServerGroup> serverGroups;
	
	public CoreServerManager(Map<String, ServerGroup> serverGroups) {
		this.serverGroups = new ConcurrentHashMap<>(serverGroups);
	}

	public ServerGroup getServerGroup(String name) {
		return serverGroups.get(name);
	}

}
