package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.net.ProtocolException;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieData;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketStoreCookie<T extends AbstractPacketCookieData> implements PacketIO<T> {

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeIdentifier(packet.key, out);
		if (packet.payload == null) {
			writeVarInt(0, out);
		} else {
			writeVarInt(packet.payload.readableBytes(), out);
			out.writeBytes(packet.payload);
		}
	}
	
	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.key = readIdentifier(in);
		int size = readVarInt(in);
		if (size == 0)
			return;
		if (size > 5120)
			throw new ProtocolException("Cookie payload exceeded 5120 bytes: " + size);
		packet.payload = in.readBytes(size);
	}
	
}
