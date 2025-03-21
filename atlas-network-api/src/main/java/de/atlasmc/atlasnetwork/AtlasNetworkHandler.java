package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.Future;

public interface AtlasNetworkHandler extends Tickable {
	
	NodeManager getNodeManager();
	
	ServerGroup getFallBack();
	
	ServerManager getServerManager();
	
	ProfileHandler getProfileHandler();
	
	PermissionManager getPermissionManager();
	
	Future<NodeConfig> getNodeConfig(String name);
	
	Future<Collection<NodeConfig>> getNodeConfigs(Collection<String> names);
	
	Future<ProxyConfig> getProxyConfig(String name);
	
	Future<Collection<ProxyConfig>> getProxyConfigs(Collection<String> names);

	int getOnlinePlayerCount();

	int getMaxPlayers();
	
	NetworkInfo getNetworkInfo();

	NetworkInfo getNetworkInfoMaintenance();

	boolean isMaintenance();
	
	Collection<Repository> getRepositories();
	
	/**
	 * Returns this nodes UUID
	 * @return uuid
	 */
	@NotNull
	UUID getNodeUUID();
	
	/**
	 * Returns the masters public key
	 * @return public key
	 */
	PublicKey getPublicKey();
	
}
