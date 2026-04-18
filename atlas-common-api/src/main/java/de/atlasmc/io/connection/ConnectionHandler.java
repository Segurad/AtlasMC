package de.atlasmc.io.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Queue;

import javax.crypto.SecretKey;

import de.atlasmc.io.IOExceptionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketChunker;
import de.atlasmc.io.Protocol;
import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.codec.CodecContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@ThreadSafe
public interface ConnectionHandler {
	
	InetSocketAddress getRemoteAddress();
	
	/**
	 * Sends a packet
	 * @param packet
	 */
	void sendPacket(Packet packet);
	
	/**
	 * Sends a packet and invokes the given
	 * @param packet
	 * @param listener
	 */
	void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener);
	
	void sendChunkedPacket(PacketChunker<? extends Packet> chunker);
	
	void setProtocol(Protocol protocol);
	
	@NotNull
	Protocol getProtocol();
	
	/**
	 * Returns the inbound listener pipeline.
	 * This pipeline is used to handle inbound packages.
	 * At least one listener in this pipeline must mark a inbound package as handled or a exception is thrown.
	 * @return pipeline
	 */
	@NotNull
	PacketListenerPipeline getInboundListeners();
	
	/**
	 * Returns the outbound listener pipeline.
	 * This pipeline is used to handle outbound packages.
	 * If a outbound packet is marked as handled after passing this pipeline it will not be send.
	 * @return pipeline
	 */
	@NotNull
	PacketListenerPipeline getOutboundListeners();

	void close();
	
	boolean isClosed();
	
	IOExceptionHandler getExceptionHandler();
	
	void setExceptionHandler(IOExceptionHandler handler);

	boolean handleException(Throwable cause);
	
	void enableEncryption(SecretKey secret) throws InvalidKeyException, InvalidAlgorithmParameterException;
	
	boolean isEncryotionEnabled();
	
	void setCompressionThreshold(int threshold);
	
	int getCompressionThreshold();
	
	void setDecompression(boolean enable);
	
	boolean hasCompression();
	
	void setCompression(boolean enbale);
	
	boolean hasDecompression();
	
	void handlePacket(Packet packet) throws IOException;
	
	/**
	 * Sets whether or not this connection handles sync packets
	 * @param enable
	 */
	void setSyncPacketHandling(boolean enable);
	
	/**
	 * Whether or not this connection handles sync packets
	 * @return true if enabled
	 */
	boolean hasSyncPacketHandling();
	
	default boolean hasSyncPackets() {
		return getSyncPacketCount() > 0;
	}
	
	/**
	 * Number of sync packets
	 * @return count
	 */
	int getSyncPacketCount();
	
	/**
	 * Handles all queued sync packets using the listeners
	 */
	void handleSyncPackets();
	
	/**
	 * Returns the queue containing all packets that need to be handled synchronous
	 * @return queue
	 */
	@NotNull
	Queue<Packet> getSyncPacketQueue();
	
	CodecContext getCodecContext();
	
	@NotNull
	Log getLogger();
	
	boolean isInboundTerminated();
	
	boolean isOutboundTerminated();

}
