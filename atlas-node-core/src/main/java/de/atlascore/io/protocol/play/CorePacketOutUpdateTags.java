package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateTags;
import de.atlasmc.tag.Tag;
import de.atlasmc.util.map.Multimap;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutUpdateTags implements PacketIO<PacketOutUpdateTags> {

	@Override
	public void read(PacketOutUpdateTags packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final int size = readVarInt(in);
		if (size <= 0)
			return;
		Map<NamespacedKey, Map<NamespacedKey, int[]>> types = packet.rawTags = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			NamespacedKey type = readIdentifier(in);
			final int tagCount = readVarInt(in);
			if (tagCount <= 0)
				continue;
			Map<NamespacedKey, int[]> tags = new HashMap<>(tagCount);
			types.put(type, tags);
			for (int j = 0; j < tagCount; j++) {
				NamespacedKey key = readIdentifier(in);
				final int count = readVarInt(in);
				int[] data = new int[count];
				for (int k = 0; k < count; k++)
					data[k] = readVarInt(in);
				tags.put(key, data);
			}
		}
	}

	@Override
	public void write(PacketOutUpdateTags packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		if (packet.tags != null) {
			Multimap<NamespacedKey, Tag<?>> tags = packet.tags;
			if (tags == null || tags.isEmpty()) {
				writeVarInt(0, out);
				return;
			}
			for (NamespacedKey key : tags.keySet()) {
				writeIdentifier(key, out);
				Collection<Tag<?>> values = tags.get(key);
				if (values == null || values.isEmpty()) {
					writeVarInt(0, out);
					continue;
				}
				for (Tag<?> tag : values) {
					tag.write(out);
				}
			}
		} else if (packet.rawTags != null) {
			Map<NamespacedKey, Map<NamespacedKey, int[]>> tags = packet.rawTags;
			if (tags == null || tags.isEmpty()) {
				writeVarInt(0, out);
				return;
			}
			for (NamespacedKey key : tags.keySet()) {
				writeIdentifier(key, out);
				Map<NamespacedKey, int[]> values = tags.get(key);
				if (values == null || values.isEmpty()) {
					writeVarInt(0, out);
					continue;
				}
				for (NamespacedKey tag : values.keySet()) {
					writeIdentifier(tag, out);
					final int[] data = values.get(tag);
					writeVarInt(data.length, out);
					for (int i : data)
						writeVarInt(i, out);
				}
			}
		} else {
			writeVarInt(0, out);
		}
	}
	
	@Override
	public PacketOutUpdateTags createPacketData() {
		return new PacketOutUpdateTags();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateTags.class);
	}
	
}
