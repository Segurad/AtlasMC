package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class MultimapType2TypeList<K extends Namespaced, V> extends ObjectType<Multimap<K, V>> {

	private final Function<NamespacedKey, K> keySupplier;
	private final NBTCodec<V> codec;
	private final CharKey keyField;
	
	public MultimapType2TypeList(CharSequence keyField, Function<NamespacedKey, K> keySupplier, NBTCodec<V> codec) {
		this.codec = Objects.requireNonNull(codec);
		this.keySupplier = Objects.requireNonNull(keySupplier);
		this.keyField = CharKey.literal(Objects.requireNonNull(keyField));
	}

	@Override
	public boolean serialize(CharSequence key, Multimap<K, V> value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.valuesSize();
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (Entry<K, Collection<V>> entry : value.entrySet()) {
			final K k = entry.getKey();
			final NamespacedKey nKey = context.clientSide ? k.getClientKey() : k.getNamespacedKey();
			for (V v : entry.getValue()) {
				writer.writeCompoundTag();
				writer.writeNamespacedKey(keyField, nKey);
				codec.serialize(v, writer, context);
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Multimap<K, V> deserialize(Multimap<K, V> value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return value;
		}
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected list of type COMPOUND but was: " + listType);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			NamespacedKey rawKey;
			if (!keyField.equals(reader.getFieldName())) {
				reader.mark();
				if (!reader.search(keyField, TagType.STRING))
					throw new NBTException("Key field not found: " + keyField);
				rawKey = reader.readNamespacedKey();
				reader.reset();
			} else {
				rawKey = reader.readNamespacedKey();
			}
			K key = keySupplier.apply(rawKey);
			V entry = codec.deserialize(reader);
			value.put(key, entry);
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return LIST;
	}

}
