package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInJigsawGenerate;
import io.netty.buffer.ByteBuf;

public class CorePacketInJigsawGenerate implements PacketIO<PacketInJigsawGenerate> {
	
	@Override
	public void read(PacketInJigsawGenerate packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.position = in.readLong();
		packet.levels = readVarInt(in);
		packet.keepJigsaws = in.readBoolean();
	}

	@Override
	public void write(PacketInJigsawGenerate packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.position);
		writeVarInt(packet.levels, out);
		out.writeBoolean(packet.keepJigsaws);
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
