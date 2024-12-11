package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSynchronizePlayerPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSynchronizePlayerPosition implements PacketIO<PacketOutSynchronizePlayerPosition> {

	@Override
	public void read(PacketOutSynchronizePlayerPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
		packet.flags = in.readByte();
		packet.teleportID = readVarInt(in);
	}

	@Override
	public void write(PacketOutSynchronizePlayerPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
		out.writeByte(packet.flags);
		writeVarInt(packet.teleportID, out);
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
