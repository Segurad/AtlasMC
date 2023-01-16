package de.atlascore.io.protocol.status;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.status.PacketInRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInRequest extends PacketIO<PacketInRequest> {

	@Override
	public void read(PacketInRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {}

	@Override
	public void write(PacketInRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {}

	@Override
	public PacketInRequest createPacketData() {
		return new PacketInRequest();
	}

}
