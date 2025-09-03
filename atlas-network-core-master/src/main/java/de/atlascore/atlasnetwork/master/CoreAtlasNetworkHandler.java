package de.atlascore.atlasnetwork.master;

import java.util.ArrayList;
import java.util.Collection;

import de.atlascore.atlasnetwork.CoreAbstractAtlasNetworkHandler;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.SocketConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.proxy.ProxyManager;
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
	public Future<SocketConfig> getProxyConfig(String name) {
		return CompleteFuture.of(AtlasMaster.getProxyManager().getProxyConfig(name));
	}
	
	@Override
	public Future<Collection<SocketConfig>> getProxyConfigs(Collection<String> names) {
		if (names.isEmpty())
			return CompleteFuture.getEmptyListFuture();
		ProxyManager proxyManager = AtlasMaster.getProxyManager();
		ArrayList<SocketConfig> configs = new ArrayList<>(names.size());
		for (String name : names) {
			SocketConfig cfg = proxyManager.getProxyConfig(name);
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
	public void tick() {}

}
