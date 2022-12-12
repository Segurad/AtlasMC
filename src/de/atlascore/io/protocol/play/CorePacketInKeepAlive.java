package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInKeepAlive;
import io.netty.buffer.ByteBuf;

public class CorePacketInKeepAlive extends CoreAbstractHandler<PacketInKeepAlive> {

	@Override
	public void read(PacketInKeepAlive packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setKeepAliveID(in.readLong());
	}

	@Override
	public void write(PacketInKeepAlive packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.getKeepAliveID());
	}

	@Override
	public PacketInKeepAlive createPacketData() {
		return new PacketInKeepAlive();
	}

}
