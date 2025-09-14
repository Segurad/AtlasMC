package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutBlockAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockAction implements PacketIO<PacketOutBlockAction> {

	@Override
	public void read(PacketOutBlockAction packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.actionID = in.readUnsignedByte();
		packet.actionParam = in.readUnsignedByte();
		packet.blockType = readVarInt(in);
	}

	@Override
	public void write(PacketOutBlockAction packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		out.writeByte(packet.actionID);
		out.writeByte(packet.actionParam);
		writeVarInt(packet.blockType, out);
	}

	@Override
	public PacketOutBlockAction createPacketData() {
		return new PacketOutBlockAction();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutBlockAction.class);
	}

}
