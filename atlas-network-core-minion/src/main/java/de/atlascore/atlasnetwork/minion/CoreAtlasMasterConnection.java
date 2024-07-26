package de.atlascore.atlasnetwork.minion;

import de.atlascore.io.SimpleConnectionHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.log.Log;
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
	private final Log logger;
	
	public CoreAtlasMasterConnection(String host, int port, Log logger) {
		this.host = host;
		this.port = port;
		this.logger = logger;
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
				connection = new SimpleConnectionHandler(ch, logger);
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
