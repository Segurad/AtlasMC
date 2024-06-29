package de.atlascore.atlasnetwork.master.node;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreLocalAtlasNodeMaster extends CoreAtlasNodeMaster {

	public CoreLocalAtlasNodeMaster(UUID uuid, PublicKey key) {
		super(uuid, key);
	}

	@Override
	public Future<? extends Server> getServer(UUID uuid) {
		return CompleteFuture.of(Atlas.getServerManager().getServer(uuid));
	}

	@Override
	public Future<? extends Proxy> getProxy(UUID uuid) {
		return Atlas.getAtlas().getProxy(uuid);
	}

	@Override
	public Future<Collection<? extends ServerGroup>> getServerGroups() {
		// TODO Auto-c
		return null;
	}

	@Override
	public void tick() {}

	@Override
	public void deployServer(ServerGroup group) {
		Atlas.getServerManager().deployServer(group);
	}

}
