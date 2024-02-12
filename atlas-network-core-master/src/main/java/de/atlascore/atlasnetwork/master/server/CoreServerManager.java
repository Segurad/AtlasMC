package de.atlascore.atlasnetwork.master.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.tick.Tickable;

public class CoreServerManager implements ServerManager, Tickable {

	private final Map<NamespacedKey, CoreServerDeploymentMethod> deploymentMethods;
	private final ServerGroup fallBack;
	private final Map<String, CoreServerGroup> serverGroups;
	private final Map<UUID, Server> servers;
	
	public CoreServerManager(ServerGroup fallBack, Map<String, CoreServerGroup> serverGroups) {
		this.serverGroups = new ConcurrentHashMap<>(serverGroups);
		this.servers = new ConcurrentHashMap<>();
		this.fallBack = fallBack;
		this.deploymentMethods = new ConcurrentHashMap<>();
	}

	@Override
	public ServerGroup getServerGroup(String name) {
		return serverGroups.get(name);
	}
	
	public Server getServer(UUID uuid) {
		return servers.get(uuid);
	}

	@Override
	public ServerGroup getFallBack() {
		return fallBack;
	}

	@Override
	public void tick() {
		for (CoreServerGroup serverGroup : serverGroups.values()) {
			serverGroup.tick();
			if (serverGroup.isUnsatisfied()) {
				deployServers(serverGroup);
				serverGroup.setTimeout(60);
			}
		}
	}
	
	public void addDeploymentMethod(NamespacedKey key, CoreServerDeploymentMethod method) {
		this.deploymentMethods.put(key, method);
	}
	
	private void deployServers(CoreServerGroup group) {
		CoreServerDeploymentMethod deployment = deploymentMethods.get(group.getDeploymentMethod());
		int serverCount = group.getServerCount();
		int serversNonFull = serverCount-group.getFullServerCount();
		int missingServers = Math.max(0, group.getMinServers()-serverCount); // provide min servers
		missingServers = Math.max(missingServers, group.getMinNonFullServers()-serversNonFull); // provide min non full
		// TODO calc missing server to satisfy slot requirement
		deployment.deploy(group, missingServers);
	}

}
