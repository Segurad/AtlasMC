package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAbilities implements PacketIO<PacketInPlayerAbilities> {

	@Override
	public void read(PacketInPlayerAbilities packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketInPlayerAbilities packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.flags);
	}

	@Override
	public PacketInPlayerAbilities createPacketData() {
		return new PacketInPlayerAbilities();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerAbilities.class);
	}

}
