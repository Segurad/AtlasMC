package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class MapTypeToCodec<K extends Namespaced, V> implements NBTCodec<Map<K, V>> {

	private final NBTCodec<V> codec;
	private final Function<V, K> keySupplier;
	
	public MapTypeToCodec(NBTCodec<V> codec, Function<V, K> keySupplier) {
		this.codec = Objects.requireNonNull(codec, "codec");
		this.keySupplier = Objects.requireNonNull(keySupplier, "keySupplier");
	}

	@Override
	public boolean serialize(CharSequence key, Map<K, V> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeCompoundTag(key);
		for (var entry : value.entrySet()) {
			var entryKey = entry.getKey();
			var nKey = context.clientSide ? entryKey.getClientKey() : entryKey.getNamespacedKey();
			codec.serialize(nKey, entry.getValue(), writer, context);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Map<K, V> deserialize(Map<K, V> value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		final Map<K, V> map = value != null ? value : new HashMap<>();
		while (reader.getType() != TagType.TAG_END) {
			V entry = codec.deserialize(reader, context);
			map.put(keySupplier.apply(entry), entry);
		}
		reader.readNextEntry();
		return map;
	}
	
	@Override
	public boolean isReuseValue() {
		return true;
	}
	
	@Override
	public Class<?> getType() {
		return Map.class;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.COMPOUND;
	}

}
