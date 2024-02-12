package de.atlascore.atlasnetwork.master;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.atlasnetwork.CorePermissionProvider;
import de.atlascore.atlasnetwork.CorePlayerProfileHandler;
import de.atlascore.atlasnetwork.master.node.CoreNodeManager;
import de.atlascore.atlasnetwork.master.server.CoreServerManager;
import de.atlascore.atlasnetwork.master.server.CoreSimpleServerDeploymentMethod;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.atlasnetwork.NodeManager;
import de.atlasmc.atlasnetwork.PermissionProvider;
import de.atlasmc.atlasnetwork.ProfileHandler;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.datarepository.Repository;

public class CoreAtlasNetwork implements AtlasNetwork {
	
	private final CoreNodeManager nodeManager;
	private ServerGroup fallBack;
	private int maxplayers, players;
	private final CorePlayerProfileHandler profileHandler;
	private final CorePermissionProvider permProvider;
	private NetworkInfo info, infoMaintenance;
	private boolean maintenance;
	private final CoreServerManager smanager;
	private final Map<String, ProxyConfig> proxyConfigs;
	private final Map<String, NodeConfig> nodeConfigs;
	private final Repository repo;
	
	public CoreAtlasNetwork(CorePlayerProfileHandler profileHandler, CorePermissionProvider permProvider, NetworkInfo info, NetworkInfo infoMaintenance, int slots, boolean maintenance, CoreServerManager smanager, Map<String, NodeConfig> nodeConfigs, Map<String, ProxyConfig> proxyConfigs, Repository repo) {
		if (profileHandler == null)
			throw new IllegalArgumentException("Profile handler can not be null!");
		if (permProvider == null)
			throw new IllegalArgumentException("Permission porvider can not be null!");
		if (repo == null)
			throw new IllegalArgumentException("Repository can not be null!");
		this.profileHandler = profileHandler;
		this.permProvider = permProvider;
		this.info = info;
		this.infoMaintenance = infoMaintenance;
		this.maxplayers = slots;
		this.maintenance = maintenance;
		this.nodeManager = new CoreNodeManager();
		this.smanager = smanager;
		CoreSimpleServerDeploymentMethod simpleDeployer = new CoreSimpleServerDeploymentMethod(this.nodeManager);
		this.smanager.addDeploymentMethod(NamespacedKey.of("atlas-master:server/default_deployment"), simpleDeployer);
		this.repo = repo;
		this.proxyConfigs = new ConcurrentHashMap<>(proxyConfigs);
		this.nodeConfigs = new ConcurrentHashMap<>(nodeConfigs);
	}
	
	public ServerGroup getFallBack() {
		return fallBack;
	}
	
	public void setFallBack(ServerGroup group) {
		this.fallBack = group;
	}

	@Override
	public int getOnlinePlayerCount() {
		return players;
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
	public NodeConfig getNodeConfig(String name) {
		return nodeConfigs.get(name);
	}

	@Override
	public ProxyConfig getProxyConfig(String name) {
		return proxyConfigs.get(name);
	}

	@Override
	public void tick() {
		profileHandler.expungeStaleEntries();
		permProvider.expungeStaleEntries();
		smanager.tick();
	}

	@Override
	public Repository getRepository() {
		return repo;
	}

	@Override
	public ServerManager getServerManager() {
		return smanager;
	}

	@Override
	public ProfileHandler getProfileHandler() {
		return profileHandler;
	}

	@Override
	public PermissionProvider getPermissionProvider() {
		return permProvider;
	}

	@Override
	public NodeManager getNodeManager() {
		return nodeManager;
	}

}