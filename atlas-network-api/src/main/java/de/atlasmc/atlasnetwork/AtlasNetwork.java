package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;

public interface AtlasNetwork extends Tickable {
	
	NodeManager getNodeManager();
	
	ServerManager getServerManager();
	
	ProfileHandler getProfileHandler();
	
	PermissionProvider getPermissionProvider();
	
	NodeConfig getNodeConfig(String name);
	
	ProxyConfig getProxyConfig(String name);

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
