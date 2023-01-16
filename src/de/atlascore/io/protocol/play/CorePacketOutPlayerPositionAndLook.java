package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPlayerPositionAndLook;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerPositionAndLook extends PacketIO<PacketOutPlayerPositionAndLook> {

	@Override
	public void read(PacketOutPlayerPositionAndLook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
		packet.setFlags(in.readByte());
		packet.setTeleportID(readVarInt(in));
	}

	@Override
	public void write(PacketOutPlayerPositionAndLook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
		out.writeByte(packet.getFlags());
		writeVarInt(packet.getTeleportID(), out);
	}
	
	@Override
	public PacketOutPlayerPositionAndLook createPacketData() {
		return new PacketOutPlayerPositionAndLook();
	}

}
