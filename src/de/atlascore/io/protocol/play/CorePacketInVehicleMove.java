package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInVehicleMove;
import io.netty.buffer.ByteBuf;

public class CorePacketInVehicleMove extends CoreAbstractHandler<PacketInVehicleMove> {

	@Override
	public void read(PacketInVehicleMove packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketInVehicleMove packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
	}

	@Override
	public PacketInVehicleMove createPacketData() {
		return new PacketInVehicleMove();
	}

}
