package de.atlasmc.network;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.UUID;

import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.network.io.MasterClient;
import de.atlasmc.network.permission.PermissionManager;
import de.atlasmc.network.player.ProfileManager;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;

public interface AtlasNetworkHandler extends Tickable {
	
	@NotNull
	NodeManager getNodeManager();
	
	@NotNull
	ServerManager getServerManager();
	
	@NotNull
	ProfileManager getProfileHandler();
	
	@NotNull
	PermissionManager getPermissionManager();
	
	@NotNull
	NetworkStats getNetworkStats();
	
	@NotNull
	NetworkInfo getNetworkInfo();

	@NotNull
	NetworkInfo getNetworkInfoMaintenance();
	
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
	Certificate getNodeCertificate();
	
	@NotNull
	ConnectionHandler getMasterConnection();
	
	@NotNull
	MasterClient getMasterClient();
	
}
