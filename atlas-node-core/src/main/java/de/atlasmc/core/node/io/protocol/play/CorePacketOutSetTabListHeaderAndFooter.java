package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetTabListHeaderAndFooter;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetTabListHeaderAndFooter implements PacketIO<PacketOutSetTabListHeaderAndFooter> {

	@Override
	public void read(PacketOutSetTabListHeaderAndFooter packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final CodecContext context = handler.getCodecContext();
		packet.header = Chat.STREAM_CODEC.deserialize(in, context);
		packet.footer = Chat.STREAM_CODEC.deserialize(in, context);
	}

	@Override
	public void write(PacketOutSetTabListHeaderAndFooter packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		final CodecContext context = handler.getCodecContext();
		Chat.STREAM_CODEC.serialize(packet.header, out, context);
		Chat.STREAM_CODEC.serialize(packet.footer, out, context);
	}

	@Override
	public PacketOutSetTabListHeaderAndFooter createPacketData() {
		return new PacketOutSetTabListHeaderAndFooter();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetTabListHeaderAndFooter.class);
	}

}
