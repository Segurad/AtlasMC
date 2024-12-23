package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClientTickEnd;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientTickEnd implements PacketIO<PacketInClientTickEnd> {

	@Override
	public void read(PacketInClientTickEnd packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// no data
	}

	@Override
	public void write(PacketInClientTickEnd packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// no data
	}

	@Override
	public PacketInClientTickEnd createPacketData() {
		return new PacketInClientTickEnd();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInClientTickEnd.class);
	}

}
