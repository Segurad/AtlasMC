package de.atlasmc.core.node.io.handshake;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.protocol.handshake.HandshakePacketIO;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftLegacyHandshake;
import io.netty.buffer.ByteBuf;

public class CorePacketMinecraftLegacyHandshake extends HandshakePacketIO<PacketMinecraftLegacyHandshake> {

	@Override
	public void read(PacketMinecraftLegacyHandshake packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.status = in.readUnsignedByte();
	}

	@Override
	public void write(PacketMinecraftLegacyHandshake packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.status);
	}

	@Override
	public PacketMinecraftLegacyHandshake createPacketData() {
		return new PacketMinecraftLegacyHandshake();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketMinecraftLegacyHandshake.class);
	}

	@Override
	public void handle(ConnectionHandler handler, PacketMinecraftLegacyHandshake packet) {
		handler.getLogger().debug("Legacy handshake!");
	}

}
