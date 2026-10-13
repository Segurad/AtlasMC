package de.atlasmc.core.network;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.Atlas;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.network.AtlasNetworkHandler;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.network.NetworkStats;
import de.atlasmc.network.NodeManager;
import de.atlasmc.network.io.MasterClient;
import de.atlasmc.network.permission.PermissionManager;
import de.atlasmc.network.player.ProfileManager;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.util.secrets.SecretManagerException;

public class CoreAtlasNetworkHandler implements AtlasNetworkHandler {

	private final NodeManager nodeManager;
	private final ServerManager serverManager;
	private final ProfileManager profileHandler;
	private final PermissionManager permissionProvider;
	private final UUID uuid;
	private final MasterClient client;
	
	public CoreAtlasNetworkHandler(CoreAbstractAtlasNetworkHandlerBuilder<?> builder) {
		this.nodeManager = Objects.requireNonNull(builder.getNodeManager(), "NodeManager");
		this.serverManager = Objects.requireNonNull(builder.getServerManager(), "ServerManager");
		this.profileHandler = Objects.requireNonNull(builder.getProfileManager(), "ProfileManager");
		this.permissionProvider = Objects.requireNonNull(builder.getPermissionManager(), "PermissionManager");
		this.uuid = Objects.requireNonNull(builder.getUUID());
		this.client = Objects.requireNonNull(builder.getClient(), "client");
	}

	@Override
	public NodeManager getNodeManager() {
		return nodeManager;
	}

	@Override
	public ServerManager getServerManager() {
		return serverManager;
	}

	@Override
	public ProfileManager getProfileHandler() {
		return profileHandler;
	}

	@Override
	public PermissionManager getPermissionManager() {
		return permissionProvider;
	}

	@Override
	public UUID getNodeUUID() {
		return uuid;
	}

	@Override
	public PublicKey getPublicKey() {
		return client.getCertificate().getPublicKey();
	}
	
	@Override
	public ConnectionHandler getMasterConnection() {
		return client.getConnection();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NetworkStats getNetworkStats() {
		return client.getNetworkStats();
	}

	@Override
	public NetworkInfo getNetworkInfo() {
		return client.getNetworkInfo();
	}

	@Override
	public NetworkInfo getNetworkInfoMaintenance() {
		return client.getNetworkInfoMaintenance();
	}

	@Override
	public Certificate getNodeCertificate() {
		try {
			return Atlas.getSecretManager().getCertificate("node-cert");
		} catch (SecretManagerException e) {
			throw new IllegalStateException("Unable to access certificate!", e);
		}
	}

	@Override
	public MasterClient getMasterClient() {
		return client;
	}

}
