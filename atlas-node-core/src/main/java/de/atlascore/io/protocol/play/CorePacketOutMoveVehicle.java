package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutMoveVehicle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMoveVehicle implements PacketIO<PacketOutMoveVehicle> {

	@Override
	public void read(PacketOutMoveVehicle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketOutMoveVehicle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
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
