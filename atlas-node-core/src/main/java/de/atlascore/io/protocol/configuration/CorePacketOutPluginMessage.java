package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutPluginMessage;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutPluginMessage implements PacketIO<PacketOutPluginMessage> {

	@Override
	public void read(PacketOutPluginMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.channel = readIdentifier(in);
		byte[] data = packet.data = new byte[in.readableBytes()];
		in.readBytes(data);
	}

	@Override
	public void write(PacketOutPluginMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeIdentifier(packet.channel, out);
		out.writeBytes(packet.data);
	}

	@Override
	public PacketOutPluginMessage createPacketData() {
		return new PacketOutPluginMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPluginMessage.class);
	}

}
