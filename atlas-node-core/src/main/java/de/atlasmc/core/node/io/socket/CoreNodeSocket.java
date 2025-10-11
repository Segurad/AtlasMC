package de.atlasmc.core.node.io.socket;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.netty.channel.DefaultChannelInitHandler;
import de.atlasmc.io.socket.ServerSocket;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.network.AtlasNode;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.util.AtlasUtil;

/**
 * Socket which is running on this AltasNode
 */
public class CoreNodeSocket extends ServerSocket implements NodeSocket {

	private final UUID uuid;
	private final Set<ConnectionHandler> connectionProcesses;
	private final InetSocketAddress externalHost;
	
	public CoreNodeSocket(UUID uuid, LocalAtlasNode node, SocketConfig config, InetSocketAddress host, InetSocketAddress externalHost) {
		super(host, config, Logging.getLogger("Socket-" + AtlasUtil.getShortUUID(uuid), "Socket"));
		this.uuid = Objects.requireNonNull(uuid);
		this.getLogger().sendToConsole(true);
		this.connectionProcesses = ConcurrentHashMap.newKeySet();
		this.externalHost = externalHost != null ? externalHost : host;
		setChannelInitHandler(new DefaultChannelInitHandler(this));
	}

	@Override
	public boolean isSync() {
		return Atlas.isMainThread();
	}

	@Override
	public void tick() {
		if (!connectionProcesses.isEmpty()) {
			final Log logger = getLogger();
			connectionProcesses.forEach((handler) -> {
				handler.handleSyncPackets(logger);
			});
		}
	}

	@Override
	public void addSyncConnection(ConnectionHandler con) {
		if (con == null)
			throw new IllegalArgumentException("Connection can not be null!");
		connectionProcesses.add(con);
	}

	@Override
	public void removeSyncConnection(ConnectionHandler con) {
		connectionProcesses.remove(con);
	}

	@Override
	public AtlasNode getNode() {
		return de.atlasmc.node.AtlasNode.getAtlas();
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public InetSocketAddress getExternalHost() {
		return externalHost;
	}
	
}
