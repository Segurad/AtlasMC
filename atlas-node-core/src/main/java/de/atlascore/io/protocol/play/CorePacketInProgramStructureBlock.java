package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInProgramStructureBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInProgramStructureBlock implements PacketIO<PacketInProgramStructureBlock> {

	@Override
	public void read(PacketInProgramStructureBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setAction(readVarInt(in));
		packet.setMode(readVarInt(in));
		packet.setName(readString(in));
		packet.setOffsetX(in.readByte());
		packet.setOffsetY(in.readByte());
		packet.setOffsetZ(in.readByte());
		packet.setSizeX(in.readByte());
		packet.setSizeY(in.readByte());
		packet.setSizeZ(in.readByte());
		packet.setMirror(readVarInt(in));
		packet.setRotation(readVarInt(in));
		packet.setMetadata(readString(in));
		packet.setIntegrity(in.readFloat());
		packet.setSeed(readVarLong(in));
		packet.setFlags(in.readByte());
		
	}

	@Override
	public void write(PacketInProgramStructureBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getAction(), out);
		writeVarInt(packet.getMode(), out);
		writeString(packet.getName(), out);
		out.writeByte(packet.getOffsetX());
		out.writeByte(packet.getOffsetY());
		out.writeByte(packet.getOffsetZ());
		out.writeByte(packet.getSizeX());
		out.writeByte(packet.getSizeY());
		out.writeByte(packet.getSizeZ());
		writeVarInt(packet.getMirror(), out);
		writeVarInt(packet.getRotation(), out);
		writeString(packet.getMetadata(), out);
		out.writeFloat(packet.getIntegrity());
		writeVarLong(packet.getSeed(), out);
		out.writeByte(packet.getFlags());
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
