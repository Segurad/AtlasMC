package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ServerboundLoginStart;
import io.netty.buffer.ByteBuf;

public class CoreServerboundLoginStart implements PacketIO<ServerboundLoginStart> {

	@Override
	public void read(ServerboundLoginStart packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.name = readString(in, 16);
		packet.uuid = readUUID(in);
	}

	@Override
	public void write(ServerboundLoginStart packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.name, out);
		writeUUID(packet.uuid, out);
	}

	@Override
	public ServerboundLoginStart createPacketData() {
		return new ServerboundLoginStart();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundLoginStart.class);
	}

}
