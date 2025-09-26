package de.atlasmc.core.network;

import java.security.PublicKey;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.network.AtlasNetworkHandler;
import de.atlasmc.network.NodeManager;
import de.atlasmc.network.PermissionManager;
import de.atlasmc.network.player.ProfileHandler;
import de.atlasmc.network.server.ServerManager;

public abstract class CoreAbstractAtlasNetworkHandler implements AtlasNetworkHandler {

	private final NodeManager nodeManager;
	private final ServerManager serverManager;
	private final ProfileHandler profileHandler;
	private final PermissionManager permissionProvider;
	private final UUID uuid;
	private final PublicKey publicKey;
	private final ConnectionHandler con;
	
	public CoreAbstractAtlasNetworkHandler(CoreAbstractAtlasNetworkHandlerBuilder<?> builder) {
		this.nodeManager = Objects.requireNonNull(builder.getNodeManager());
		this.serverManager = Objects.requireNonNull(builder.getServerManager());
		this.profileHandler = Objects.requireNonNull(builder.getProfileHandler());
		this.permissionProvider = Objects.requireNonNull(builder.getPermissionManager());
		this.uuid = Objects.requireNonNull(builder.getUUID());
		this.publicKey = Objects.requireNonNull(builder.getPublicKey());
		this.con = Objects.requireNonNull(builder.getConnection());
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
	public ProfileHandler getProfileHandler() {
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
		return publicKey;
	}
	
	@Override
	public ConnectionHandler getMasterConnection() {
		return con;
	}

}
