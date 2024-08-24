package de.atlascore.atlasnetwork.master;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.atlasnetwork.CoreAbstractAtlasNetworkHandler;
import de.atlascore.atlasnetwork.CoreAbstractAtlasNetworkHandlerBuilder;
import de.atlascore.atlasnetwork.master.node.CoreNodeManager;
import de.atlascore.atlasnetwork.master.server.CoreServerManager;
import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.AtlasNetworkHandler;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.util.Builder;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreAtlasNetworkHandler extends CoreAbstractAtlasNetworkHandler {
	
	public CoreAtlasNetworkHandler(CoreAtlasNetworkHandlerBuilder builder) {
		super(builder);
	}
	
	@Override
	public ServerGroup getFallBack() {
		return AtlasMaster.getServerManager().getFallBack();
	}

	@Override
	public int getOnlinePlayerCount() {
		return onlineplayers;
	}
	
	@Override
	public int getMaxPlayers() {
		return maxplayers;
	}

	@Override
	public NetworkInfo getNetworkInfo() {
		return info;
	}

	@Override
	public NetworkInfo getNetworkInfoMaintenance() {
		return infoMaintenance;
	}

	@Override
	public boolean isMaintenance() {
		return maintenance;
	}

	@Override
	public Future<NodeConfig> getNodeConfig(String name) {
		return null;
	}

	@Override
	public Future<ProxyConfig> getProxyConfig(String name) {
		return null;
	}

	@Override
	public Collection<Repository> getRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
