package de.atlascore.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.crypto.SecretKey;

import de.atlascore.io.netty.channel.ErrorHandler;
import de.atlascore.io.netty.channel.PacketCompressort;
import de.atlascore.io.netty.channel.PacketDecoder;
import de.atlascore.io.netty.channel.PacketDecompressor;
import de.atlascore.io.netty.channel.PacketDecryptor;
import de.atlascore.io.netty.channel.PacketEncoder;
import de.atlascore.io.netty.channel.PacketEncryptor;
import de.atlascore.io.netty.channel.PacketProcessor;
import de.atlascore.io.netty.channel.PacketLengthDecoder;
import de.atlascore.io.netty.channel.PacketLengthEncoder;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.IOExceptionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketChunker;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@ThreadSafe
public abstract class CoreConnectionHandler implements ConnectionHandler {
	
	protected static final String
	CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER = "outbound_exception_handler",
	CHANNEL_PIPE_PACKET_DECRYPTOR = "packet_decryptor",
	CHANNEL_PIPE_PACKET_ENCRYPTOR = "packet_encryptor",
	CHANNEL_PIPE_PACKET_LENGTH_DECODER = "packet_length_decoder",
	CHANNEL_PIPE_PACKET_DECOMPRESSOR = "packet_decompressor",
	CHANNEL_PIPE_PACKET_LENGTH_ENCODER = "packet_length_encoder",
	CHANNEL_PIPE_PACKET_COMPRESSOR = "packet_compressor",
	CHANNEL_PIPE_PACKET_DECODER = "packet_decoder",
	CHANNEL_PIPE_PACKET_ENCODER = "packet_encoder",
	CHANNEL_PIPE_PACKET_PROCESSOR = "packet_processor",
	CHANNEL_PIPE_INBOUND_EXCEPTION_HANDLER = "inbound_exception_handler";
	
	/** 
	 * Channel default pipeline
	 *  <table>
	 *  <tr><th>Optional</th><th>Direction	</th><th>Name</th></tr>
	 *  <tr><td> 		</td><td>OUT		</td><td>outbound_exception_handler	</td></tr>
	 *  <tr><td>X		</td><td>IN 	  	</td><td>packet_decryptor 			</td></tr>
	 *  <tr><td>X		</td><td>OUT		</td><td>packet_encryptor 			</td></tr>
	 *  <tr><td> 		</td><td>IN 		</td><td>packet_length_decoder		</td></tr>
	 *  <tr><td>X		</td><td>IN 		</td><td>packet_decompressor		</td></tr>
	 *  <tr><td> 		</td><td>OUT		</td><td>packet_length_encoder		</td></tr>
	 *  <tr><td>X		</td><td>OUT		</td><td>packet_compressor			</td></tr>
	 *  <tr><td>		</td><td>IN 		</td><td>packet_decoder				</td></tr>
	 *  <tr><td>		</td><td>OUT		</td><td>packet_encoder				</td></tr>
	 *  <tr><td>		</td><td>IN 		</td><td>packet_processor			</td></tr>
	 *  <tr><td>		</td><td>IN 		</td><td>inbound_exception_handler 	</td></tr>
	 *  </table>
	 *  "packet_decryptor" and "packet_encryptor" are only present if {@link #enableEncryption(byte[])} has been called<br>
	 *  "packet_decompressor" and "packet_compressor" are only present if {@link #setCompression(int)} has been called with a value > 0
	 */
	protected final SocketChannel channel;
	private volatile boolean encryption;
	private volatile int compressionThreshold;
	private volatile Protocol protocol;
	private volatile IOExceptionHandler errHandler;
	private final Object listenerLock = new Object();
	private PacketListener[] listeners;
	private int listenerCount;
	private final Queue<Object> queue; // contains Packet and FuturePacket
	
	public CoreConnectionHandler(SocketChannel channel) {
		this(channel, HandshakeProtocol.DEFAULT_PROTOCOL);
		registerPacketListener(HandshakeProtocol.DEFAULT_PROTOCOL.createDefaultPacketListener(this));
	}
	
	public CoreConnectionHandler(SocketChannel channel, Protocol protocol) {
		queue = new ConcurrentLinkedQueue<>();
		this.channel = channel;
		this.listeners = new PacketListener[2];
		this.protocol = protocol;
		this.errHandler = IOExceptionHandler.UNHANDLED;
		
		channel.pipeline()
		.addLast(CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER, new ErrorHandler("Error while handling outbound packet!", this))
		.addLast(CHANNEL_PIPE_PACKET_LENGTH_DECODER, new PacketLengthDecoder())
		.addLast(CHANNEL_PIPE_PACKET_LENGTH_ENCODER, PacketLengthEncoder.INSTANCE)
		.addLast(CHANNEL_PIPE_PACKET_DECODER, new PacketDecoder(this))
		.addLast(CHANNEL_PIPE_PACKET_ENCODER, new PacketEncoder(this))
		.addLast(CHANNEL_PIPE_PACKET_PROCESSOR, new PacketProcessor(this))
		.addLast(CHANNEL_PIPE_INBOUND_EXCEPTION_HANDLER, new ErrorHandler("Error while handling inbound packet!", this))
		;
	}
	
