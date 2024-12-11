package de.atlascore.io.protocol.login;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketOutDisconnect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisconnect implements PacketIO<PacketOutDisconnect> {

	@Override
	public void read(PacketOutDisconnect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.reason = readTextComponent(in);
	}

	@Override
	public void write(PacketOutDisconnect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeTextComponent(packet.reason, out);
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
