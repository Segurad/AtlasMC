package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketTransfer;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketTransfer<T extends AbstractPacketTransfer> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.host = StringCodec.readString(in);
		packet.port = readVarInt(in);
	}
	
	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		StringCodec.writeString(packet.host, out);
		writeVarInt(packet.port, out);
	}
	
}
