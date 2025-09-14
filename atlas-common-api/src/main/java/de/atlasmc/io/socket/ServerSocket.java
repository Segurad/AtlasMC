package de.atlasmc.io.socket;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.Map.Entry;
import java.util.Objects;

import de.atlasmc.io.socket.SocketConfig.ChannelType;
import de.atlasmc.log.Log;
import de.atlasmc.util.configuration.ConfigurationSection;
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
	private final String host;
	private final int port;
	
	public ServerSocket(String host, int port, SocketConfig config, Log logger) {
		this.host = host;
		this.port = port;
		this.config = Objects.requireNonNull(config);
		this.logger = Objects.requireNonNull(logger);
	}

	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler) {
		this.handler = handler;
	}
	
	public synchronized boolean isOpen() {
		return channel != null && !channel.isOpen();
	}
	
	public int getPort() {
		return port;
	}
	
	public String getHost() {
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
		ConfigurationSection options = config.getChannelOptions();
		if (options != null) {
			for (Entry<String, Object> entry : options.asMap().entrySet()) {
				ChannelOption<Object> option = ChannelOption.valueOf(entry.getKey());
				b.option(option, entry.getValue());
			}
		}
		ConfigurationSection childOptions = config.getChildChannelOptions();
		if (childOptions != null) {
			for (Entry<String, Object> entry : options.asMap().entrySet()) {
				ChannelOption<Object> option = ChannelOption.valueOf(entry.getKey());
				b.childOption(option, entry.getValue());
			}
		}
		if (host != null) {
			channel = (ServerChannel) b.bind(host, port).channel();
			logger.info("Socket running on {}:{}", host, getPort());
		} else {
			InetSocketAddress address = new InetSocketAddress(port);
			channel = (ServerChannel) b.bind(address).channel();
			logger.info("Socket running on {}:{}", address.getAddress().toString(), getPort());
		}
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
