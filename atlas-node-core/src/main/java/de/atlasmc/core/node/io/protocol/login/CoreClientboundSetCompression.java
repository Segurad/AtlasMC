package de.atlasmc.core.node.io.protocol.login;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ClientboundSetCompression;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

public class CoreClientboundSetCompression implements PacketCodec<ClientboundSetCompression> {

	@Override
	public void deserialize(ClientboundSetCompression packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.threshold = readVarInt(in);
	}

	@Override
	public void serialize(ClientboundSetCompression packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.threshold, out);
	}

	@Override
	public ClientboundSetCompression createPacketData() {
		return new ClientboundSetCompression();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundSetCompression.class);
	}

}
