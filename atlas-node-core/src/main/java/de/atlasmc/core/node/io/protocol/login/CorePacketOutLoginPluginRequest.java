package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.PacketOutLoginPluginRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLoginPluginRequest implements PacketIO<PacketOutLoginPluginRequest> {

	@Override
	public void read(PacketOutLoginPluginRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);
		packet.channel = readIdentifier(in);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(PacketOutLoginPluginRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.messageID, out);
		writeIdentifier(packet.channel, out);
		out.writeBytes(packet.data);
	}

	@Override
	public PacketOutLoginPluginRequest createPacketData() {
		return new PacketOutLoginPluginRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutLoginPluginRequest.class);
	}

}
