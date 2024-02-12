package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetTabListHeaderAndFooter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetTabListHeaderAndFooter implements PacketIO<PacketOutSetTabListHeaderAndFooter> {

	@Override
	public void read(PacketOutSetTabListHeaderAndFooter packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHeader(readString(in));
		packet.setFooter(readString(in));
	}

	@Override
	public void write(PacketOutSetTabListHeaderAndFooter packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getHeader(), out);
		writeString(packet.getFooter(), out);
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
