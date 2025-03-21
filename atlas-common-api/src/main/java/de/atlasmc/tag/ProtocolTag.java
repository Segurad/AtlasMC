package de.atlasmc.tag;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import io.netty.buffer.ByteBuf;

public class ProtocolTag<E extends ProtocolRegistryValue> extends Tag<E> implements IOWriteable, IOReadable {

	private final ProtocolRegistry<E> registry;
	
	public ProtocolTag(NamespacedKey identifier, ProtocolRegistry<E> registry) {
		super(identifier, registry.getType());
		this.registry = registry;
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		values.clear();
		final int size = readVarInt(buf);
		for (int i = 0; i < size; i++) {
			E entry = registry.getByID(readVarInt(buf));
			if (entry == null)
				continue;
			add(entry);
		}
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		final Set<E> values = this.values;
		if (values.isEmpty()) {
			writeVarInt(0, buf);
			return;
		}
		writeVarInt(values.size(), buf);
		for (E entry : values) {
			writeVarInt(entry.getID(), buf);
		}
	}

}
