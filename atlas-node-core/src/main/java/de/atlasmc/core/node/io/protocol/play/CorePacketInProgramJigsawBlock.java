package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInProgramJigsawBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramJigsawBlock implements PacketIO<PacketInProgramJigsawBlock> {

	@Override
	public void read(PacketInProgramJigsawBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.name = readIdentifier(in);
		packet.target = readIdentifier(in);
		packet.pool = readIdentifier(in);
		packet.finalState = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.jointtype = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.selectionPriority = readVarInt(in);
		packet.placementPriority = readVarInt(in);
	}

	@Override
	public void write(PacketInProgramJigsawBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		writeIdentifier(packet.name, out);
		writeIdentifier(packet.target, out);
		writeIdentifier(packet.pool, out);
		writeString(packet.finalState, out);
		writeString(packet.jointtype, out);
		writeVarInt(packet.selectionPriority, out);
		writeVarInt(packet.placementPriority, out);
	}

	@Override
	public PacketInProgramJigsawBlock createPacketData() {
		return new PacketInProgramJigsawBlock();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInProgramJigsawBlock.class);
	}

}
