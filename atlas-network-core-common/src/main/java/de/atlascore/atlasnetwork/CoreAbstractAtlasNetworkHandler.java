package de.atlascore.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetworkHandler;
import de.atlasmc.atlasnetwork.NodeManager;
import de.atlasmc.atlasnetwork.PermissionManager;
import de.atlasmc.atlasnetwork.ProfileHandler;
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
		this.permissionProvider = builder.getPermissionProvider();
		this.uuid = builder.getUUID();
		this.publicKey = builder.getPublicKey();
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
	public PermissionManager getPermissionProvider() {
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
