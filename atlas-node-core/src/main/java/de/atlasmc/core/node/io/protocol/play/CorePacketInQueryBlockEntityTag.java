package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInQueryBlockEntityTag;
import io.netty.buffer.ByteBuf;

public class CorePacketInQueryBlockEntityTag implements PacketCodec<PacketInQueryBlockEntityTag> {
	
	@Override
	public void deserialize(PacketInQueryBlockEntityTag packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		packet.setPosition(in.readLong());
	}

	@Override
	public void serialize(PacketInQueryBlockEntityTag packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getTransactionID(), out);
		out.writeLong(packet.getPosition());
	}

	@Override
	public PacketInQueryBlockEntityTag createPacketData() {
		return new PacketInQueryBlockEntityTag();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInQueryBlockEntityTag.class);
	}

}
