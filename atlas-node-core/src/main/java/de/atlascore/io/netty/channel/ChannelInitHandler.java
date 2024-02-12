package de.atlascore.io.netty.channel;

import de.atlascore.io.CoreProxyConnectionHandler;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChannelInitHandler extends ChannelInitializer<SocketChannel> {

	private final LocalProxy proxy;
	
	public ChannelInitHandler(LocalProxy proxy) {
		this.proxy = proxy;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		proxy.addSyncConnection(new CoreProxyConnectionHandler(ch, proxy));
	}

}
