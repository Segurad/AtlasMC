package de.atlasmc.core.network;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.datarepository.Repository;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.network.AtlasNetworkHandler;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.network.NetworkStats;
import de.atlasmc.network.NodeConfig;
import de.atlasmc.network.NodeManager;
import de.atlasmc.network.permission.PermissionManager;
import de.atlasmc.network.player.ProfileHandler;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.util.concurrent.future.Future;

public class CoreAtlasNetworkHandler implements AtlasNetworkHandler {

	private final NodeManager nodeManager;
	private final ServerManager serverManager;
	private final ProfileHandler profileHandler;
	private final PermissionManager permissionProvider;
	private final UUID uuid;
	private final PublicKey publicKey;
	private final ConnectionHandler con;
	private final NetworkInfo info;
	private final NetworkInfo infoMaintenance;
	private final NetworkStats stats;
	
	public CoreAtlasNetworkHandler(CoreAbstractAtlasNetworkHandlerBuilder<?> builder) {
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

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Future<NodeConfig> getNodeConfig(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Collection<NodeConfig>> getNodeConfigs(Collection<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<SocketConfig> getSocketConfig(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Collection<SocketConfig>> getSocketConfigs(Collection<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkStats getNetworkStats() {
		return stats;
	}

	@Override
	public NetworkInfo getNetworkInfo() {
		return info;
	}

	@Override
	public NetworkInfo getNetworkInfoMaintenance() {
		return infoMaintenance;
	}

	@Override
	public Collection<Repository> getRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

}
