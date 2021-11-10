package de.atlasmc.atlasnetwork.proxy;

import com.google.gson.JsonElement;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Proxy which is running on this AltasNode
 */
public interface LocalProxy extends Proxy {
	
	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler);
	
	public boolean isRunnning();
	
	public void run();
	
	public void stop();

	public JsonElement createServerListResponse(int protocol);
	
}
