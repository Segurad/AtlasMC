package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutChatSuggestions;
import io.netty.buffer.ByteBuf;

public class CorePacketOutChatSuggestions implements PacketCodec<PacketOutChatSuggestions> {

	@Override
	public void deserialize(PacketOutChatSuggestions packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.action = readVarInt(in);
		final int count = readVarInt(in);
		if (count <= 0)
			return;
		List<String> entries = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
			entries.add(StringCodec.readString(in));
		packet.entries = entries;
	}

	@Override
	public void serialize(PacketOutChatSuggestions packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.action, out);
		List<String> entries = packet.entries;
		if (entries == null || entries.isEmpty()) {
			writeVarInt(0, out);
			return;
		}
		for (String s : entries)
			StringCodec.writeString(s, out);
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
