package de.atlasmc.io.socket;

import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.configuration.MemoryConfiguration;
import io.netty.channel.IoHandlerFactory;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollIoHandler;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.uring.IoUringIoHandler;
import io.netty.channel.uring.IoUringServerSocketChannel;
import io.netty.channel.uring.IoUringSocketChannel;

public class SocketConfig implements ConfigurationSerializable {
	
	private Configuration config;
	
	public SocketConfig(ConfigurationSection config) {
		this.config = new MemoryConfiguration(config);
	}
	
	public ChannelType getType() {
		String raw = config.getString("channel-type");
		if (raw == null)
			return ChannelType.getDefault();
		return ChannelType.valueOf(raw);
	}
	
	public int getBossThreads() {
		return config.getInt("boss-threads");
	}
	
	public int getWorkerThreads() {
		return config.getInt("worker-threads");
	}
	
	public ConfigurationSection getChannelOptions() {
		return config.getConfigurationSection("channel-options");
	}
	
	public ConfigurationSection getChildChannelOptions() {
		return config.getConfigurationSection("child-channel-options");
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T config) {
		config.addConfiguration(config);
		return config;
	}
	
	public static enum ChannelType {
		
		IO_URING(IoUringServerSocketChannel.class, IoUringSocketChannel.class, IoUringIoHandler.newFactory()),
		EPOLL(EpollServerSocketChannel.class, EpollSocketChannel.class, EpollIoHandler.newFactory()),
		K_QUEUE(KQueueServerSocketChannel.class, KQueueSocketChannel.class, KQueueIoHandler.newFactory()),
		NIO(NioServerSocketChannel.class, NioSocketChannel.class, NioIoHandler.newFactory());
		
		private final Class<? extends ServerSocketChannel> serverSocektChannelClass;
		private final Class<? extends SocketChannel> socketChannelClass;
		private final IoHandlerFactory defaultHandlerFactory;
		
		private ChannelType(Class<? extends ServerSocketChannel> serverChannelClass, Class<? extends SocketChannel> channelClass, IoHandlerFactory defaultHandlerFactory) {
			this.serverSocektChannelClass = serverChannelClass;
			this.socketChannelClass = channelClass;
			this.defaultHandlerFactory = defaultHandlerFactory;
		}
		
		public Class<? extends ServerSocketChannel> getServerSocektChannelClass() {
			return serverSocektChannelClass;
		}
		
		public Class<? extends SocketChannel> getSocketChannelClass() {
			return socketChannelClass;
		}
		
		public boolean isAvailable() {
			switch(this) {
			case IO_URING:
				return IO_URING.isAvailable();
			case EPOLL:
				return Epoll.isAvailable();
			case K_QUEUE:
				return KQueue.isAvailable();
			default:
				return true;
			}
		}
		
		public IoHandlerFactory getDefaultFactory() {
			return defaultHandlerFactory;
		}
		
		public static ChannelType getDefault() {
			for (ChannelType type : values()) {
				if (type.isAvailable())
					return type;
			}
			return NIO;
		}
		
	}

}
