package de.atlasmc.io.connection;

import java.net.InetSocketAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.crypto.SecretKey;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.netty.channel.InboundErrorHandler;
import de.atlasmc.io.netty.channel.OutboundErrorHandler;
import de.atlasmc.io.netty.channel.PacketCompressor;
import de.atlasmc.io.netty.channel.PacketDecoder;
import de.atlasmc.io.netty.channel.PacketDecompressor;
import de.atlasmc.io.netty.channel.PacketDecryptor;
import de.atlasmc.io.netty.channel.PacketEncoder;
import de.atlasmc.io.netty.channel.PacketEncryptor;
import de.atlasmc.io.netty.channel.PacketLengthDecoder;
import de.atlasmc.io.netty.channel.PacketLengthEncoder;
import de.atlasmc.io.netty.channel.PacketProcessor;
import de.atlasmc.io.protocol.handshake.HandshakeProtocol;
import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.flow.FlowControlHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Connection handler implementation for {@link SocketChannel}
 */
@ThreadSafe
public class SocketConnectionHandler extends AbstractConnectionHandler {
	
	public static final String
	CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER = "outbound_exception_handler",
	CHANNEL_PIPE_PACKET_DECRYPTOR = "packet_decryptor",
	CHANNEL_PIPE_PACKET_ENCRYPTOR = "packet_encryptor",
	CHANNEL_PIPE_PACKET_LENGTH_DECODER = "packet_length_decoder",
	CHANNEL_PIPE_FLOW_CONTROLLER = "flow_controller",
	CHANNEL_PIPE_PACKET_DECOMPRESSOR = "packet_decompressor",
	CHANNEL_PIPE_PACKET_LENGTH_ENCODER = "packet_length_encoder",
	CHANNEL_PIPE_PACKET_COMPRESSOR = "packet_compressor",
	CHANNEL_PIPE_INBOUND_NO_PROTOCOL = "inbound_no_protocol",
	CHANNEL_PIPE_PACKET_DECODER = "packet_decoder",
	CHANNEL_PIPE_PACKET_ENCODER = "packet_encoder",
	CHANNEL_PIPE_OUTBOUND_NO_PROTOCOL = "outbound_no_protocol",
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
	 *  <tr><td>		</td><td>IN			</td><td>flow_controller			</td></tr>
	 *  <tr><td>X		</td><td>IN 		</td><td>packet_decompressor		</td></tr>
	 *  <tr><td> 		</td><td>OUT		</td><td>packet_length_encoder		</td></tr>
	 *  <tr><td>X		</td><td>OUT		</td><td>packet_compressor			</td></tr>
	 *  <tr><td>X		</td><td>IN			</td><td>inbound_no_protocol		</td></tr>
	 *  <tr><td>		</td><td>IN 		</td><td>packet_decoder				</td></tr>
	 *  <tr><td>		</td><td>OUT		</td><td>packet_encoder				</td></tr>
	 *  <tr><td>X		</td><td>OUT		</td><td>outbound_no_protocol		</td></tr>
	 *  <tr><td>		</td><td>IN 		</td><td>packet_processor			</td></tr>
	 *  <tr><td>		</td><td>IN 		</td><td>inbound_exception_handler 	</td></tr>
	 *  </table>
	 *  "packet_decryptor" and "packet_encryptor" are only present if {@link #enableEncryption(byte[])} has been called<br>
	 *  "packet_decompressor" and "packet_compressor" are only present if {@link #setCompression(int)} has been called with a value > 0
	 */
	protected final SocketChannel channel;
	private volatile boolean encryption;
	private final Queue<Object> queue; // contains Packet and FuturePacket
	private volatile PacketDecompressor decompressor; // use lock over this
	private volatile PacketCompressor compressor; // use lock over this
	
	public SocketConnectionHandler(SocketChannel channel, Log log) {
		this(channel, log, HandshakeProtocol.DEFAULT_PROTOCOL);
		registerPacketListener(HandshakeProtocol.DEFAULT_PROTOCOL.createDefaultPacketListenerIn(this));
	}
	
	public SocketConnectionHandler(SocketChannel channel, Log log, Protocol protocol) {
		super(log, protocol);
		queue = new ConcurrentLinkedQueue<>();
		this.channel = Objects.requireNonNull(channel);
		
		channel.pipeline()
		.addLast(CHANNEL_PIPE_OUTBOUND_EXCEPTION_HANDLER, new OutboundErrorHandler(this.getLogger()))
		.addLast(CHANNEL_PIPE_PACKET_LENGTH_DECODER, new PacketLengthDecoder())
		.addLast(CHANNEL_PIPE_PACKET_LENGTH_ENCODER, PacketLengthEncoder.INSTANCE)
		.addLast(CHANNEL_PIPE_FLOW_CONTROLLER, new FlowControlHandler())
		.addLast(CHANNEL_PIPE_PACKET_DECODER, new PacketDecoder(this))
		.addLast(CHANNEL_PIPE_PACKET_ENCODER, new PacketEncoder(this))
		.addLast(CHANNEL_PIPE_PACKET_PROCESSOR, new PacketProcessor(this))
		.addLast(CHANNEL_PIPE_INBOUND_EXCEPTION_HANDLER, new InboundErrorHandler(this.getLogger()))
		;
	}
	
	@Override
	public InetSocketAddress getRemoteAddress() {
		return channel.remoteAddress();
	}
	
	@Override
	public void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		if (isClosed())
			return;
		if (channel.isActive()) {
			writeQueuedPackets();
			writePacket(packet, listener);
		} else queuePacket(packet, listener);
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

	@Override
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

	@Override
	public boolean hasQueued() {
		return !queue.isEmpty();
	}

	@Override
	public void close() {
		if (isClosed())
			return;
		channel.config().setAutoRead(false);
		channel.close();
		queue.clear();
	}
	
	@Override
	public boolean isClosed() {
		return !channel.isOpen();
	}
	
	@Override
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
	
	@Override
	public synchronized void setProtocol(Protocol protocol, PacketListener listener) {
		if (protocol == null) 
			throw new IllegalArgumentException("Protocol can not be null!");
		synchronized (listenerLock) {
			log.debug("Switching Protocol to: {}", protocol.getClass().getSimpleName());
			this.protocol = protocol;
			removeAllPacketListener();
			if (listener != null)
				registerPacketListener(listener);
			ChannelPipeline pipeline = channel.pipeline();
			if (pipeline.get(CHANNEL_PIPE_INBOUND_NO_PROTOCOL) != null)
				channel.pipeline().remove(CHANNEL_PIPE_INBOUND_NO_PROTOCOL);
			if (pipeline.get(CHANNEL_PIPE_OUTBOUND_NO_PROTOCOL) != null)
				channel.pipeline().remove(CHANNEL_PIPE_OUTBOUND_NO_PROTOCOL);
			channel.config().setAutoRead(true);
		}
	}
	
	@Override
	public boolean isEncryotionEnabled() {
		return encryption;
	}
	
	@Override
	public void setCompressionThreshold(int threshold) {
		if (threshold < 0)
			threshold = 0;
		if (compressionThreshold == threshold)
			return;
		synchronized (this) {
			if (compressionThreshold == threshold)
				return;
			compressionThreshold = threshold;
			if (hasCompression()) {
				setCompression(false);
				setCompression(true);
			}
		}
	}
	
	@Override
	public synchronized void setCompression(boolean enbale) {
		PacketCompressor compressor = this.compressor;
		if (compressor != null == enbale)
			return;
		if (enbale) {
			compressor = new PacketCompressor(compressionThreshold);
			channel.pipeline().addAfter(CHANNEL_PIPE_PACKET_LENGTH_ENCODER, CHANNEL_PIPE_PACKET_COMPRESSOR, compressor);
			this.compressor = compressor;
		} else {
			channel.pipeline().remove(compressor);
			this.compressor = null;
		}
	}
	
	@Override
	public synchronized void setDecompression(boolean enable) {
		PacketDecompressor decompressor = this.decompressor;
		if (decompressor != null == enable)
			return;
		if (enable) {
			decompressor = new PacketDecompressor();
			channel.pipeline().addAfter(CHANNEL_PIPE_PACKET_LENGTH_DECODER, CHANNEL_PIPE_PACKET_DECOMPRESSOR, decompressor);
			this.decompressor = decompressor;
		} else {
			channel.pipeline().remove(decompressor);
			this.decompressor = null;
		}
	}
	
	@Override
	public boolean hasCompression() {
		return compressor != null;
	}
	
	@Override
	public boolean hasDecompression() {
		return decompressor != null;
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
