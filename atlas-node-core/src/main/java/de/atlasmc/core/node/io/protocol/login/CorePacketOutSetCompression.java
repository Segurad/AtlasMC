package de.atlasmc.core.node.io.protocol.login;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.PacketOutSetCompression;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

public class CorePacketOutSetCompression implements PacketIO<PacketOutSetCompression> {

	@Override
	public void read(PacketOutSetCompression packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.threshold = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetCompression packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.threshold, out);
	}

	@Override
	public PacketOutSetCompression createPacketData() {
		return new PacketOutSetCompression();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetCompression.class);
	}

}
