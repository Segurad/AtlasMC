package de.atlasmc.io.channel;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class PacketDecoder extends ReplayingDecoder<Packet> {
	
	private Protocol protocol;
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int id = AbstractPacket.readVarInt(in);
		Packet packet = protocol.createPacket(id);
		packet.read(in);
		out.add(packet);
	}

}
