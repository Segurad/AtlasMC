package de.atlascore.io.netty.channel;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
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
		final int packetID = msg.getID();
		PacketUtil.writeVarInt(msg.getID(), out);
		@SuppressWarnings("unchecked")
		PacketIO<Packet> io = (PacketIO<Packet>) handler.getProtocol().getHandlerOut(packetID);
		io.write(msg, out, handler);
	}

}
