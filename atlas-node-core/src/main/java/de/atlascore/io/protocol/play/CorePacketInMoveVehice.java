package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInMoveVehicle;
import io.netty.buffer.ByteBuf;

public class CorePacketInMoveVehice implements PacketIO<PacketInMoveVehicle> {

	@Override
	public void read(PacketInMoveVehicle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
	}

	@Override
	public void write(PacketInMoveVehicle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
	}

	@Override
	public PacketInMoveVehicle createPacketData() {
		return new PacketInMoveVehicle();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInMoveVehicle.class);
	}

}
