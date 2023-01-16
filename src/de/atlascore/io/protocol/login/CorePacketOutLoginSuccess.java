package de.atlascore.io.protocol.login;

import java.io.IOException;
import java.util.UUID;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketOutLoginSuccess;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLoginSuccess extends PacketIO<PacketOutLoginSuccess> {

	@Override
	public void read(PacketOutLoginSuccess packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		packet.setUUID(new UUID(most, least));
		packet.setUsername(readString(in, 16));
	}

	@Override
	public void write(PacketOutLoginSuccess packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		UUID uuid = packet.getUUID();
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeString(packet.getUsername(), out);
	}

	@Override
	public PacketOutLoginSuccess createPacketData() {
		return new PacketOutLoginSuccess();
	}

}
