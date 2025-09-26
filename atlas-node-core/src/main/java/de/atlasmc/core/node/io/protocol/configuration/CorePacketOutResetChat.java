package de.atlasmc.core.node.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.configuration.PacketOutResetChat;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResetChat implements PacketIO<PacketOutResetChat> {

	@Override
	public void read(PacketOutResetChat packet, ByteBuf in, ConnectionHandler con) throws IOException {}

	@Override
	public void write(PacketOutResetChat packet, ByteBuf out, ConnectionHandler con) throws IOException {}

	@Override
	public PacketOutResetChat createPacketData() {
		return new PacketOutResetChat();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutResetChat.class);
	}

}
