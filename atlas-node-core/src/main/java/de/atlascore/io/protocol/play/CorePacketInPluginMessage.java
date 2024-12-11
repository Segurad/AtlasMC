package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInPluginMessage implements PacketIO<PacketInPluginMessage> {
	
	@Override
	public void read(PacketInPluginMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.channel = readIdentifier(in);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(PacketInPluginMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
