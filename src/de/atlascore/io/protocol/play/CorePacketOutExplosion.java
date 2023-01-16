package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutExplosion;
import io.netty.buffer.ByteBuf;

public class CorePacketOutExplosion extends PacketIO<PacketOutExplosion> {

	@Override
	public void read(PacketOutExplosion packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setX(in.readFloat());
		packet.setY(in.readFloat());
		packet.setZ(in.readFloat());
		packet.setStrength(in.readFloat());
		final int count = in.readInt()*3;
		byte[] records = new byte[count];
		in.readBytes(records);
		packet.setRecords(records);
		packet.setMotionX(in.readFloat());
		packet.setMotionY(in.readFloat());
		packet.setMotionZ(in.readFloat());
	}

	@Override
	public void write(PacketOutExplosion packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.getX());
		out.writeFloat(packet.getY());
		out.writeFloat(packet.getZ());
		out.writeFloat(packet.getStrength());
		out.writeInt(packet.getRecords().length/3);
		out.writeBytes(packet.getRecords());
		out.writeFloat(packet.getMotionX());
		out.writeFloat(packet.getMotionY());
		out.writeFloat(packet.getMotionZ());
	}
	
	@Override
	public PacketOutExplosion createPacketData() {
		return new PacketOutExplosion();
	}

}
