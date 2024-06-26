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
		packet.setFlags(in.readByte());
		packet.setFlySpeed(in.readFloat());
		packet.setFovModifier(in.readFloat());
	}

	@Override
	public void write(PacketOutPlayerAbilities packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getFlags());
		out.writeFloat(packet.getFlySpeed());
		out.writeFloat(packet.getFovModifier());
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
