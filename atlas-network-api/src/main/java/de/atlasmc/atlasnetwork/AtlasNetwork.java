package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.Future;

public class AtlasNetwork  {
	
	private static AtlasNetworkHandler HANDLER;
	
	private AtlasNetwork() {}
	
	@InternalAPI
	public static void init(AtlasNetworkHandler handler) {
		synchronized (AtlasNetwork.class) {
			if (HANDLER != null)
				throw new IllegalStateException("AtlasNetwork already initialized!");
			HANDLER = handler;
		}
	}
	
	public static NodeManager getNodeManager() {
		return HANDLER.getNodeManager();
	}
	
	public static ServerManager getServerManager() {
		return HANDLER.getServerManager();
	}
	
	public static ProfileHandler getProfileHandler() {
		return HANDLER.getProfileHandler();
	}
	
	public static PermissionManager getPermissionProvider() {
		return HANDLER.getPermissionProvider();
	}
	
	public static Future<NodeConfig> getNodeConfig(String name) {
		return HANDLER.getNodeConfig(name);
	}
	
	public static Future<ProxyConfig> getProxyConfig(String name) {
		return HANDLER.getProxyConfig(name);
	}

	public static int getOnlinePlayerCount() {
		return HANDLER.getOnlinePlayerCount();
	}

	public static int getMaxPlayers() {
		return HANDLER.getMaxPlayers();
	}
	
	public static NetworkInfo getNetworkInfo() {
		return HANDLER.getNetworkInfo();
	}

	public static boolean isMaintenance() {
		return HANDLER.isMaintenance();
	}
	
	/**
	 * Returns this nodes UUID
	 * @return uuid
	 */
	@NotNull
	public static UUID getNodeUUID() {
		return HANDLER.getNodeUUID();
	}
	
	/**
	 * Returns the masters public key
	 * @return public key
	 */
	public static PublicKey getPublicKey() {
		return HANDLER.getPublicKey();
	}
	
}
