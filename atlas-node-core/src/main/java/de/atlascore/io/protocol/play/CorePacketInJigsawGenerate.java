package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInJigsawGenerate;
import io.netty.buffer.ByteBuf;

public class CorePacketInJigsawGenerate implements PacketIO<PacketInJigsawGenerate> {
	
	@Override
	public void read(PacketInJigsawGenerate packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setPosition(in.readLong());
		packet.setLevels(readVarInt(in));
		packet.setKeepJigsaws(in.readBoolean());
	}

	@Override
	public void write(PacketInJigsawGenerate packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getLevels(), out);
		out.writeBoolean(packet.getKeepJigsaws());
	}

	@Override
	public PacketInJigsawGenerate createPacketData() {
		return new PacketInJigsawGenerate();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInJigsawGenerate.class);
	}

}
