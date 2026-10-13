package de.atlasmc.io.socket;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;

import de.atlasmc.io.connection.ConnectionConfig;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationException;
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
import io.netty.channel.uring.IoUring;
import io.netty.channel.uring.IoUringIoHandler;
import io.netty.channel.uring.IoUringServerSocketChannel;
import io.netty.channel.uring.IoUringSocketChannel;

public class SocketConfig implements ConfigurationSerializable {
	
	private Map<ChannelOption<?>, Object> channelOptions;
	private Map<ChannelOption<?>, Object> childChannelOptions;
	private Map<String, ConnectionConfig> conConfigs;
	private Configuration config;
	private InetSocketAddress host;
	
	public SocketConfig(ConfigurationSection config) {
		this.channelOptions = buildChannelOptions(config.getConfigurationSection("channel-options"));
		this.childChannelOptions = buildChannelOptions(config.getConfigurationSection("child-channel-options"));
		this.conConfigs = new HashMap<>();
		var host = config.getString("host", "0.0.0.0");
		var port = config.getInt("port", -1);
		if (port == -1)
			throw new ConfigurationException("Port not set!");
		this.host = InetSocketAddress.createUnresolved(host, port);
		List<ConfigurationSection> conConfigs = config.getConfigurationList("connection-config");
		for (ConfigurationSection conConfig : conConfigs) {
			ConnectionConfig cfg = ConfigurationSerializable.deserializeSafe(conConfig, ConnectionConfig.class);
			this.conConfigs.put(cfg.getName(), cfg);
		}
		this.config = new MemoryConfiguration(config);
	}
	
	public InetSocketAddress getHost() {
		return host;
	}
	
	public void setHost(InetSocketAddress host) {
		this.host = Objects.requireNonNull(host, "host");
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	public String getName() {
		return config.getString("name");
	}
	
	public ChannelType getType() {
		String rawChannel = config.getString("channel-type");
		return rawChannel != null ? ChannelType.valueOf(rawChannel) : ChannelType.getDefault();
	}
	
	public int getBossThreads() {
		return config.getInt("boss-threads", 1);
	}
	
	public int getWorkerThreads() {
		return config.getInt("worker-threads", 2);
	}
	
	public Map<ChannelOption<?>, Object> getChannelOptions() {
		return channelOptions;
	}
	
	public void setChannelOptions(Map<ChannelOption<?>, Object> channelOptions) {
		this.channelOptions = channelOptions;
	}
	
	public Map<ChannelOption<?>, Object> getChildChannelOptions() {
		return childChannelOptions;
	}
	
	public void setChildChannelOptions(Map<ChannelOption<?>, Object> childChannelOptions) {
		this.childChannelOptions = childChannelOptions;
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
		
		IO_URING(IoUringServerSocketChannel.class, IoUringSocketChannel.class, IoUringIoHandler::newFactory),
		EPOLL(EpollServerSocketChannel.class, EpollSocketChannel.class, EpollIoHandler::newFactory),
		NIO(NioServerSocketChannel.class, NioSocketChannel.class, NioIoHandler::newFactory);
		
		private final Class<? extends ServerSocketChannel> serverSocektChannelClass;
		private final Class<? extends SocketChannel> socketChannelClass;
		private final Supplier<IoHandlerFactory> defaultHandlerFactory;
		
		private ChannelType(Class<? extends ServerSocketChannel> serverChannelClass, Class<? extends SocketChannel> channelClass, Supplier<IoHandlerFactory> defaultHandlerFactory) {
			this.serverSocektChannelClass = serverChannelClass;
			this.socketChannelClass = channelClass;
			this.defaultHandlerFactory = defaultHandlerFactory;
		}
		
		public Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
			return serverSocektChannelClass;
		}
		
		public Class<? extends SocketChannel> getSocketChannelClass() {
			return socketChannelClass;
		}
		
		public boolean isAvailable() {
			switch(this) {
			case IO_URING:
				return IoUring.isAvailable();
			case EPOLL:
				return Epoll.isAvailable();
			case NIO:
				return true;
			default:
				return false;
			}
		}
		
		public IoHandlerFactory getDefaultFactory() {
			return defaultHandlerFactory.get();
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
