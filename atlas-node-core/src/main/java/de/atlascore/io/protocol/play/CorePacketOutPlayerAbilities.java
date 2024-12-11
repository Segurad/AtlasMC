package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerAbilities implements PacketIO<PacketOutPlayerAbilities> {

	@Override
	public void read(PacketOutPlayerAbilities packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.flags = in.readByte();
		packet.flySpeed = in.readFloat();
		packet.fovModifier = in.readFloat();
	}

	@Override
	public void write(PacketOutPlayerAbilities packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
