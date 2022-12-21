package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketOutKeepAlive extends CoreAbstractHandler<PacketOutKeepAlive> {

	@Override
	public void read(PacketOutKeepAlive packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setKeepAlive(in.readLong());
	}

	@Override
	public void write(PacketOutKeepAlive packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getKeepAlive());
	}

	@Override
	public PacketOutKeepAlive createPacketData() {
		return new PacketOutKeepAlive();
	}

}
