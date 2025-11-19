package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketUpdateTags;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.Registries;
import de.atlasmc.tag.ProtocolTag;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.Multimap;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketUpdateTags<T extends AbstractPacketUpdateTags> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count == 0) {
			packet.tags = Multimap.of();
			return;
		}
		Multimap<NamespacedKey, ProtocolTag<?>> tags = new ArrayListMultimap<>();
		for (int i = 0; i < count; i++) {
			NamespacedKey registryKey = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
			ProtocolRegistry<?> registry = Registries.getRegistry(registryKey);
			final int tagCount = readVarInt(in);
			for (int j = 0; j < tagCount; j++) {
				NamespacedKey tagKey = NamespacedKey.STREAM_CODEC.deserialize(null, in, null);
				ProtocolTag<?> tag = new ProtocolTag<>(tagKey, registry);
				tag.read(in);
				tags.put(registryKey, tag);
			}
		}
		packet.tags = tags;
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		Multimap<NamespacedKey, ProtocolTag<?>> tags = packet.tags;
		if (tags == null || tags.isEmpty()) {
			writeVarInt(0, out);
			return;
		}
		for (NamespacedKey key : tags.keySet()) {
			NamespacedKey.STREAM_CODEC.serialize(key, out, null);
			Collection<ProtocolTag<?>> values = tags.get(key);
			if (values == null || values.isEmpty()) {
				writeVarInt(0, out);
				continue;
			}
			writeVarInt(values.size(), out);
			for (ProtocolTag<?> tag : values) {
				NamespacedKey.STREAM_CODEC.serialize(tag.getClientKey(), out, null);
				tag.write(out);
			}
		}
	}

}
