package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetDifficulty;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetDifficulty extends PacketIO<PacketInSetDifficulty> {
	
	@Override
	public void read(PacketInSetDifficulty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setDifficulty(in.readByte());
	}

	@Override
	public void write(PacketInSetDifficulty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getDifficulty());
	}

	@Override
	public PacketInSetDifficulty createPacketData() {
		return new PacketInSetDifficulty();
	}

}
