package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketOutLoginPluginRequest;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutLoginPluginRequest implements PacketIO<PacketOutLoginPluginRequest> {

	@Override
	public void read(PacketOutLoginPluginRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.messageID = readVarInt(in);
		packet.channel = readIdentifier(in);
		byte[] data = packet.data = new byte[in.readableBytes()];
		in.readBytes(data);
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
