package de.atlasmc.node.io.socket;

import java.io.Closeable;

import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.log.Log;
import de.atlasmc.network.socket.AtlasSocket;
import de.atlasmc.tick.Tickable;

/**
 * Socket which is running on this AltasNode
 */
public interface NodeSocket extends AtlasSocket, SyncThreadHolder, Tickable, Closeable {
	
	boolean isOpen();
	
	void open();
	
	SocketConfig getConfig();

	Log getLogger();
	
	void addSyncConnection(ConnectionHandler con);
	
	void removeSyncConnection(ConnectionHandler con);
	
}
