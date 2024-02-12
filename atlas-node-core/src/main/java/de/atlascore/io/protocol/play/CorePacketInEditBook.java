package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.protocol.play.PacketInEditBook;
import io.netty.buffer.ByteBuf;

public class CorePacketInEditBook implements PacketIO<PacketInEditBook> {

	@Override
	public void read(PacketInEditBook packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setSlot(readVarInt(in));
		int pageCount = readVarInt(in);
		if (pageCount < 0 || pageCount > 200)
			throw new ProtocolException("Invalid page count must be between 0 and 200: " + pageCount);
		if (pageCount > 0) {
			String[] pages = new String[pageCount];
			for (int i = 0; i < pageCount; i++)
				pages[i] = readString(in, 8192);
			packet.setPages(pages);
		}
		if (in.readBoolean())
			packet.setTitle(readString(in, 128));
	}

	@Override
	public void write(PacketInEditBook packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getSlot(), out);
		String[] pages = packet.getPages();
		if (pages == null || pages.length == 0) {
			writeVarInt(0, out);
		} else {
			writeVarInt(pages.length, out);
			for (String s : pages)
				writeString(s, out);
		}
		String title = packet.getTitle();
		if (title == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			writeString(title, out);
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
