package de.atlasmc.io.connection;

import java.io.IOException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.io.IOExceptionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketChunker;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.log.Log;
import de.atlasmc.util.codec.CodecContext;

public abstract class AbstractConnectionHandler implements ConnectionHandler {

	protected final Log log;
	protected volatile Protocol protocol;
	protected final DefaultPacketListenerPipeline inboundListeners;
	protected final DefaultPacketListenerPipeline outboundPipeline;
	protected volatile int compressionThreshold;
	private volatile IOExceptionHandler errHandler = IOExceptionHandler.UNHANDLED;
	protected final Queue<Packet> syncPacketQueue;
	protected boolean syncPacketHandling;
	
	public AbstractConnectionHandler(Log log, Protocol protocol) {
		this.log = Objects.requireNonNull(log);
		this.protocol = Objects.requireNonNull(protocol);
		this.syncPacketQueue = new ConcurrentLinkedQueue<>();
		this.inboundListeners = new DefaultPacketListenerPipeline();
		this.outboundPipeline = new DefaultPacketListenerPipeline();
	}
	
	@Override
	public PacketListenerPipeline getInboundListeners() {
		return inboundListeners;
	}
	
	@Override
	public PacketListenerPipeline getOutboundListeners() {
		return outboundPipeline;
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
	public synchronized void setProtocol(Protocol protocol) {
		if (protocol == null) 
			throw new IllegalArgumentException("Protocol can not be null!");
		log.debug("Switching Protocol to: {}", protocol.getClass().getSimpleName());
		this.protocol = protocol;
		syncPacketQueue.clear();
		inboundListeners.removeListeners();
		outboundPipeline.removeListeners();
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
		inboundListeners.handlePacket(this, packet);
		if (packet.isHandled())
			return;
		if (!syncPacketHandling)
			throw new ProtocolException("Unhandled packet: " + packet, protocol, packet);
		syncPacketQueue.add(packet);
	}
	
	@Override
	public int getSyncPacketCount() {
		return syncPacketQueue.size();
	}
	
	@Override
	public Queue<Packet> getSyncPacketQueue() {
		return syncPacketQueue;
	}

	@Override
	public void handleSyncPackets() {
		Packet packet = null;
		while ((packet = syncPacketQueue.poll()) != null) {
			inboundListeners.handlePacket(this, packet);
			if (!packet.isHandled()) {
				throw new ProtocolException("Unhandled packet: " + packet, protocol, packet);
			}
		}
	}
	
	@Override
	public CodecContext getCodecContext() {
		return CodecContext.DEFAULT_CLIENT;
	}
	
	@Override
	public void setSyncPacketHandling(boolean enable) {
		this.syncPacketHandling = true;
	}
	
	@Override
	public boolean hasSyncPacketHandling() {
		return syncPacketHandling;
	}

}
