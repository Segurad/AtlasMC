package de.atlasmc.core.node.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.configuration.ClientboundResetChat;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResetChat implements PacketIO<ClientboundResetChat> {

	@Override
	public void read(ClientboundResetChat packet, ByteBuf in, ConnectionHandler con) throws IOException {}

	@Override
	public void write(ClientboundResetChat packet, ByteBuf out, ConnectionHandler con) throws IOException {}

	@Override
	public ClientboundResetChat createPacketData() {
		return new ClientboundResetChat();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundResetChat.class);
	}

}
