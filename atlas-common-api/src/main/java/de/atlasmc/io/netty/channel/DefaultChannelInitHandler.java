package de.atlasmc.io.netty.channel;

import de.atlascore.io.CoreSocketConnectionHandler;
import de.atlasmc.socket.NodeSocket;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class DefaultChannelInitHandler extends ChannelInitializer<SocketChannel> {

	private final NodeSocket proxy;
	
	public DefaultChannelInitHandler(NodeSocket proxy) {
		this.proxy = proxy;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		proxy.addSyncConnection(new CoreSocketConnectionHandler(ch, proxy.getLogger()));
	}

}
