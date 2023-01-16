package de.atlascore.io;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlascore.io.netty.channel.PacketCompressort;
import de.atlascore.io.netty.channel.PacketDecoder;
import de.atlascore.io.netty.channel.PacketDecompressor;
import de.atlascore.io.netty.channel.PacketDecryptor;
import de.atlascore.io.netty.channel.PacketEncoder;
import de.atlascore.io.netty.channel.PacketEncryptor;
import de.atlascore.io.netty.channel.PacketProcessor;
import de.atlascore.io.netty.channel.PacketLengthDecoder;
import de.atlascore.io.netty.channel.PacketLengthEncoder;
import de.atlasmc.io.IOExceptionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

@ThreadSafe
public class ConnectionHandler {
	
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
	private final Vector<PacketListener> packetListeners;
	private final Queue<Object> queue; // contains Packet and FuturePacket
	
	public ConnectionHandler(SocketChannel channel) {
		this(channel, HandshakeProtocol.DEFAULT_PROTOCOL);
		registerPacketListener(HandshakeProtocol.DEFAULT_PROTOCOL.createDefaultPacketListener(this));
	}
	
	public ConnectionHandler(SocketChannel channel, Protocol protocol) {
		queue = new ConcurrentLinkedQueue<>();
		this.channel = channel;
		this.packetListeners = new Vector<PacketListener>();
		this.protocol = protocol;
		this.errHandler = IOExceptionHandler.UNHANDLED;
		
		channel.pipeline()
		//.addLast(CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER, null) // TODO add outbound exception handler
		.addLast(CHANNEL_PIPE_PACKET_LENGTH_DECODER, new PacketLengthDecoder())
		.addLast(CHANNEL_PIPE_PACKET_LENGTH_ENCODER, PacketLengthEncoder.INSTANCE)
		.addLast(CHANNEL_PIPE_PACKET_DECODER, new PacketDecoder(this))
		.addLast(CHANNEL_PIPE_PACKET_ENCODER, new PacketEncoder(this))
		.addLast(CHANNEL_PIPE_PACKET_PROCESSOR, new PacketProcessor(this));
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
	
	public void enableEncryption(byte[] secret) {
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
	
	static class FuturePacket {
		
		private final Packet packet;
		private final GenericFutureListener<? extends Future<? super Void>> listener;
		
		public FuturePacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
			this.packet = packet;
			this.listener = listener;
		}
		
	}

}
