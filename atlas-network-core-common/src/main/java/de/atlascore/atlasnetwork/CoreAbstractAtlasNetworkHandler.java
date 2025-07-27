package de.atlascore.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetworkHandler;
import de.atlasmc.atlasnetwork.NodeManager;
import de.atlasmc.atlasnetwork.PermissionManager;
import de.atlasmc.atlasnetwork.player.ProfileHandler;
import de.atlasmc.atlasnetwork.server.ServerManager;

public abstract class CoreAbstractAtlasNetworkHandler implements AtlasNetworkHandler {

	private final NodeManager nodeManager;
	private final ServerManager serverManager;
	private final ProfileHandler profileHandler;
	private final PermissionManager permissionProvider;
	private final UUID uuid;
	private final PublicKey publicKey;
	
	public CoreAbstractAtlasNetworkHandler(CoreAbstractAtlasNetworkHandlerBuilder<?> builder) {
		this.nodeManager = builder.getNodeManager();
		this.serverManager = builder.getServerManager();
		this.profileHandler = builder.getProfileHandler();
		this.permissionProvider = builder.getPermissionManager();
		this.uuid = builder.getUUID();
		this.publicKey = builder.getPublicKey();
		if (nodeManager == null)
			throw new IllegalArgumentException("NodeManager can not be null!");
		if (serverManager == null)
			throw new IllegalArgumentException("ServerManager can not be null!");
		if (profileHandler == null)
			throw new IllegalArgumentException("ProfileHandler can not be null!");
		if (permissionProvider == null)
			throw new IllegalArgumentException("PermissionProvider can not be null!");
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (publicKey == null)
			throw new IllegalArgumentException("PublicKey can not be null!");
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

}
