package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketOutKeepAlive implements PacketIO<PacketOutKeepAlive> {

	@Override
	public void read(PacketOutKeepAlive packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.keepAliveID = in.readLong();
	}

	@Override
	public void write(PacketOutKeepAlive packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.keepAliveID);
	}

	@Override
	public PacketOutKeepAlive createPacketData() {
		return new PacketOutKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutKeepAlive.class);
	}

}
