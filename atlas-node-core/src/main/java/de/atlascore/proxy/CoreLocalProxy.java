package de.atlascore.proxy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.proxy.LocalProxy;
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
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;
	private final ProxyConfig config;
	private final Log logger;
	private final Set<ProxyConnectionHandler> connectionProcesses;
	
	public CoreLocalProxy(UUID uuid, LocalAtlasNode node, int port, ProxyConfig config) {
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
	public Log getLogger() {
		return logger;
	}
	
	@Override
	public ProxyConfig getConfig() {
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
	public void addSyncConnection(ProxyConnectionHandler con) {
		if (con == null)
			throw new IllegalArgumentException("Connection can not be null!");
		if (con.getProxy() != this)
			throw new IllegalArgumentException("Connection does not belong to this proxy");
		connectionProcesses.add(con);
	}

	@Override
	public void removeSyncConnection(ProxyConnectionHandler con) {
		connectionProcesses.remove(con);
	}
	
}
