package de.atlascore.io.netty.channel;

import java.util.List;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
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
		int id = PacketUtil.readVarInt(in);
		Protocol prot = handler.getProtocol();
		@SuppressWarnings("unchecked")
		PacketIO<Packet> io = (PacketIO<Packet>) prot.getHandlerIn(id);
		if (io == null)
			throw new ProtocolException("Invalid Packet ID: " + id, prot, null);
		Packet packet = prot.createPacketIn(id);
		packet.setTimestamp(System.currentTimeMillis());
		io.read(packet, in, handler);
		out.add(packet);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (!handler.handleException(cause)) 
			ctx.fireExceptionCaught(cause);
	}

}
