package de.atlasmc.proxy;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.tick.Tickable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Proxy which is running on this AltasNode
 */
public interface LocalProxy extends Proxy, SyncThreadHolder, Tickable {
	
	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler);
	
	public boolean isRunnning();
	
	public void run();
	
	public void stop();
	
	public ProxyConfig getConfig();

	public Log getLogger();
	
	public void addSyncConnection(ProxyConnectionHandler con);
	
	public void removeSyncConnection(ProxyConnectionHandler con);
	
}
