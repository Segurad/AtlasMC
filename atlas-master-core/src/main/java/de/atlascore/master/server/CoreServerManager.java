package de.atlascore.master.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.log.Log;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerDeploymentMethod;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.master.server.ServerGroupBuilder;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.tick.Tickable;

public class CoreServerManager implements ServerManager, Tickable {

	private final Registry<ServerDeploymentMethod> deploymentMethods;
	private final ServerGroup fallBack;
	private final Map<String, CoreServerGroup> serverGroups;
	private final Map<UUID, Server> servers;
	
	public CoreServerManager() {
		this.serverGroups = new ConcurrentHashMap<>();
		this.servers = new ConcurrentHashMap<>();
		this.fallBack = null;
		this.deploymentMethods = Registries.getInstanceRegistry(ServerDeploymentMethod.class);
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
	
	private void deployServers(ServerGroup group) {
		ServerDeploymentMethod deployment = deploymentMethods.get(group.getDeploymentMethod());
		int serverCount = group.getServerCount();
		int serversNonFull = serverCount-group.getFullServerCount();
		int missingServers = Math.max(0, group.getMinServers()-serverCount); // provide min servers
		missingServers = Math.max(missingServers, group.getMinNonFullServers()-serversNonFull); // provide min non full
		// TODO calc missing server to satisfy slot requirement
		deployment.deploy(group, missingServers);
	}

	@Override
	public Collection<ServerGroup> getServerGroups(Collection<String> names) {
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

	@Override
	public ServerGroup createServerGroup(ServerGroupBuilder builder) {
		if (builder.getName() == null)
			throw new IllegalArgumentException("Name can not be null!");
		CoreServerGroup group = serverGroups.get(builder.getName());
		if (group != null)
			return group;
		group = new CoreServerGroup(this, builder);
		serverGroups.put(group.getName(), group);
		return group;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ServerGroup> getServerGroups() {
		Collection<? extends ServerGroup> view = serverGroups.values();
		return (Collection<ServerGroup>) view;
	}

	void addServer(CoreServer server) {
		servers.put(server.getServerID(), server);
	}

}
