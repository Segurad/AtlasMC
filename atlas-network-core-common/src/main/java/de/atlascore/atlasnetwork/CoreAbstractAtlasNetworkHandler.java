package de.atlascore.atlasnetwork;

import java.security.PublicKey;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetworkHandler;
import de.atlasmc.atlasnetwork.NodeManager;
import de.atlasmc.atlasnetwork.PermissionManager;
import de.atlasmc.atlasnetwork.player.ProfileHandler;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.io.ConnectionHandler;

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
