package de.atlasmc.io.channel;

import de.atlasmc.io.ConnectionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChannelInitHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ConnectionHandler handler = new ConnectionHandler(ch);
		ch.pipeline().addLast("framer", new VarLengthFieldFrameDecoder(2097151, 0, -1))
		.addLast("framer-prepender", new VarLengthFieldPrepender())
		.addLast("protocol-decoder", new PacketDecoder())
		.addLast("protocol-encoder", new PacketEncoder())
		.addLast("processor", new PacketProcessor(handler));
	}

}
