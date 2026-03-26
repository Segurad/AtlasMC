package de.atlasmc.io.connection;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import de.atlasmc.io.IOExceptionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketChunker;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.log.Log;
import de.atlasmc.util.codec.CodecContext;

public abstract class AbstractConnectionHandler implements ConnectionHandler {
	
	protected static final PacketListener[] EMPTY = new PacketListener[0];

	protected final Log log;
	protected volatile Protocol protocol;
	protected final Object listenerLock = new Object();
	private volatile PacketListener[] listeners = EMPTY;
	protected volatile int compressionThreshold;
	private volatile IOExceptionHandler errHandler = IOExceptionHandler.UNHANDLED;
	
	public AbstractConnectionHandler(Log log, Protocol protocol) {
		this.log = Objects.requireNonNull(log);
		this.protocol = Objects.requireNonNull(protocol);
	}

	@Override
	public Log getLogger() {
		return log;
	}
	
	@Override
	public void sendPacket(Packet packet) {
		sendPacket(packet, null);
	}

	@Override
	public void sendChunkedPacket(PacketChunker<? extends Packet> chunker) {
		Packet next = chunker.nextChunk();
		if (next == null)
			return;
		sendPacket(next, (_) -> {
			sendChunkedPacket(chunker);
		});
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public void setProtocol(Protocol protocol, PacketListener listener) {
		if (protocol == null) 
			throw new IllegalArgumentException("Protocol can not be null!");
		synchronized (listenerLock) {
			this.protocol = protocol;
			removeAllPacketListener();
			if (listener != null)
				registerPacketListener(listener);
		}
	}
	
	@Override
	public boolean registerPacketListener(PacketListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		synchronized (listenerLock) {
			PacketListener[] listeners = this.listeners;
			listeners = Arrays.copyOf(listeners, listeners.length + 1);
			listeners[listeners.length - 1] = listener;
			this.listeners = listeners;
			log.debug("Registered packet listener: {}", listener.getClass().getSimpleName());
			return true;
		}
	}

	@Override
	public boolean unregisterPacketListener(PacketListener listener) {
		if (listener == null)
			return false;
		synchronized (listenerLock) {
			PacketListener[] listeners = this.listeners;
			if (listeners == null)
				return false;
			final int count = listeners.length;
			for (int i = 0; i < count; i++) {
				if (listeners[i] != listener)
					continue;
				final int newCount = count - 1;
				if (newCount == 0) {
					this.listeners = EMPTY;
				} else {
					PacketListener[] newListeners = Arrays.copyOf(listeners, newCount);
					System.arraycopy(listeners, i+1, newListeners, i, newCount - i);
				}
				return true;
			}
			return false;
		}
	}
	
	@Override
	public void removeAllPacketListener() {
		synchronized (listenerLock) {
			listeners = EMPTY;
		}
	}

	@Override
	public IOExceptionHandler getExceptionHandler() {
		return errHandler;
	}
	
	@Override
	public void setExceptionHandler(IOExceptionHandler handler) {
		this.errHandler = handler != null ? handler : IOExceptionHandler.UNHANDLED;
	}

	@Override
	public boolean handleException(Throwable cause) {
		return errHandler.handle(this, cause);
	}

	@Override
	public void setCompressionThreshold(int threshold) {
		if (threshold < 0)
			threshold = 0;
		if (compressionThreshold == threshold)
			return;
		this.compressionThreshold = threshold;
	}

	@Override
	public int getCompressionThreshold() {
		return compressionThreshold;
	}

	@Override
	public void handlePacket(final Packet packet) throws IOException {
		final PacketListener[] listeners = this.listeners;
		if (listeners == null)
			return;
		final int count = listeners.length;
		for (int i = 0; i < count; i++) {
			listeners[i].handlePacket(packet);
		}
	}

	@Override
	public void handleSyncPackets(final Log logger) {
		final PacketListener[] listeners = this.listeners;
		if (listeners == null)
			return;
		final int count = listeners.length;
		for (int i = 0; i < count; i++) {
			final PacketListener listener = listeners[i];
			if (!listener.hasSyncPackets())
				continue;
			listener.handleSyncPackets(logger);
		}
	}
	
	@Override
	public CodecContext getCodecContext() {
		return CodecContext.DEFAULT_CLIENT;
	}

}
