package de.atlascore.proxy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
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
public class CoreLocalProxy extends CoreProxy implements LocalProxy {

	private ChannelInitializer<SocketChannel> handler;
	private EventLoopGroup bossGroup, workerGroup;
	private Channel channel;
	private final ProxyConfig config;
	private final Logger logger;
	
	public CoreLocalProxy(LocalAtlasNode node, int port, ProxyConfig config) {
		super(node, getLocalHostName(), port);
		this.config = config;
		this.logger = LoggerFactory.getLogger("Proxy-" + port);
	}

	@Override
	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler) {
		this.handler = handler;
	}
	
	@Override
	public boolean isRunnning() {
		return channel != null && !channel.isOpen();
	}
	
	@Override
	public void run() {
		if (isRunnning()) 
			throw new RuntimeException("Proxy already running!");
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(handler);
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			channel = b.bind(getPort()).channel();
		logger.info("Proxy running on {}", getPort());
	}
	
	@Override
	public void stop() {
		if (!isRunnning()) 
			return;
		channel.close();
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		bossGroup = null;
		workerGroup = null;
		logger.info("Proxy stopped!");
	}

	@Override
	public Logger getLogger() {
		return logger;
	}
	
	@Override
	public ProxyConfig getConfig() {
		return config;
	}

	@Override
	public boolean isSync() {
		return Atlas.getAtlas().isSync();
	}
	
	private static String getLocalHostName() {
		try {
			InetAddress adr = InetAddress.getLocalHost();
			return adr.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
