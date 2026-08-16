package de.atlasmc.network;

import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.datarepository.Repository;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.network.permission.PermissionManager;
import de.atlasmc.network.player.ProfileHandler;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.Future;

public interface AtlasNetworkHandler extends Tickable {
	
	@NotNull
	NodeManager getNodeManager();
	
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
	Future<SocketConfig> getSocketConfig(String name);
	
	@NotNull
	Future<Collection<SocketConfig>> getSocketConfigs(Collection<String> names);
	
	@NotNull
	NetworkStats getNetworkStats();
	
	@NotNull
	NetworkInfo getNetworkInfo();

	@NotNull
	NetworkInfo getNetworkInfoMaintenance();
	
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
