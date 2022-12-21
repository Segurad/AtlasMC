package de.atlascore.io.netty.channel;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import io.netty.buffer.ByteBuf;
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
		int id = AbstractPacket.readVarInt(in);
		Protocol prot = handler.getProtocol();
		Packet packet = prot.createPacketIn(id);
		if (packet == null)
			throw new IllegalStateException("Invalid Packet ID: " + id);
		packet.setTimestamp(System.currentTimeMillis());
		prot.readPacket(packet, in, handler);
		out.add(packet);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (!handler.handleException(cause)) 
			ctx.fireExceptionCaught(cause);
	}

}
