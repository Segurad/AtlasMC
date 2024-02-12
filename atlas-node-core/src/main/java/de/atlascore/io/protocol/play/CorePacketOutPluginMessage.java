package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPluginMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPluginMessage implements PacketIO<PacketOutPluginMessage> {

	@Override
	public void read(PacketOutPluginMessage packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.channel = readIdentifier(in);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(PacketOutPluginMessage packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
