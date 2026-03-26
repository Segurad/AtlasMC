package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutMoveVehicle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMoveVehicle implements PacketCodec<PacketOutMoveVehicle> {

	@Override
	public void deserialize(PacketOutMoveVehicle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
	}

	@Override
	public void serialize(PacketOutMoveVehicle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
	}

	@Override
	public PacketOutMoveVehicle createPacketData() {
		return new PacketOutMoveVehicle();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutMoveVehicle.class);
	}

}
