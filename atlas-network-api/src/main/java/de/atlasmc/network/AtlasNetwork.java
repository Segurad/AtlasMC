package de.atlasmc.network;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.UUID;

import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.network.io.MasterClient;
import de.atlasmc.network.permission.PermissionManager;
import de.atlasmc.network.player.ProfileManager;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;

public class AtlasNetwork  {
	
	private static AtlasNetworkHandler HANDLER;
	
	private AtlasNetwork() {}
	
	public static AtlasNetworkHandler getInstance() {
		return HANDLER;
	}
	
	@InternalAPI
	public static void init(AtlasNetworkHandler handler) {
		synchronized (AtlasNetwork.class) {
			if (HANDLER != null)
				throw new IllegalStateException("AtlasNetwork already initialized!");
			HANDLER = handler;
		}
	}
	
	public static MasterClient getMasterClient() {
		return HANDLER.getMasterClient();
	}
	
	public static Certificate getNodeCertificate() {
		return HANDLER.getNodeCertificate();
	}
	
	public static NodeManager getNodeManager() {
		return HANDLER.getNodeManager();
	}
	
	public static ServerManager getServerManager() {
		return HANDLER.getServerManager();
	}
	
	public static ProfileManager getProfileHandler() {
		return HANDLER.getProfileHandler();
	}
	
	public static PermissionManager getPermissionProvider() {
		return HANDLER.getPermissionManager();
	}
	
	public static NetworkInfo getNetworkInfo() {
		return HANDLER.getNetworkInfo();
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
	
	public static ConnectionHandler getMasterConnection() {
		return HANDLER.getMasterConnection();
	}
	
}
