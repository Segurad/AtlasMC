package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.status.PacketInRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInRequest implements PacketIO<PacketInRequest> {

	@Override
	public void read(PacketInRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {}

	@Override
	public void write(PacketInRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {}

	@Override
	public PacketInRequest createPacketData() {
		return new PacketInRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInRequest.class);
	}

}
