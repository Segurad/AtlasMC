package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInQueryBlockEntityTag;
import io.netty.buffer.ByteBuf;

public class CorePacketInQueryBlockEntityTag implements PacketIO<PacketInQueryBlockEntityTag> {
	
	@Override
	public void read(PacketInQueryBlockEntityTag packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		packet.setPosition(in.readLong());
	}

	@Override
	public void write(PacketInQueryBlockEntityTag packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
