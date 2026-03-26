package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInJigsawGenerate;
import io.netty.buffer.ByteBuf;

public class CorePacketInJigsawGenerate implements PacketCodec<PacketInJigsawGenerate> {
	
	@Override
	public void deserialize(PacketInJigsawGenerate packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.position = in.readLong();
		packet.levels = readVarInt(in);
		packet.keepJigsaws = in.readBoolean();
	}

	@Override
	public void serialize(PacketInJigsawGenerate packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
