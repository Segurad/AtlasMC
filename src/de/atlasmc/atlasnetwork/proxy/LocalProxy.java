package de.atlasmc.atlasnetwork.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Proxy which is running on this AltasNode
 */
public class LocalProxy extends Proxy {

	private ChannelInitializer<SocketChannel> handler;
	private EventLoopGroup bossGroup, workerGroup;
	private Channel channel;
	
	public LocalProxy(int port) {
		super(null, port);
	}
	
	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler) {
		this.handler = handler;
	}
	
	public boolean isRunnning() {
		return bossGroup != null && !bossGroup.isShutdown();
	}
	
	public void run() {
		if (isRunnning()) throw new RuntimeException("Proxy already running!");
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(handler);
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			channel = b.bind(getPort()).channel();
	}
	
	public void stop() {
		if (!isRunnning()) return;
		channel.close();
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		bossGroup = null;
		workerGroup = null;
	}
	
}
