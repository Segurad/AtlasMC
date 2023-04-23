package de.atlascore.atlasnetwork.master;

import de.atlascore.io.ConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class CoreAtlasMasterConnection {
	
	private EventLoopGroup workerGroup;
	private ConnectionHandler connection = null;
	private final String host;
	private final int port;
	
	public CoreAtlasMasterConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public synchronized void connect(GenericFutureListener<? extends Future<? super Void>> listener) {
		if (workerGroup != null)
			workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup);
		b.channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				connection = new ConnectionHandler(ch);
			}
		});
		b.connect(host, port).addListener(listener);
	}
	
	public synchronized void close() {
		if (connection == null)
			return;
		connection.close();
		workerGroup.shutdownGracefully();
		connection = null;
		workerGroup = null;
	}

}
