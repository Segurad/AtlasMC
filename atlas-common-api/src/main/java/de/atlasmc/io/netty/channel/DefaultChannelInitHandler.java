package de.atlasmc.io.netty.channel;

import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.io.socket.ServerSocket;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class DefaultChannelInitHandler extends ChannelInitializer<SocketChannel> {

	private final ServerSocket socket;
	
	public DefaultChannelInitHandler(ServerSocket socket) {
		this.socket = socket;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		new ServerSocketConnectionHandler(socket, ch, socket.getLogger());
	}

}