	public InetSocketAddress getRemoteAddress() {
		return channel.remoteAddress();
	}
	
	public void sendPacket(Packet packet) {
		sendPacket(packet, null);
	}
	
	public void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		if (isClosed())
			return;
		if (channel.isActive()) {
			writeQueuedPackets();
			writePacket(packet, listener);
		} else queuePacket(packet, listener);
	}
	
	public void sendChunkedPacket(PacketChunker<? extends Packet> chunker) {
		Packet next = chunker.nextChunk();
		if (next == null)
			return;
		sendPacket(next, (future) -> {
			sendChunkedPacket(chunker);
		});
	}
	
	private void writePacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		if (!channel.isActive()) {
			queuePacket(packet, listener);
			return;
		}
		ChannelFuture future = channel.writeAndFlush(packet);
		if (listener != null)
			future.addListener(listener);
	}
	
	private void queuePacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		if (listener == null) {
			queue.add(packet);
		} else
			queue.add(new FuturePacket(packet, listener));
	}

	public void writeQueuedPackets() {
		if (!channel.isActive() || queue.isEmpty())
			return;
		synchronized (queue) {
			Object element = null;
			while ((element = queue.poll()) != null) {
				if (element instanceof Packet)
					writePacket((Packet) element, null);
				else {
					FuturePacket future = (FuturePacket) element;
					writePacket(future.packet, future.listener);
				}
			}
		}
	}

	public boolean hasQueued() {
		return !queue.isEmpty();
	}
	
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
	
	public Protocol getProtocol() {
		return protocol;
	}
	
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
			return true;
		}
	}
	
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
	
	public void removeAllPacketListener() {
		synchronized (listenerLock) {
			for (int i = 0; i < listenerCount; i++) {
				listeners[i].handleUnregister();
				listeners[i] = null;
			}
			listenerCount = 0;
		}
	}

	public void close() {
		if (isClosed())
			return;
		channel.config().setAutoRead(false);
		channel.close();
		queue.clear();
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
	
	public void enableEncryption(SecretKey secret) throws InvalidKeyException, InvalidAlgorithmParameterException {
		if (isEncryotionEnabled())
			throw new IllegalStateException("Encryption already enabled");
		synchronized (this) {
			if (isEncryotionEnabled())
				throw new IllegalStateException("Encryption already enabled");
			channel.pipeline()
			.addAfter(CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER, CHANNEL_PIPE_PACKET_ENCRYPTOR, new PacketEncryptor(secret))
			.addAfter(CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER, CHANNEL_PIPE_PACKET_DECRYPTOR, new PacketDecryptor(secret));
			encryption = true;
		}
	}
	
	public boolean isEncryotionEnabled() {
		return encryption;
	}
	
	public void setCompression(int threshold) {
		if (threshold < 0)
			threshold = 0;
		if (compressionThreshold == threshold)
			return;
		synchronized (this) {
			if (compressionThreshold == threshold)
				return;
			compressionThreshold = threshold;
			if (threshold == 0) {
				ChannelPipeline pipe = channel.pipeline();
				pipe.remove(CHANNEL_PIPE_PACKET_COMPRESSOR);
				pipe.remove(CHANNEL_PIPE_PACKET_DECOMPRESSOR);
			} else {
				channel.pipeline()
				.addAfter(CHANNEL_PIPE_PACKET_LENGTH_DECODER, CHANNEL_PIPE_PACKET_DECOMPRESSOR, new PacketDecompressor())
				.addAfter(CHANNEL_PIPE_PACKET_LENGTH_ENCODER, CHANNEL_PIPE_PACKET_COMPRESSOR, new PacketCompressort(threshold));
			}
		}
	}
	
	public int getCompressionThreshold() {
		return compressionThreshold;
	}
	
	public void handlePacket(Packet packet) throws IOException {
		synchronized (listenerLock) {
			for (int i = 0; i < listenerCount; i++)
				listeners[i].handlePacket(packet);
		}
	}
	
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
	
	public abstract Log getLogger();
	
	static class FuturePacket {
		
		private final Packet packet;
		private final GenericFutureListener<? extends Future<? super Void>> listener;
		
		public FuturePacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
			this.packet = packet;
			this.listener = listener;
		}
		
	}

}
