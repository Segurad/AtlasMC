package de.atlasmc.io.socket;

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

public class EventLoopConfiguration {
	
	
	
	public static enum EventLoopType {
		
		IO_URING(IoUringServerSocketChannel.class, IoUringSocketChannel.class, IoUringIoHandler.newFactory()),
		EPOLL(EpollServerSocketChannel.class, EpollSocketChannel.class, EpollIoHandler.newFactory()),
		K_QUEUE(KQueueServerSocketChannel.class, KQueueSocketChannel.class, KQueueIoHandler.newFactory()),
		NIO(NioServerSocketChannel.class, NioSocketChannel.class, NioIoHandler.newFactory());
		
		private final Class<? extends ServerSocketChannel> serverSocektChannelClass;
		private final Class<? extends SocketChannel> socketChannelClass;
		private final IoHandlerFactory defaultHandlerFactory;
		
		private EventLoopType(Class<? extends ServerSocketChannel> serverChannelClass, Class<? extends SocketChannel> channelClass, IoHandlerFactory defaultHandlerFactory) {
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
		
	}

}
