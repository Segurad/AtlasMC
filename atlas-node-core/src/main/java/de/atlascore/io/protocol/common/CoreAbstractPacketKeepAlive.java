package de.atlascore.io.protocol.common;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.common.AbstractPacketKeepAlive;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketKeepAlive<T extends AbstractPacketKeepAlive>  implements PacketIO<T> {
	
	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.keepAliveID = in.readLong();
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.keepAliveID);
	}

}
