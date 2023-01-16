package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.Difficulty;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutServerDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutServerDifficulty extends PacketIO<PacketOutServerDifficulty> {

	@Override
	public void read(PacketOutServerDifficulty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setDifficulty(Difficulty.getByID(in.readUnsignedByte()));
		packet.setLocked(in.readBoolean());
	}

	@Override
	public void write(PacketOutServerDifficulty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getDifficulty().getID());
		out.writeBoolean(packet.isLocked());
	}

	@Override
	public PacketOutServerDifficulty createPacketData() {
		return new PacketOutServerDifficulty();
	}
	
}
