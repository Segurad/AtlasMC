package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerPositionAndRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerPositionAndRotation extends PacketIO<PacketInPlayerPositionAndRotation> {

	@Override
	public void read(PacketInPlayerPositionAndRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setX(in.readDouble());
		packet.setFeetY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketInPlayerPositionAndRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getFeetY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketInPlayerPositionAndRotation createPacketData() {
		return new PacketInPlayerPositionAndRotation();
	}
	
	

}
