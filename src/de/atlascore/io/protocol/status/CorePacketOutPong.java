package de.atlascore.io.protocol.status;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.status.PacketOutPong;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPong extends PacketIO<PacketOutPong> {

	@Override
	public void read(PacketOutPong packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPong(in.readLong());
	}

	@Override
	public void write(PacketOutPong packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPong());
	}
	
	@Override
	public PacketOutPong createPacketData() {
		return new PacketOutPong();
	}

}
