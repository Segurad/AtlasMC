package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutChatSuggestions;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutChatSuggestions implements PacketIO<PacketOutChatSuggestions> {

	@Override
	public void read(PacketOutChatSuggestions packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.action = readVarInt(in);
		final int count = readVarInt(in);
		if (count <= 0)
			return;
		List<String> entries = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
			entries.add(readString(in, 32767));
		packet.entries = entries;
	}

	@Override
	public void write(PacketOutChatSuggestions packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.action, out);
		List<String> entries = packet.entries;
		if (entries == null || entries.isEmpty()) {
			writeVarInt(0, out);
			return;
		}
		for (String s : entries)
			writeString(s, out);
	}

	@Override
	public PacketOutChatSuggestions createPacketData() {
		return new PacketOutChatSuggestions();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutChatSuggestions.class);
	}

}
