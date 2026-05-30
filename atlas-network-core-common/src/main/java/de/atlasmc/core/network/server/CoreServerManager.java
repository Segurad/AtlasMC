package de.atlasmc.core.network.server;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.cache.MapCache;
import de.atlasmc.network.server.Server;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Wrapper for master server manager
 */
public class CoreServerManager implements ServerManager {
	
	private final MapCache<String, ServerGroup> groups;
	private final MapCache<UUID, Server> server;

	private final ConcurrentHashMap<String, CompletableFuture<ServerGroup>> futureGroups;
	private final ConcurrentHashMap<UUID, CompletableFuture<Server>> futureServers;
	
	public CoreServerManager() {
		this.groups = new MapCache<>();
		this.server = new MapCache<>();
		this.futureGroups = new ConcurrentHashMap<>();
		this.futureServers = new ConcurrentHashMap<>();
	}
	
	@Override
	public ServerGroup getFallBack() {
		return manager.getFallBack();
	}

	@Override
	public Future<ServerGroup> getServerGroup(String name) {
		ServerGroup group = manager.getServerGroup(name);
		return CompleteFuture.of(group);
	}

	@Override
	public Future<Collection<? extends ServerGroup>> getServerGroups(Collection<String> names) {
		Collection<? extends ServerGroup> groups = manager.getServerGroups(names);
		return CompleteFuture.of(groups);
	}

	@Override
	public Future<Server> getServer(UUID uuid) {
		Server server = manager.getServer(uuid);
		return CompleteFuture.of(server);
	}

	@Override
	public Future<Server> getServer(ServerGroup group, UUID uuid) {
		de.atlasmc.master.server.ServerGroup mgroup = manager.getServerGroup(group.getName());
		if (mgroup == null)
			return CompleteFuture.getNullFuture();
		Server server = mgroup.getServer(uuid);
		return CompleteFuture.of(server);
	}

}
