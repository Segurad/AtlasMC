package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateEntityPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateEntityPosition implements PacketIO<PacketOutUpdateEntityPosition> {

	@Override
	public void read(PacketOutUpdateEntityPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setDeltaX(in.readShort());
		packet.setDeltaY(in.readShort());
		packet.setDeltaZ(in.readShort());
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketOutUpdateEntityPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeShort(packet.getDeltaX());
		out.writeShort(packet.getDeltaY());
		out.writeShort(packet.getDeltaZ());
		out.writeBoolean(packet.isOnGround());
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
