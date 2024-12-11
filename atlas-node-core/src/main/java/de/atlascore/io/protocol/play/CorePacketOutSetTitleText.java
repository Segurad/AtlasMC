package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetTitleText;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutSetTitleText implements PacketIO<PacketOutSetTitleText> {

	@Override
	public void read(PacketOutSetTitleText packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.title = readTextComponent(in);
	}

	@Override
	public void write(PacketOutSetTitleText packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeTextComponent(packet.title, out);
	}

	@Override
	public PacketOutSetTitleText createPacketData() {
		return new PacketOutSetTitleText();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetTitleText.class);
	}

}
