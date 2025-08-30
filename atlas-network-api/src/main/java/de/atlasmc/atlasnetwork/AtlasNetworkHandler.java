package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.player.ProfileHandler;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.Future;

public interface AtlasNetworkHandler extends Tickable {
	
	@NotNull
	NodeManager getNodeManager();
	
	@NotNull
	ServerGroup getFallBack();
	
	@NotNull
	ServerManager getServerManager();
	
	@NotNull
	ProfileHandler getProfileHandler();
	
	@NotNull
	PermissionManager getPermissionManager();
	
	@NotNull
	Future<NodeConfig> getNodeConfig(String name);
	
	@NotNull
	Future<Collection<NodeConfig>> getNodeConfigs(Collection<String> names);
	
	@NotNull
	Future<ProxyConfig> getProxyConfig(String name);
	
	@NotNull
	Future<Collection<ProxyConfig>> getProxyConfigs(Collection<String> names);

	int getOnlinePlayerCount();

	int getMaxPlayers();
	
	@NotNull
	NetworkInfo getNetworkInfo();

	@NotNull
	NetworkInfo getNetworkInfoMaintenance();

	boolean isMaintenance();
	
	@NotNull
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
	@NotNull
	PublicKey getPublicKey();
	
	@NotNull
	ConnectionHandler getMasterConnection();
	
}
