package de.atlascore.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetworkHandler;
import de.atlasmc.atlasnetwork.NodeManager;
import de.atlasmc.atlasnetwork.PermissionManager;
import de.atlasmc.atlasnetwork.player.ProfileHandler;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.util.Builder;

public abstract class CoreAbstractAtlasNetworkHandlerBuilder<T extends CoreAbstractAtlasNetworkHandlerBuilder<T>> implements Builder<AtlasNetworkHandler> {

	private NodeManager nodeManager;
	private ServerManager serverManager;
	private ProfileHandler profileHandler;
	private PermissionManager permissionManager;
	private UUID uuid;
	private PublicKey publicKey;
	private ConnectionHandler connection;
	
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

	public ProfileHandler getProfileHandler() {
		return profileHandler;
	}
	
	public T setProfileHandler(ProfileHandler profileHandler) {
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

	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public T setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
		return getThis();
	}
	
	public T setConnection(ConnectionHandler connection) {
		this.connection = connection;
		return getThis();
	}
	
	public ConnectionHandler getConnection() {
		return connection;
	}
	
	public abstract T getThis();

	@Override
	public void clear() {
		nodeManager = null;
		profileHandler = null;
		serverManager = null;
		permissionManager = null;
		publicKey = null;
		uuid = null;
	}

}
