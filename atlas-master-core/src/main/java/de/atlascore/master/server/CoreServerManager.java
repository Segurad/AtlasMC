package de.atlascore.master.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerDeploymentMethod;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.tick.Tickable;

public class CoreServerManager implements ServerManager, Tickable {

	private final Map<NamespacedKey, ServerDeploymentMethod> deploymentMethods;
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
		Log logger = AtlasMaster.getLogger();
		for (CoreServerGroup serverGroup : serverGroups.values()) {
			serverGroup.tick();
			if (serverGroup.isUnsatisfied()) {
				logger.debug("Server group unsatisfied: {}", serverGroup.getName());
				deployServers(serverGroup);
				serverGroup.setTimeout(60);
			}
		}
	}
	
	@Override
	public void addDeploymentMethod(NamespacedKey key, ServerDeploymentMethod method) {
		this.deploymentMethods.put(key, method);
	}
	
	private void deployServers(CoreServerGroup group) {
		ServerDeploymentMethod deployment = deploymentMethods.get(group.getDeploymentMethod());
		int serverCount = group.getServerCount();
		int serversNonFull = serverCount-group.getFullServerCount();
		int missingServers = Math.max(0, group.getMinServers()-serverCount); // provide min servers
		missingServers = Math.max(missingServers, group.getMinNonFullServers()-serversNonFull); // provide min non full
		// TODO calc missing server to satisfy slot requirement
		deployment.deploy(group, missingServers);
	}

	@Override
	public Collection<ServerGroup> getServerGroups(String... names) {
		ArrayList<ServerGroup> groups = null;
		for (String name : names) {
			ServerGroup group = this.serverGroups.get(name);
			if (group == null)
				continue;
			if (groups == null)
				groups = new ArrayList<>();
			groups.add(group);
		}
		return groups != null ? groups : List.of();
	}

}
