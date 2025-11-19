package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class MapRegistryValueToVarIntField<T, K extends IDHolder> extends AbstractCollectionField<T, Object2IntMap<K>> {

	private final RegistryKey<K> registry;
	
	public MapRegistryValueToVarIntField(ToBooleanFunction<T> has, Function<T, Object2IntMap<K>> get, RegistryKey<K> registry) {
		super(has, get);
		this.registry = Objects.requireNonNull(registry);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		if (!has.applyAsBoolean(type)) {
			PacketUtil.writeVarInt(0, buf);
			return true;
		}
		final var map = get.apply(type);
		for (var entry : map.object2IntEntrySet()) {
			PacketUtil.writeVarInt(entry.getKey().getID(), buf);
			PacketUtil.writeVarInt(entry.getIntValue(), buf);
		}
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		final int size = PacketUtil.readVarInt(buf);
		if (size == 0)
			return;
		final var map = get.apply(type);
		for (int i = 0; i < size; i++) {
			K key = registry.getValue(PacketUtil.readVarInt(buf));
			int value = PacketUtil.readVarInt(buf);
			map.put(key, value);
		}
	}

}
