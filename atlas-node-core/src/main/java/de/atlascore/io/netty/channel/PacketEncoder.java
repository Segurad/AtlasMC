package de.atlascore.io.netty.channel;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketUtil;
import io.netty.buffer.ByteBuf;
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
		PacketUtil.writeVarInt(msg.getID(), out);
		handler.getProtocol().writePacket(msg, out, handler);
	}

}
