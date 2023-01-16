package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutVehicleMove;
import io.netty.buffer.ByteBuf;

public class CorePacketOutVehicleMove extends PacketIO<PacketOutVehicleMove> {

	@Override
	public void read(PacketOutVehicleMove packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketOutVehicleMove packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
	}

	@Override
	public PacketOutVehicleMove createPacketData() {
		return new PacketOutVehicleMove();
	}

}
