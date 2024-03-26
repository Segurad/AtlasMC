package de.atlascore.atlasnetwork.master.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.atlasnetwork.master.CoreAtlasMaster;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.log.Log;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

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
	public Future<ServerGroup> getServerGroup(String name) {
		ServerGroup group = serverGroups.get(name);
		if (group == null)
			return CompleteFuture.getNullFuture();
		return new CompleteFuture<>(group);
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
		Log logger = CoreAtlasMaster.getLogger();
		for (CoreServerGroup serverGroup : serverGroups.values()) {
			serverGroup.tick();
			if (serverGroup.isUnsatisfied()) {
				logger.debug("Server group unsatisfied: {}", serverGroup.getName());
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

	@Override
	public Future<Collection<ServerGroup>> getServerGroups(String... names) {
		ArrayList<ServerGroup> groups = new ArrayList<>();
		for (String name : names) {
			ServerGroup group = this.serverGroups.get(name);
			if (group != null)
				groups.add(group);
		}
		return new CompletableFuture<>();
	}

}
