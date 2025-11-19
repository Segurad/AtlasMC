package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInEditBook;
import io.netty.buffer.ByteBuf;

public class CorePacketInEditBook implements PacketIO<PacketInEditBook> {

	@Override
	public void read(PacketInEditBook packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = readVarInt(in);
		int pageCount = readVarInt(in);
		if (pageCount < 0 || pageCount > 200)
			throw new ProtocolException("Invalid page count must be between 0 and 200: " + pageCount);
		if (pageCount > 0) {
			String[] pages = new String[pageCount];
			for (int i = 0; i < pageCount; i++)
				pages[i] = StringCodec.readString(in, 8192);
			packet.pages = pages;
		}
		if (in.readBoolean())
			packet.title = StringCodec.readString(in, 128);
	}

	@Override
	public void write(PacketInEditBook packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slot, out);
		String[] pages = packet.pages;
		if (pages == null || pages.length == 0) {
			writeVarInt(0, out);
		} else {
			writeVarInt(pages.length, out);
			for (String s : pages)
				StringCodec.writeString(s, out);
		}
		String title = packet.title;
		if (title == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			StringCodec.writeString(title, out);
		}
	}
	
	@Override
	public PacketInEditBook createPacketData() {
		return new PacketInEditBook();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInEditBook.class);
	}

}
