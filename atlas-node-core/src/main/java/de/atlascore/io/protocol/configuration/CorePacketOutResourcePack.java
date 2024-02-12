package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutResourcePack;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutResourcePack implements PacketIO<PacketOutResourcePack> {

	@Override
	public void read(PacketOutResourcePack packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.url = readString(in, 32767);
		packet.hash = readString(in, 40);
		packet.force = in.readBoolean();
		if (in.readBoolean()) {
			packet.message = readString(in, CHAT_MAX_LENGTH);
		}
	}

	@Override
	public void write(PacketOutResourcePack packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.url, out);
		writeString(packet.hash, out);
		out.writeBoolean(packet.force);
		if (packet.message != null) {
			out.writeBoolean(true);
			writeString(packet.message, out);
		} else {
			out.writeBoolean(false);
		}
	}

	@Override
	public PacketOutResourcePack createPacketData() {
		return new PacketOutResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutResourcePack.class);
	}

}
