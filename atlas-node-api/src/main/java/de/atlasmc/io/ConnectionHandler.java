package de.atlasmc.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.SecretKey;

import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@ThreadSafe
public interface ConnectionHandler {
	
	InetSocketAddress getRemoteAddress();
	
	void sendPacket(Packet packet);
	
	void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener);
	
	void sendChunkedPacket(PacketChunker<? extends Packet> chunker);

	void writeQueuedPackets();

	boolean hasQueued();
	
	void setProtocol(Protocol protocol, PacketListener listener);
	
	Protocol getProtocol();
	
	boolean registerPacketListener(PacketListener listener);
	
	boolean unregisterPacketListener(PacketListener listener);
	
	void removeAllPacketListener();

	void close();
	
	boolean isClosed();
	
	IOExceptionHandler getExceptionHandler();
	
	void setExceptionHandler(IOExceptionHandler handler);

	boolean handleException(Throwable cause);
	
	void enableEncryption(SecretKey secret) throws InvalidKeyException, InvalidAlgorithmParameterException;
	
	boolean isEncryotionEnabled();
	
	void setCompression(int threshold);
	
	int getCompressionThreshold();
	
	void handlePacket(Packet packet) throws IOException;
	
	void handleSyncPackets(Log logger);
	
	Log getLogger();

}
