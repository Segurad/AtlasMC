package de.atlasmc.io.socket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.io.connection.ConnectionConfig;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.configuration.MemoryConfiguration;
import io.netty.channel.ChannelOption;
import io.netty.channel.IoHandlerFactory;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollIoHandler;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.uring.IoUringIoHandler;
import io.netty.channel.uring.IoUringServerSocketChannel;
import io.netty.channel.uring.IoUringSocketChannel;

public class SocketConfig implements ConfigurationSerializable {
	
	private String name;
	private ChannelType type;
	private int bossThreads;
	private int workerThreads;
	private Map<ChannelOption<?>, Object> channelOptions;
	private Map<ChannelOption<?>, Object> childChannelOptions;
	private int port;
	private String host;
	private Map<String, ConnectionConfig> conConfigs;
	private Configuration config;
	
	public SocketConfig(ConfigurationSection config) {
		this.name = config.getString("name");
		String rawChannel = config.getString("channel-type");
		this.type = rawChannel != null ? ChannelType.valueOf(rawChannel) : ChannelType.getDefault();
		this.channelOptions = buildChannelOptions(config.getConfigurationSection("channel-options"));
		this.childChannelOptions = buildChannelOptions(config.getConfigurationSection("child-channel-options"));
		this.bossThreads = config.getInt("boss-threads");
		this.workerThreads = config.getInt("worker-threads");
		this.port = config.getInt("port");
		this.host = config.getString("host");
		this.conConfigs = new HashMap<>();
		List<ConfigurationSection> conConfigs = config.getConfigurationList("connection-config");
		for (ConfigurationSection conConfig : conConfigs) {
			ConnectionConfig cfg = ConfigurationSerializable.deserializeSafe(conConfig, ConnectionConfig.class);
			this.conConfigs.put(cfg.getName(), cfg);
		}
		this.config = new MemoryConfiguration(config);
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getName() {
		return name;
	}
	
	public ChannelType getType() {
		return type;
	}
	
	public int getBossThreads() {
		return bossThreads;
	}
	
	public int getWorkerThreads() {
		return workerThreads;
	}
	
	public Map<ChannelOption<?>, Object> getChannelOptions() {
		return channelOptions;
	}
	
	public Map<ChannelOption<?>, Object> getChildChannelOptions() {
		return childChannelOptions;
	}
	
	public int getPort() {
		return port;
	}
	
	private static Map<ChannelOption<?>, Object> buildChannelOptions(ConfigurationSection config) {
		Map<ChannelOption<?>, Object> options = new HashMap<>();
		if (config == null)
			return options;
		for (Entry<String, Object> entry : config.asMap().entrySet()) {
			String key = entry.getKey();
			if (!ChannelOption.exists(key))
				continue;
			ChannelOption<?> option = ChannelOption.valueOf(key);
			options.put(option, entry.getValue());
		}
		return options;
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T config) {
		config.addConfiguration(config);
		return config;
	}
	
	public static enum ChannelType {
		
		IO_URING(IoUringServerSocketChannel.class, IoUringSocketChannel.class, IoUringIoHandler.newFactory()),
		EPOLL(EpollServerSocketChannel.class, EpollSocketChannel.class, EpollIoHandler.newFactory()),
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
			case NIO:
				return true;
			default:
				return false;
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

	@SuppressWarnings("unchecked")
	public <T extends ConnectionConfig> T getConnectionConfig(String key) {
		return (T) conConfigs.get(key);
	}

}
