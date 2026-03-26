package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketKeepAlive;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketKeepAlive<T extends AbstractPacketKeepAlive>  implements PacketCodec<T> {
	
	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.keepAliveID = in.readLong();
	}

	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.keepAliveID);
	}

}
