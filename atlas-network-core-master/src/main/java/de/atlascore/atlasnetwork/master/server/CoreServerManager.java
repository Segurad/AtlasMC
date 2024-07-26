package de.atlascore.atlasnetwork.master.server;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Wrapper for master server manager
 */
public class CoreServerManager implements ServerManager {

	private final de.atlasmc.master.server.ServerManager manager;
	
	public CoreServerManager(de.atlasmc.master.server.ServerManager manager) {
		this.manager = manager;
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
	public Future<Collection<? extends ServerGroup>> getServerGroups(String... name) {
		Collection<? extends ServerGroup> groups = manager.getServerGroups(name);
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
