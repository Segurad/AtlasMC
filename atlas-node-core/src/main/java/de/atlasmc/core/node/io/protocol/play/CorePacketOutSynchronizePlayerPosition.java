package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSynchronizePlayerPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSynchronizePlayerPosition implements PacketIO<PacketOutSynchronizePlayerPosition> {

	@Override
	public void read(PacketOutSynchronizePlayerPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.teleportID = readVarInt(in);
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.velocityX = in.readDouble();
		packet.velocityY = in.readDouble();
		packet.velocityZ = in.readDouble();
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
		packet.flags = in.readInt();
	}

	@Override
	public void write(PacketOutSynchronizePlayerPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.teleportID, out);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeDouble(packet.velocityX);
		out.writeDouble(packet.velocityY);
		out.writeDouble(packet.velocityZ);
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
		out.writeInt(packet.flags);
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
