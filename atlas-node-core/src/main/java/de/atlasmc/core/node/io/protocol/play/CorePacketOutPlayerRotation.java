package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutPlayerRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerRotation implements PacketCodec<PacketOutPlayerRotation> {

	@Override
	public void deserialize(PacketOutPlayerRotation packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.yaw = in.readFloat();
		packet.pitch = in.readFloat();
	}

	@Override
	public void serialize(PacketOutPlayerRotation packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
