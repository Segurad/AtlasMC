package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.ClientboundPong;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPong implements PacketIO<ClientboundPong> {

	@Override
	public void read(ClientboundPong packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.pong = in.readLong();
	}

	@Override
	public void write(ClientboundPong packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.pong);
	}
	
	@Override
	public ClientboundPong createPacketData() {
		return new ClientboundPong();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundPong.class);
	}

}
