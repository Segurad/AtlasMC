package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieData;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketCookieResponse<T extends AbstractPacketCookieData> implements PacketIO<T> {

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeIdentifier(packet.key, out);
		if (packet.payload == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			writeVarInt(packet.payload.readableBytes(), out);
			out.writeBytes(packet.payload);
		}
	}
	
	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.key = readIdentifier(in);
		if (in.readBoolean()) {
			int size = readArrayLength(in, 5120);
			packet.payload = in.readBytes(size);
		}
	}
	
}
