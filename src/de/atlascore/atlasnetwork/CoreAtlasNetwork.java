package de.atlascore.atlasnetwork;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.atlasnetwork.master.CoreServerManager;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;

public class CoreAtlasNetwork implements AtlasNetwork {
	
	private List<AtlasNode> nodes;
	private ServerGroup fallBack;
	private int maxplayers, players;
	private final CorePlayerProfileHandler profileHandler;
	private NetworkInfo info, infoMaintenance;
	private boolean maintenance;
	private final CoreServerManager smanager;
	private final Map<String, ProxyConfig> proxyConfigs;
	private final Map<String, NodeConfig> nodeConfigs;
	
	public CoreAtlasNetwork(CorePlayerProfileHandler profileHandler, NetworkInfo info, NetworkInfo infoMaintenance, int slots, boolean maintenance, CoreServerManager smanager, Map<String, NodeConfig> nodeConfigs, Map<String, ProxyConfig> proxyConfigs) {
		if (profileHandler == null)
			throw new IllegalArgumentException("Profile Handler can not be null!");
		this.profileHandler = profileHandler;
		this.info = info;
		this.infoMaintenance = infoMaintenance;
		this.maxplayers = slots;
		this.maintenance = maintenance;
		this.smanager = smanager;
		this.proxyConfigs = new ConcurrentHashMap<>(proxyConfigs);
		this.nodeConfigs = new ConcurrentHashMap<>(nodeConfigs);
	}
	
	public ServerGroup getFallBack() {
		return fallBack;
	}
	
	public void setFallBack(ServerGroup group) {
		this.fallBack = group;
	}

	public int getOnlinePlayerCount() {
		return players;
	}

	@Override
	public AtlasPlayer getPlayer(String name) {
		return profileHandler.getPlayer(name);
	}

	@Override
	public AtlasPlayer getPlayerByMojang(UUID uuid) {
		return profileHandler.getPlayerByMojang(uuid);
	}

	@Override
	public AtlasPlayer getPlayerByMojang(String name) {
		return profileHandler.getPlayerByMojang(name);
	}

	@Override
	public AtlasPlayer getPlayer(int id) {
		return profileHandler.getPlayer(id);
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
	public boolean isMainenance() {
		return maintenance;
	}

	@Override
	public ServerGroup getServerGroup(String name) {
		return smanager.getServerGroup(name);
	}

	@Override
	public NodeConfig getNodeConfig(String name) {
		return nodeConfigs.get(name);
	}

	@Override
	public ProxyConfig getProxyConfig(String name) {
		return proxyConfigs.get(name);
	}

}
