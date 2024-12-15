package de.atlascore.io.protocol.common;

import java.io.IOException;
import java.net.ProtocolException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.common.AbstractPacketCookieData;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

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
			int size = readVarInt(in);
			if (size > 5120)
				throw new ProtocolException("Cookie payload exceeded 5120 bytes: " + size);
			packet.payload = in.readBytes(size);
		}
	}
	
}
