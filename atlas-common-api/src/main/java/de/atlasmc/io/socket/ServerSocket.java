package de.atlasmc.io.socket;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.Map.Entry;
import java.util.Objects;

import de.atlasmc.io.socket.SocketConfig.ChannelType;
import de.atlasmc.log.Log;
import de.atlasmc.util.configuration.ConfigurationException;
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
	
	public ServerSocket(SocketConfig config, Log logger) {
		this(null, config, logger);
	}
	
	public ServerSocket(InetSocketAddress host, SocketConfig config, Log logger) {
		this.config = Objects.requireNonNull(config, "config");
		if (host == null)
			host = config.getHost();
		this.host = Objects.requireNonNull(host, "host");
		this.logger = Objects.requireNonNull(logger, "logger");
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
	
	/**
	 * Opens the socket
	 * @throws IllegalStateException if the socket is already open
	 * @throws ConfigurationException if the host couldn't be resolved
	 */
	public synchronized void open() {
		if (isOpen()) 
			throw new IllegalStateException("Socket already open!");
		
		ChannelType type = config.getType();
		if (!type.isAvailable()) {
			var defaultType = ChannelType.getDefault();
			logger.warn("Configured channel type not available: " + type + " defaulting to: " + defaultType);
			type = defaultType;
		}
		bossGroup = new MultiThreadIoEventLoopGroup(config.getBossThreads(), type.getDefaultFactory());
		workerGroup = new MultiThreadIoEventLoopGroup(config.getWorkerThreads(), type.getDefaultFactory());
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(type.getServerSocketChannelClass());
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
		var host = new InetSocketAddress(this.host.getHostString(), this.host.getPort());
		if (host.isUnresolved())
			throw new ConfigurationException("Failed to resolve host " + host.getHostString() + ":" + host.getPort());
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
