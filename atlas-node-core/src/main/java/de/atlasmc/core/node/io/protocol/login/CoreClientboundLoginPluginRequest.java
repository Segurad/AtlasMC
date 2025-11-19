package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ClientboundLoginPluginRequest;
import io.netty.buffer.ByteBuf;

public class CoreClientboundLoginPluginRequest implements PacketIO<ClientboundLoginPluginRequest> {

	@Override
	public void read(ClientboundLoginPluginRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);
		packet.channel = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(ClientboundLoginPluginRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
		NamespacedKey.STREAM_CODEC.serialize(packet.channel, out, null);
		out.writeBytes(packet.data);
	}

	@Override
	public ClientboundLoginPluginRequest createPacketData() {
		return new ClientboundLoginPluginRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundLoginPluginRequest.class);
	}

}
