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
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketInMoveVehicle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
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
