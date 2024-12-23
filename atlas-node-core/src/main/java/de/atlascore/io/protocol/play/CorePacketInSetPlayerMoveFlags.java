package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetPlayerMoveFlags;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetPlayerMoveFlags implements PacketIO<PacketInSetPlayerMoveFlags> {

	@Override
	public void read(PacketInSetPlayerMoveFlags packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketInSetPlayerMoveFlags packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.flags);
	}
	
	@Override
	public PacketInSetPlayerMoveFlags createPacketData() {
		return new PacketInSetPlayerMoveFlags();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetPlayerMoveFlags.class);
	}

}
