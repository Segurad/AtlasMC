package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPlayerRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerRotation implements PacketIO<PacketOutPlayerRotation> {

	@Override
	public void read(PacketOutPlayerRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
	}

	@Override
	public void write(PacketOutPlayerRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeFloat(packet.yaw);
		out.writeFloat(packet.pitch);
	}

	@Override
	public PacketOutPlayerRotation createPacketData() {
		return new PacketOutPlayerRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPlayerRotation.class);
	}

}
