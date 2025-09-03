package de.atlasmc.socket;

import de.atlasmc.atlasnetwork.proxy.AtlasSocket;
import de.atlasmc.atlasnetwork.proxy.SocketConfig;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.tick.Tickable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Socket which is running on this AltasNode
 */
public interface NodeSocket extends AtlasSocket, SyncThreadHolder, Tickable {
	
	void setChannelInitHandler(ChannelInitializer<SocketChannel> handler);
	
	boolean isRunnning();
	
	void run();
	
	void stop();
	
	SocketConfig getConfig();

	Log getLogger();
	
	void addSyncConnection(ConnectionHandler con);
	
	void removeSyncConnection(ConnectionHandler con);
	
}
