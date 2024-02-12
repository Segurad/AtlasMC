package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutDisconnect;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutDisconnect implements PacketIO<PacketOutDisconnect> {

	@Override
	public void read(PacketOutDisconnect packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.reason = readString(in, CHAT_MAX_LENGTH);
	}

	@Override
	public void write(PacketOutDisconnect packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.reason, out);
	}

	@Override
	public PacketOutDisconnect createPacketData() {
		return new PacketOutDisconnect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDisconnect.class);
	}

}
