package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketInPluginMessage;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInPluginMessage implements PacketIO<PacketInPluginMessage> {

	@Override
	public void read(PacketInPluginMessage packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.channel = readIdentifier(in);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(PacketInPluginMessage packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeIdentifier(packet.channel, out);
		out.writeBytes(packet.data);
	}

	@Override
	public PacketInPluginMessage createPacketData() {
		return new PacketInPluginMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPluginMessage.class);
	}

}
