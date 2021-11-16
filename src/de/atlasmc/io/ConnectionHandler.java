package de.atlasmc.io;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.channel.socket.SocketChannel;

@ThreadSafe
public class ConnectionHandler {
	
	private final Queue<Packet> queue;
	private final SocketChannel channel;
	private volatile Protocol protocol;
	private volatile IOExceptionHandler errHandler;
	private final Vector<PacketListener> packetListeners;
	private final LocalProxy proxy;
	
	public ConnectionHandler(SocketChannel channel, LocalProxy proxy) {
		this(channel, proxy, HandshakeProtocol.DEFAULT_PROTOCOL);
		registerPacketListener(HandshakeProtocol.DEFAULT_PROTOCOL.createDefaultPacketListener(this));
	}
	
	public ConnectionHandler(SocketChannel channel, LocalProxy proxy, Protocol protocol) {
		queue = new ConcurrentLinkedQueue<Packet>();
		this.channel = channel;
		this.packetListeners = new Vector<PacketListener>();
		this.proxy = proxy;
		this.protocol = protocol;
		this.errHandler = IOExceptionHandler.UNHANDLED;
	}
	
	public LocalProxy getProxy() {
		return proxy;
	}
	
	public void sendPacket(Packet packet) {
		Protocol prot = getProtocol();
		if (packet.getVersion() != prot.getVersion()) {
			packet = prot.createCopy(packet);
		}
		if (channel.isActive()) {
			channel.writeAndFlush(packet);
		} else {
			queue.add(packet);
		}
	}

	public void writeQueued() {
		while (!queue.isEmpty()) {
			channel.writeAndFlush(queue.poll());
		}
	}

	public boolean hasQueued() {
		return !queue.isEmpty();
	}
	
	public synchronized void setProtocol(Protocol protocol, PacketListener listener) {
		if (protocol == null) throw new IllegalArgumentException("Protocol can not be null!");
		this.protocol = protocol;
		packetListeners.removeAllElements();
		if (listener != null)
		packetListeners.add(listener);
	}
	
	public Protocol getProtocol() {
		return protocol;
	}
	
	public void registerPacketListener(PacketListener listener) {
		packetListeners.add(0, listener);
	}
	
	public void unregisterPacketListener(PacketListener listener) {
		if (packetListeners.remove(listener)) listener.handleUnregister();
	}
	
	public void removeAllPacketListener() {
		packetListeners.removeAllElements();
	}
	
	public Vector<PacketListener> getPacketListeners() {
		return packetListeners;
	}

	public void close() {
		channel.close();
	}
	
	public boolean isClosed() {
		return !channel.isOpen();
	}
	
	public IOExceptionHandler getExceptionHandler() {
		return errHandler;
	}
	
	public void setExceptionHandler(IOExceptionHandler handler) {
		this.errHandler = handler != null ? handler : IOExceptionHandler.UNHANDLED;
	}

	public boolean handleException(Throwable cause) {
		return errHandler.handle(this, cause);
	}

}
