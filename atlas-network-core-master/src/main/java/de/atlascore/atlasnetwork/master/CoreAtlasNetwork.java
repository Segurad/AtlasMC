package de.atlascore.atlasnetwork.master;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
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
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.datarepository.DataRepositoryHandler;
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
	private final DataRepositoryHandler repoHandler;
	private final KeyPair keyPair;
	private final UUID uuid;
	
	public CoreAtlasNetwork(CorePlayerProfileHandler profileHandler, CorePermissionProvider permProvider, NetworkInfo info, NetworkInfo infoMaintenance, int slots, boolean maintenance, CoreServerManager smanager, Map<String, NodeConfig> nodeConfigs, Map<String, ProxyConfig> proxyConfigs, DataRepositoryHandler repoHandler, KeyPair keyPair, UUID uuid) {
		if (profileHandler == null)
			throw new IllegalArgumentException("Profile handler can not be null!");
		if (permProvider == null)
			throw new IllegalArgumentException("Permission porvider can not be null!");
		if (repoHandler == null)
			throw new IllegalArgumentException("RepositoryHandler can not be null!");
		this.profileHandler = profileHandler;
		this.permProvider = permProvider;
		this.info = info;
		this.infoMaintenance = infoMaintenance;
		this.maxplayers = slots;
		this.maintenance = maintenance;
		this.nodeManager = new CoreNodeManager();
		this.smanager = smanager;
		CoreSimpleServerDeploymentMethod simpleDeployer = new CoreSimpleServerDeploymentMethod(this.nodeManager);
		this.smanager.addDeploymentMethod(NamespacedKey.of("atlas-master:server/simple_deployment"), simpleDeployer);
		this.repoHandler = repoHandler;
		this.proxyConfigs = new ConcurrentHashMap<>(proxyConfigs);
		this.nodeConfigs = new ConcurrentHashMap<>(nodeConfigs);
		this.keyPair = keyPair;
		this.uuid = uuid;
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
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Repository> getRepositories() {
		Collection<? extends Repository> repos = repoHandler.getLocalRepos();
		return (Collection<Repository>) repos;
	}

	@Override
	public CoreServerManager getServerManager() {
		return smanager;
	}

	@Override
	public CorePlayerProfileHandler getProfileHandler() {
		return profileHandler;
	}

	@Override
	public CorePermissionProvider getPermissionProvider() {
		return permProvider;
	}

	@Override
	public CoreNodeManager getNodeManager() {
		return nodeManager;
	}

	@Override
	public UUID getNodeUUID() {
		return uuid;
	}

	@Override
	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

}
