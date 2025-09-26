package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutClearTitle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutClearTitle implements PacketIO<PacketOutClearTitle> {

	@Override
	public void read(PacketOutClearTitle packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.reset = in.readBoolean();
	}

	@Override
	public void write(PacketOutClearTitle packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeBoolean(packet.reset);
	}

	@Override
	public PacketOutClearTitle createPacketData() {
		return new PacketOutClearTitle();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutClearTitle.class);
	}

}
