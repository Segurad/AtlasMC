package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.ServerboundPing;
import io.netty.buffer.ByteBuf;

public class CorePacketInPing implements PacketIO<ServerboundPing> {

	@Override
	public void read(ServerboundPing packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.ping = in.readLong();
	}

	@Override
	public void write(ServerboundPing packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.ping);
	}

	@Override
	public ServerboundPing createPacketData() {
		return new ServerboundPing();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundPing.class);
	}

}
