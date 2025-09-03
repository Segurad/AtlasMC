package de.atlascore.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.SocketConfig;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.socket.NodeSocket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.IoHandlerFactory;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.uring.IoUring;
import io.netty.channel.uring.IoUringServerSocketChannel;

/**
 * Proxy which is running on this AltasNode
 */
public class CoreNodeSocket extends CoreSocket implements NodeSocket {

	private ChannelInitializer<SocketChannel> handler;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;
	private final SocketConfig config;
	private final Log logger;
	private final Set<ConnectionHandler> connectionProcesses;
	
	public CoreNodeSocket(UUID uuid, LocalAtlasNode node, int port, SocketConfig config) {
		super(uuid, node, getLocalHostName(), port);
		this.config = config;
		this.logger = Logging.getLogger("Proxy-" + uuid, "Proxy");
		this.logger.sendToConsole(true);
		this.connectionProcesses = ConcurrentHashMap.newKeySet();
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
		Class<? extends ServerChannel> channelClass;
		IoHandlerFactory bossHandlerFactory;
		IoHandlerFactory workerHandlerFactory;
		if (IoUring.isAvailable()) {
			channelClass = IoUringServerSocketChannel.class;
		} else if (Epoll.isAvailable()) {
			channelClass = EpollServerSocketChannel.class;
		} else if (KQueue.isAvailable()) {
			channelClass = KQueueServerSocketChannel.class;
		} else {
			channelClass = NioServerSocketChannel.class;
		}
		
		bossGroup = new MultiThreadIoEventLoopGroup(bossHandlerFactory);
		workerGroup = new MultiThreadIoEventLoopGroup(workerHandlerFactory);
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(channelClass);
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
	public Log getLogger() {
		return logger;
	}
	
	@Override
	public SocketConfig getConfig() {
		return config;
	}

	@Override
	public boolean isSync() {
		return Atlas.isMainThread();
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

	@Override
	public void tick() {
		if (!connectionProcesses.isEmpty()) {
			connectionProcesses.forEach((handler) -> {
				handler.handleSyncPackets(logger);
			});
		}
	}

	@Override
	public void addSyncConnection(ConnectionHandler con) {
		if (con == null)
			throw new IllegalArgumentException("Connection can not be null!");
		connectionProcesses.add(con);
	}

	@Override
	public void removeSyncConnection(ConnectionHandler con) {
		connectionProcesses.remove(con);
	}
	
}
