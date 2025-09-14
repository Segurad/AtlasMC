package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateEntityPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateEntityPosition implements PacketIO<PacketOutUpdateEntityPosition> {

	@Override
	public void read(PacketOutUpdateEntityPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.deltaX = in.readShort();
		packet.deltaY = in.readShort();
		packet.deltaZ = in.readShort();
		packet.onGround = in.readBoolean();
	}

	@Override
	public void write(PacketOutUpdateEntityPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeShort(packet.deltaX);
		out.writeShort(packet.deltaY);
		out.writeShort(packet.deltaZ);
		out.writeBoolean(packet.onGround);
	}

	@Override
	public PacketOutUpdateEntityPosition createPacketData() {
		return new PacketOutUpdateEntityPosition();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateEntityPosition.class);
	}

}
