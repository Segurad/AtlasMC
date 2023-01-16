package de.atlascore.io.protocol.login;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketInLoginStart;
import io.netty.buffer.ByteBuf;

public class CorePacketInLoginStart extends PacketIO<PacketInLoginStart> {

	@Override
	public void read(PacketInLoginStart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setName(readString(in, 16));
	}

	@Override
	public void write(PacketInLoginStart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getName(), out);
	}

	@Override
	public PacketInLoginStart createPacketData() {
		return new PacketInLoginStart();
	}

}
