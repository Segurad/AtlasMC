package de.atlascore.io.protocol.play;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetActionBarText;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import java.io.IOException;

public class CorePacketOutSetActionBarText implements PacketIO<PacketOutSetActionBarText> {

	@Override
	public void read(PacketOutSetActionBarText packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.text = readTextComponent(in);
	}

	@Override
	public void write(PacketOutSetActionBarText packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeTextComponent(packet.text, out);
	}

	@Override
	public PacketOutSetActionBarText createPacketData() {
		return new PacketOutSetActionBarText();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetActionBarText.class);
	}

}
