package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSynchronizePlayerPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSynchronizePlayerPosition implements PacketIO<PacketOutSynchronizePlayerPosition> {

	@Override
	public void read(PacketOutSynchronizePlayerPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
		packet.setFlags(in.readByte());
		packet.setTeleportID(readVarInt(in));
	}

	@Override
	public void write(PacketOutSynchronizePlayerPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
		out.writeByte(packet.getFlags());
		writeVarInt(packet.getTeleportID(), out);
	}
	
	@Override
	public PacketOutSynchronizePlayerPosition createPacketData() {
		return new PacketOutSynchronizePlayerPosition();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSynchronizePlayerPosition.class);
	}

}
