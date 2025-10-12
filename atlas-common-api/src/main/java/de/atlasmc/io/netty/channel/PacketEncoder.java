package de.atlasmc.io.netty.channel;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.SocketConnectionHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encoder for outgoing packets
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
	
	private final ConnectionHandler handler;
	
	public PacketEncoder(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		final int packetID = msg.getID();
		PacketUtil.writeVarInt(msg.getID(), out);
		@SuppressWarnings("unchecked")
		PacketIO<Packet> io = (PacketIO<Packet>) handler.getProtocol().getHandlerClientbound(packetID);
		io.write(msg, out, handler);
		
		handler.getLogger().debug("Encoded packet: {}", msg.getClass().getSimpleName());
		
		if (msg.isTerminating()) {
			Channel channel = ctx.channel();
			channel.pipeline().addAfter(ctx.name(), SocketConnectionHandler.CHANNEL_PIPE_OUTBOUND_NO_PROTOCOL, OutboundNoProtocolHandler.INSTANCE);
			handler.getLogger().debug("Outbound protocol termination!");
		}
	}

}
