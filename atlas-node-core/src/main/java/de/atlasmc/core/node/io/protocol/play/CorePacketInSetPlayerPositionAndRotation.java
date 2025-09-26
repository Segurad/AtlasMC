package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSetPlayerPositionAndRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetPlayerPositionAndRotation implements PacketIO<PacketInSetPlayerPositionAndRotation> {

	@Override
	public void read(PacketInSetPlayerPositionAndRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.x = in.readDouble();
		packet.feetY = in.readDouble();
		packet.z = in.readDouble();
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketInSetPlayerPositionAndRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.feetY);
		out.writeDouble(packet.z);
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
		out.writeByte(packet.flags);
	}

	@Override
	public PacketInSetPlayerPositionAndRotation createPacketData() {
		return new PacketInSetPlayerPositionAndRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSetPlayerPositionAndRotation.class);
	}
	
	

}
