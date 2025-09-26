package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetSubtitleText;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetSubtitleText implements PacketIO<PacketOutSetSubtitleText> {

	@Override
	public void read(PacketOutSetSubtitleText packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.subtitle = readTextComponent(in);
	}

	@Override
	public void write(PacketOutSetSubtitleText packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeTextComponent(packet.subtitle, out);
	}

	@Override
	public PacketOutSetSubtitleText createPacketData() {
		return new PacketOutSetSubtitleText();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetSubtitleText.class);
	}

}
