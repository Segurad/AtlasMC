package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.login.ClientboundDisconnect;
import io.netty.buffer.ByteBuf;

public class CoreClientboundDisconnect implements PacketIO<ClientboundDisconnect> {

	@Override
	public void read(ClientboundDisconnect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.reason = readTextComponent(in);
	}

	@Override
	public void write(ClientboundDisconnect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeTextComponent(packet.reason, out);
	}

	@Override
	public ClientboundDisconnect createPacketData() {
		return new ClientboundDisconnect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundDisconnect.class);
	}

}
