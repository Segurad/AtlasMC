package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutBundleDelimiter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBundleDelimiter implements PacketIO<PacketOutBundleDelimiter> {

	@Override
	public void read(PacketOutBundleDelimiter packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// this packet does not contain data
	}

	@Override
	public void write(PacketOutBundleDelimiter packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// this packet does not contain data
	}

	@Override
	public PacketOutBundleDelimiter createPacketData() {
		return new PacketOutBundleDelimiter();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutBundleDelimiter.class);
	}

}
