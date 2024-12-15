package de.atlascore.io.protocol.common;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.common.AbstractPacketTransfer;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public abstract class CoreAbstractPacketTransfer<T extends AbstractPacketTransfer> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.host = readString(in);
		packet.port = readVarInt(in);
	}
	
	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.host, out);
		writeVarInt(packet.port, out);
	}
	
}
