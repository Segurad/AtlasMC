package de.atlascore.io.netty.channel;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.io.ConnectionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChannelInitHandler extends ChannelInitializer<SocketChannel> {

	private final LocalProxy proxy;
	
	public ChannelInitHandler(LocalProxy proxy) {
		this.proxy = proxy;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ConnectionHandler handler = new ConnectionHandler(ch, proxy);
		ch.pipeline().addLast("framer", VarLengthFieldFrameDecoder.INSTANCE)
		.addLast("framer-prepender", VarLengthFieldPrepender.INSTANCE)
		.addLast("protocol-decoder", new PacketDecoder(handler))
		.addLast("protocol-encoder", new PacketEncoder())
		.addLast("processor", new PacketProcessor(handler));
	}

}
