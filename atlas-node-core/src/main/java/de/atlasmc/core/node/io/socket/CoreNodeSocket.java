package de.atlasmc.core.node.io.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.socket.ServerSocket;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.network.AtlasNode;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.node.io.socket.NodeSocket;

/**
 * Proxy which is running on this AltasNode
 */
public class CoreNodeSocket extends ServerSocket implements NodeSocket {

	private final UUID uuid;
	private final Set<ConnectionHandler> connectionProcesses;
	
	public CoreNodeSocket(UUID uuid, LocalAtlasNode node, int port, SocketConfig config) {
		super(getLocalHostName(), port, config, Logging.getLogger("Socket-" + uuid, "Socket"));
		this.uuid = Objects.requireNonNull(uuid);
		this.getLogger().sendToConsole(true);
		this.connectionProcesses = ConcurrentHashMap.newKeySet();
	}

	@Override
	public boolean isSync() {
		return Atlas.isMainThread();
	}
	
	private static String getLocalHostName() {
		try {
			InetAddress adr = InetAddress.getLocalHost();
			return adr.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
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
	
}
