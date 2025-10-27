package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInProgramStructureBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramStructureBlock implements PacketIO<PacketInProgramStructureBlock> {

	@Override
	public void read(PacketInProgramStructureBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.action = readVarInt(in);
		packet.mode = readVarInt(in);
		packet.name = readString(in, MAX_IDENTIFIER_LENGTH);
		packet.offsetX = in.readByte();
		packet.offsetY = in.readByte();
		packet.offsetZ = in.readByte();
		packet.sizeX = in.readByte();
		packet.sizeY = in.readByte();
		packet.sizeZ = in.readByte();
		packet.mirror = readVarInt(in);
		packet.rotation = readVarInt(in);
		packet.metadata = readString(in, 128);
		packet.integrity = in.readFloat();
		packet.seed = readVarLong(in);
		packet.flags = in.readByte();
		
	}

	@Override
	public void write(PacketInProgramStructureBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		writeVarInt(packet.action, out);
		writeVarInt(packet.mode, out);
		writeString(packet.name, out);
		out.writeByte(packet.offsetX);
		out.writeByte(packet.offsetY);
		out.writeByte(packet.offsetZ);
		out.writeByte(packet.sizeX);
		out.writeByte(packet.sizeY);
		out.writeByte(packet.sizeZ);
		writeVarInt(packet.mirror, out);
		writeVarInt(packet.rotation, out);
		writeString(packet.metadata, out);
		out.writeFloat(packet.integrity);
		writeVarLong(packet.seed, out);
		out.writeByte(packet.flags);
	}

	@Override
	public PacketInProgramStructureBlock createPacketData() {
		return new PacketInProgramStructureBlock();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInProgramStructureBlock.class);
	}

}
