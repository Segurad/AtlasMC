package de.atlasmc.atlasnetwork.proxy;

import org.slf4j.Logger;

import de.atlasmc.event.SyncThreadHolder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Proxy which is running on this AltasNode
 */
public interface LocalProxy extends Proxy, SyncThreadHolder {
	
	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler);
	
	public boolean isRunnning();
	
	public void run();
	
	public void stop();
	
	public ProxyConfig getConfig();

	public Logger getLogger();
	
}
