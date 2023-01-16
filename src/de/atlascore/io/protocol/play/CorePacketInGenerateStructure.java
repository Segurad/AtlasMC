package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInGenerateStructure;
import io.netty.buffer.ByteBuf;

public class CorePacketInGenerateStructure extends PacketIO<PacketInGenerateStructure> {
	
	@Override
	public void read(PacketInGenerateStructure packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setPosition(in.readLong());
		packet.setLevels(readVarInt(in));
		packet.setKeepJigsaws(in.readBoolean());
	}

	@Override
	public void write(PacketInGenerateStructure packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getLevels(), out);
		out.writeBoolean(packet.getKeepJigsaws());
	}

	@Override
	public PacketInGenerateStructure createPacketData() {
		return new PacketInGenerateStructure();
	}

}
