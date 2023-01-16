package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSteerVehicle;
import io.netty.buffer.ByteBuf;

public class CorePacketInSteerVehicle extends PacketIO<PacketInSteerVehicle> {

	@Override
	public void read(PacketInSteerVehicle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSideways(in.readFloat());
		packet.setForward(in.readFloat());
		packet.setFlags(in.readByte());
	}

	@Override
	public void write(PacketInSteerVehicle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.getSideways());
		out.writeFloat(packet.getForward());
		out.writeByte(packet.getFlags());
	}
	
	@Override
	public PacketInSteerVehicle createPacketData() {
		return new PacketInSteerVehicle();
	}

}
