package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerAbilities implements PacketCodec<PacketOutPlayerAbilities> {

	@Override
	public void deserialize(PacketOutPlayerAbilities packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.flags = in.readByte();
		packet.flySpeed = in.readFloat();
		packet.fovModifier = in.readFloat();
	}

	@Override
	public void serialize(PacketOutPlayerAbilities packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.flags);
		out.writeFloat(packet.flySpeed);
		out.writeFloat(packet.fovModifier);
	}
	
	@Override
	public PacketOutPlayerAbilities createPacketData() {
		return new PacketOutPlayerAbilities();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPlayerAbilities.class);
	}

}
