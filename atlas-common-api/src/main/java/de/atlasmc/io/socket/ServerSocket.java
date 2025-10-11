package de.atlasmc.io.socket;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.Map.Entry;
import java.util.Objects;

import de.atlasmc.io.socket.SocketConfig.ChannelType;
import de.atlasmc.log.Log;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.socket.SocketChannel;

public class ServerSocket implements Closeable {
	
	private ChannelInitializer<SocketChannel> handler;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ServerChannel channel;
	private final SocketConfig config;
	private final Log logger;
	private final InetSocketAddress host;
	
	public ServerSocket(InetSocketAddress host, SocketConfig config, Log logger) {
		this.host = Objects.requireNonNull(host);
		this.config = Objects.requireNonNull(config);
		this.logger = Objects.requireNonNull(logger);
		this.logger.sendToConsole(true);
	}

	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler) {
		this.handler = handler;
	}
	
	public synchronized boolean isOpen() {
		return channel != null && !channel.isOpen();
	}
	
	public InetSocketAddress getHost() {
		return host;
	}
	
	public synchronized void open() {
		if (isOpen()) 
			throw new IllegalStateException("Socket already open!");
		
		ChannelType type = config.getType();
		
		bossGroup = new MultiThreadIoEventLoopGroup(config.getBossThreads(), type.getDefaultFactory());
		workerGroup = new MultiThreadIoEventLoopGroup(config.getWorkerThreads(), type.getDefaultFactory());
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(type.getServerSocektChannelClass());
		b.childHandler(handler);
		if (!config.getChannelOptions().isEmpty()) {
			for (Entry<ChannelOption<?>, Object> entry : config.getChannelOptions().entrySet()) {
				@SuppressWarnings("unchecked")
				ChannelOption<Object> option = (ChannelOption<Object>) entry.getKey(); 
				b.option(option, entry.getValue());
			}
		}
		if (!config.getChildChannelOptions().isEmpty()) {
			for (Entry<ChannelOption<?>, Object> entry : config.getChildChannelOptions().entrySet()) {
				@SuppressWarnings("unchecked")
				ChannelOption<Object> option = (ChannelOption<Object>) entry.getKey(); 
				b.childOption(option, entry.getValue());
			}
		}
		channel = (ServerChannel) b.bind(host).channel();
		logger.info("Socket running on {}:{}", host.getHostString(), host.getPort());
	}
	
	@Override
	public synchronized void close() {
		if (!isOpen()) 
			return;
		channel.close();
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		bossGroup = null;
		workerGroup = null;
		logger.info("Socket closed!");
	}

	public Log getLogger() {
		return logger;
	}
	
	public SocketConfig getConfig() {
		return config;
	}

}
