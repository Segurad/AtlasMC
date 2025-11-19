package de.atlasmc.io.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

import de.atlasmc.IDHolder;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.Multimap;
import io.netty.buffer.ByteBuf;

public class MultimapTypeToCodecField<T, K extends IDHolder, V> extends AbstractCollectionField<T, Multimap<K, V>> {

	private final IntFunction<K> keySupplier;
	private final StreamCodec<V> codec;
	
	public MultimapTypeToCodecField(ToBooleanFunction<T> has, Function<T, Multimap<K, V>> get, IntFunction<K> keySupplier, StreamCodec<V> codec) {
		super(has, get);
		this.keySupplier = Objects.requireNonNull(keySupplier);
		this.codec = Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		if (!has.applyAsBoolean(type)) {
			PacketUtil.writeVarInt(0, buf);
			return true;
		}
		final Multimap<K, V> map = get.apply(type);
		PacketUtil.writeVarInt(map.valuesSize(), buf);
		for (var entry : map.entrySet()) {
			final int id = entry.getKey().getID();
			for (V value : entry.getValue()) {
				PacketUtil.writeVarInt(id, buf);
				codec.serialize(value, buf, context);
			}
		}
		return true;
	}

	@Override
	public void deserialize(T type, ByteBuf buf, CodecContext context) throws IOException {
		final int size = PacketUtil.readVarInt(buf);
		if (size == 0)
			return;
		final Multimap<K, V> map = get.apply(type);
		for (int i = 0; i < size; i++) {
			K key = keySupplier.apply(PacketUtil.readVarInt(buf));
			V value = codec.deserialize(null, buf, context);
			map.put(key, value);
		}
	}

}
