package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutBlockAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockAction extends PacketIO<PacketOutBlockAction> {

	@Override
	public void read(PacketOutBlockAction packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setActionID(in.readUnsignedByte());
		packet.setActionParam(in.readUnsignedByte());
		packet.setBlockType(readVarInt(in));
	}

	@Override
	public void write(PacketOutBlockAction packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		out.writeByte(packet.getActionID());
		out.writeByte(packet.getActionParam());
		writeVarInt(packet.getBlockType(), out);
	}

	@Override
	public PacketOutBlockAction createPacketData() {
		return new PacketOutBlockAction();
	}

}
