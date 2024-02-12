package de.atlascore.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.status.PacketInPing;
import io.netty.buffer.ByteBuf;

public class CorePacketInPing implements PacketIO<PacketInPing> {

	@Override
	public void read(PacketInPing packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPing(in.readLong());
	}

	@Override
	public void write(PacketInPing packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPing());
	}

	@Override
	public PacketInPing createPacketData() {
		return new PacketInPing();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPing.class);
	}

}
