package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.Difficulty;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutChangeDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChangeDifficulty implements PacketIO<PacketOutChangeDifficulty> {

	@Override
	public void read(PacketOutChangeDifficulty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setDifficulty(Difficulty.getByID(in.readUnsignedByte()));
		packet.setLocked(in.readBoolean());
	}

	@Override
	public void write(PacketOutChangeDifficulty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getDifficulty().getID());
		out.writeBoolean(packet.isLocked());
	}

	@Override
	public PacketOutChangeDifficulty createPacketData() {
		return new PacketOutChangeDifficulty();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChangeDifficulty.class);
	}
	
}
