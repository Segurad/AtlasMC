package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.login.PacketInLoginStart;
import io.netty.buffer.ByteBuf;

public class CorePacketInLoginStart implements PacketIO<PacketInLoginStart> {

	@Override
	public void read(PacketInLoginStart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.name = readString(in, 16);
		packet.uuid = readUUID(in);
	}

	@Override
	public void write(PacketInLoginStart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.name, out);
		writeUUID(packet.uuid, out);
	}

	@Override
	public PacketInLoginStart createPacketData() {
		return new PacketInLoginStart();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInLoginStart.class);
	}

}
