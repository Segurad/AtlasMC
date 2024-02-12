package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetPlayerPositionAndRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetPlayerPositionAndRotation implements PacketIO<PacketInSetPlayerPositionAndRotation> {

	@Override
	public void read(PacketInSetPlayerPositionAndRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setX(in.readDouble());
		packet.setFeetY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setYaw(in.readFloat());
		packet.setPitch(in.readFloat());
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketInSetPlayerPositionAndRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getFeetY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getYaw());
		out.writeFloat(packet.getPitch());
		out.writeBoolean(packet.isOnGround());
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
