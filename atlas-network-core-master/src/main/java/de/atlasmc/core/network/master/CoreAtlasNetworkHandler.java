package de.atlasmc.core.network.master;

import java.util.ArrayList;
import java.util.Collection;

import de.atlasmc.core.network.CoreAbstractAtlasNetworkHandler;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.socket.SocketManager;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.network.NodeConfig;
import de.atlasmc.network.server.ServerGroup;
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
		return AtlasMaster.getOnlinePlayerCount();
	}
	
	@Override
	public int getMaxPlayers() {
		return AtlasMaster.getMaxPlayers();
	}

	@Override
	public NetworkInfo getNetworkInfo() {
		return AtlasMaster.getNetworkInfo();
	}

	@Override
	public NetworkInfo getNetworkInfoMaintenance() {
		return AtlasMaster.getNetworkInfoMaintenance();
	}

	@Override
	public boolean isMaintenance() {
		return AtlasMaster.isMaintenance();
	}

	@Override
	public Future<NodeConfig> getNodeConfig(String name) {
		return CompleteFuture.of(AtlasMaster.getNodeManager().getNodeConfig(name));
	}
	
	@Override
	public Future<Collection<NodeConfig>> getNodeConfigs(Collection<String> names) {
		if (names.isEmpty())
			return CompleteFuture.getEmptyListFuture();
		NodeManager nodeManager = AtlasMaster.getNodeManager();
		ArrayList<NodeConfig> configs = new ArrayList<>(names.size());
		for (String name : names) {
			NodeConfig cfg = nodeManager.getNodeConfig(name);
			if (cfg == null)
				continue;
			configs.add(cfg);
		}
		return CompleteFuture.of(configs);
	}

	@Override
	public Future<SocketConfig> getSocketConfig(String name) {
		return CompleteFuture.of(AtlasMaster.getSocketManager().getSocketConfig(name));
	}
	
	@Override
	public Future<Collection<SocketConfig>> getSocketConfigs(Collection<String> names) {
		if (names.isEmpty())
			return CompleteFuture.getEmptyListFuture();
		SocketManager proxyManager = AtlasMaster.getSocketManager();
		ArrayList<SocketConfig> configs = new ArrayList<>(names.size());
		for (String name : names) {
			SocketConfig cfg = proxyManager.getSocketConfig(name);
			if (cfg == null)
				continue;
			configs.add(cfg);
		}
		return CompleteFuture.of(configs);
	}

	@Override
	public Collection<Repository> getRepositories() {
		return AtlasMaster.getRepositories();
	}

	@Override
	public void tick() {
		// not required
	}

}
