package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerAbilities extends PacketIO<PacketInPlayerAbilities> {

	@Override
	public void read(PacketInPlayerAbilities packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setFlags(in.readByte());
	}

	@Override
	public void write(PacketInPlayerAbilities packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getFlags());
	}

	@Override
	public PacketInPlayerAbilities createPacketData() {
		return new PacketInPlayerAbilities();
	}

}
