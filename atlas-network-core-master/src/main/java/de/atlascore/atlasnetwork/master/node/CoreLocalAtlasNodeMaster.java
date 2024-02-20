package de.atlascore.atlasnetwork.master.node;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.concurrent.future.Future;

public class CoreLocalAtlasNodeMaster extends CoreAtlasNodeMaster {

	public CoreLocalAtlasNodeMaster(UUID uuid, PublicKey key) {
		super(uuid, key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Future<? extends Server> getServer(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<? extends Proxy> getProxy(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Collection<? extends ServerGroup>> getServerGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deployServer(UUID uuid, ServerGroup group) {
		// TODO Auto-generated method stub
		
	}

}
