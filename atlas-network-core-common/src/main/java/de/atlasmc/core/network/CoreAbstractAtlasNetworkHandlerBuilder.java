package de.atlasmc.core.network;

import java.util.UUID;

import de.atlasmc.network.AtlasNetworkHandler;
import de.atlasmc.network.NodeManager;
import de.atlasmc.network.io.MasterClient;
import de.atlasmc.network.permission.PermissionManager;
import de.atlasmc.network.player.ProfileManager;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.util.Builder;

public abstract class CoreAbstractAtlasNetworkHandlerBuilder<T extends CoreAbstractAtlasNetworkHandlerBuilder<T>> implements Builder<AtlasNetworkHandler> {

	private NodeManager nodeManager;
	private ServerManager serverManager;
	private ProfileManager profileHandler;
	private PermissionManager permissionManager;
	private UUID uuid;
	private MasterClient client;
	
	public NodeManager getNodeManager() {
		return nodeManager;
	}
	
	public T setNodeManager(NodeManager nodeManager) {
		this.nodeManager = nodeManager;
		return getThis();
	}

	public ServerManager getServerManager() {
		return serverManager;
	}
	
	public T setServerManager(ServerManager serverManager) {
		this.serverManager = serverManager;
		return getThis();
	}

	public ProfileManager getProfileManager() {
		return profileHandler;
	}
	
	public T setProfileHandler(ProfileManager profileHandler) {
		this.profileHandler = profileHandler;
		return getThis();
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}
	
	public T setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
		return getThis();
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public T setUUID(UUID uuid) {
		this.uuid = uuid;
		return getThis();
	}
	
	public T setClient(MasterClient client) {
		this.client = client;
		return getThis();
	}
	
	public MasterClient getClient() {
		return client;
	}
	
	public abstract T getThis();
	
}
