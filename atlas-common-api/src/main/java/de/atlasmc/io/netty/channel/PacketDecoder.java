package de.atlasmc.io.netty.channel;

import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.SocketConnectionHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Decoder for incoming packets 
 */
public class PacketDecoder extends ByteToMessageDecoder {

	private final ConnectionHandler handler;
	
	public PacketDecoder(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		final int id = PacketUtil.readVarInt(in);
		final Protocol prot = handler.getProtocol();
		@SuppressWarnings("unchecked")
		final PacketIO<Packet> io = (PacketIO<Packet>) prot.getHandlerIn(id);
		if (io == null)
			throw new ProtocolException("Invalid Packet ID: " + id, prot, null);
		final Packet packet = prot.createPacketIn(id);
		packet.setTimestamp(System.currentTimeMillis());
		io.read(packet, in, handler);
		
		if (in.readableBytes() > 0) // check for unread data
			throw new ProtocolException("Packet " + id + " longer than exspected extra bytes found: " + in.readableBytes() , prot, packet);
		
		handler.getLogger().debug("Decoded packet: {}", packet.getClass().getSimpleName());
		
		if (packet.isTerminating()) { // check for protocol change
			Channel channel = ctx.channel();
			channel.config().setAutoRead(false);
			channel.pipeline().addBefore(ctx.name(), SocketConnectionHandler.CHANNEL_PIPE_INBOUND_NO_PROTOCOL, InboundNoProtocolHandler.INSTANCE);
			handler.getLogger().debug("Inbound protocol termination!");
		}
		
		out.add(packet);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (!handler.handleException(cause)) 
			ctx.fireExceptionCaught(cause);
	}

}
