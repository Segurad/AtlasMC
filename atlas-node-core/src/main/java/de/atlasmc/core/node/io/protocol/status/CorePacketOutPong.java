package de.atlasmc.core.node.io.protocol.status;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.status.PacketOutPong;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPong implements PacketIO<PacketOutPong> {

	@Override
	public void read(PacketOutPong packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.pong = in.readLong();
	}

	@Override
	public void write(PacketOutPong packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.pong);
	}
	
	@Override
	public PacketOutPong createPacketData() {
		return new PacketOutPong();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPong.class);
	}

}
