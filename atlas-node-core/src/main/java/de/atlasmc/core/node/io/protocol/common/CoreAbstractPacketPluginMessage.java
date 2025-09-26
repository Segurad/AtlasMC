package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.writeIdentifier;

import java.io.IOException;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginMessage;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketPluginMessage<T extends AbstractPacketPluginMessage> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.channel = readIdentifier(in);
		packet.data = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeIdentifier(packet.channel, out);
		out.writeBytes(packet.data);
	}
	
}
