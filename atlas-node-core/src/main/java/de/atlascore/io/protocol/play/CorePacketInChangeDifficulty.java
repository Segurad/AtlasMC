package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInChangeDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInChangeDifficulty implements PacketIO<PacketInChangeDifficulty> {
	
	@Override
	public void read(PacketInChangeDifficulty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.difficulty = in.readByte();
	}

	@Override
	public void write(PacketInChangeDifficulty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.difficulty);
	}

	@Override
	public PacketInChangeDifficulty createPacketData() {
		return new PacketInChangeDifficulty();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChangeDifficulty.class);
	}

}
