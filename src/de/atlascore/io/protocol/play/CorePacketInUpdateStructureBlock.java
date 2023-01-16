package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUpdateStructureBlock;
import io.netty.buffer.ByteBuf;

public class CorePacketInUpdateStructureBlock extends PacketIO<PacketInUpdateStructureBlock> {

	@Override
	public void read(PacketInUpdateStructureBlock packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setAction(readVarInt(in));
		packet.setMode(readVarInt(in));
		packet.setMirror(readVarInt(in));
		packet.setRotation(readVarInt(in));
		packet.setName(readString(in));
		packet.setMetadata(readString(in));
		packet.setOffsetX(in.readByte());
		packet.setOffsetY(in.readByte());
		packet.setOffsetZ(in.readByte());
		packet.setSizeX(in.readByte());
		packet.setSizeY(in.readByte());
		packet.setSizeZ(in.readByte());
		packet.setFlags(in.readByte());
		packet.setIntegrity(in.readFloat());
		packet.setSeed(readVarLong(in));
		
	}

	@Override
	public void write(PacketInUpdateStructureBlock packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getAction(), out);
		writeVarInt(packet.getMode(), out);
		writeVarInt(packet.getMirror(), out);
		writeVarInt(packet.getRotation(), out);
		writeString(packet.getName(), out);
		writeString(packet.getMetadata(), out);
		out.writeByte(packet.getOffsetX());
		out.writeByte(packet.getOffsetY());
		out.writeByte(packet.getOffsetZ());
		out.writeByte(packet.getSizeX());
		out.writeByte(packet.getSizeY());
		out.writeByte(packet.getSizeZ());
		out.writeByte(packet.getFlags());
		out.writeFloat(packet.getIntegrity());
		writeVarLong(packet.getSeed(), out);
	}

	@Override
	public PacketInUpdateStructureBlock createPacketData() {
		return new PacketInUpdateStructureBlock();
	}

}
