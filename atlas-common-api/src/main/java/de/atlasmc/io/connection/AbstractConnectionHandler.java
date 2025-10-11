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

public abstract class AbstractConnectionHandler implements ConnectionHandler {

	protected final Log log;
	protected volatile Protocol protocol;
	protected final Object listenerLock = new Object();
	private int listenerCount;
	private PacketListener[] listeners;
	protected volatile int compressionThreshold;
	private volatile IOExceptionHandler errHandler;
	
	public AbstractConnectionHandler(Log log, Protocol protocol) {
		this.log = Objects.requireNonNull(log);
		this.protocol = Objects.requireNonNull(protocol);
		this.listeners = new PacketListener[2];
		this.errHandler = IOExceptionHandler.UNHANDLED;
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
		sendPacket(next, (future) -> {
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
			for (int i = 0; i < listenerCount; i++) { // check present
				if (listeners[i] == listener)
					return false;
			}
			if (listeners.length == listenerCount) { // grow listener array
				listeners = Arrays.copyOf(listeners, listenerCount << 1);
			}
			// add listener
			listeners[listenerCount++] = listener;
			log.debug("Registered packet listener: {}", listener.getClass().getSimpleName());
			return true;
		}
	}

	@Override
	public boolean unregisterPacketListener(PacketListener listener) {
		if (listener == null)
			return false;
		synchronized (listenerLock) {
			for (int i = 0; i < listenerCount; i++) {
				if (listeners[i] != listener)
					continue;
				listeners[i] = null;
				listenerCount--;
				if (listenerCount > i)
					System.arraycopy(listeners, i+1, listeners, i, listenerCount);
				return true;
			}
			return false;
		}
	}
	
	@Override
	public void removeAllPacketListener() {
		synchronized (listenerLock) {
			for (int i = 0; i < listenerCount; i++) {
				listeners[i].handleUnregister();
				listeners[i] = null;
			}
			listenerCount = 0;
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
	public void handlePacket(Packet packet) throws IOException {
		synchronized (listenerLock) {
			for (int i = 0; i < listenerCount; i++)
				listeners[i].handlePacket(packet);
		}
	}

	@Override
	public void handleSyncPackets(Log logger) {
		if (listenerCount == 0)
			return;
		Protocol protocol = this.protocol;
		int index = 0;
		do {
			PacketListener nextListener;
			synchronized (listenerLock) { // search next listener
				if (this.protocol != protocol)
					return; // protocol has changed so cancel execution
				do {
					nextListener = listeners[index++];
					if (nextListener == null)
						return;
					if (nextListener.hasSyncPackets())
						break;
					if (index == -1)
						return;
				} while (true);
			}
			nextListener.handleSyncPackets(logger); // handle packet without locking listeners
		} while (index != -1);
	}

}
