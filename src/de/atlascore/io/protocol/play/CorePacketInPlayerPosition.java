package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerPosition extends PacketIO<PacketInPlayerPosition> {

	@Override
	public void read(PacketInPlayerPosition packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setX(in.readDouble());
		packet.setFeetY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketInPlayerPosition packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getFeetY());
		out.writeDouble(packet.getZ());
		out.writeBoolean(packet.isOnGround());
	}
	
	@Override
	public PacketInPlayerPosition createPacketData() {
		return new PacketInPlayerPosition();
	}

}
