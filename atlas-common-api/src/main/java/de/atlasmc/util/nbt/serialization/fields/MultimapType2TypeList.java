package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class MultimapType2TypeList<T, K extends Namespaced, V> extends AbstractCollectionField<T, Multimap<K, V>> {

	private final CharKey keyField;
	private final Function<NamespacedKey, K> keySupplier;
	private final NBTSerializationHandler<V> handler;
	
	public MultimapType2TypeList(CharSequence key, ToBooleanFunction<T> has, Function<T, Multimap<K, V>> getMap, CharSequence keyField, Function<NamespacedKey, K> keySupplier, NBTSerializationHandler<V> handler) {
		super(key, LIST, has, getMap, true);
		this.handler = handler;
		this.keyField = CharKey.literal(keyField);
		this.keySupplier = keySupplier;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value)) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		final Multimap<K, V> map = get.apply(value);
		final int size = map.valuesSize();
		if (size == 0) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (Entry<K, Collection<V>> entry : map.entrySet()) {
			final K key = entry.getKey();
			final NamespacedKey nKey = context.clientSide ? key.getClientKey() : key.getNamespacedKey();
			for (V v : entry.getValue()) {
				writer.writeCompoundTag();
				writer.writeNamespacedKey(keyField, nKey);
				handler.serialize(v, writer, context);
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected list of type COMPOUND but was: " + listType);
		final Multimap<K, V> map = get.apply(value);
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
			V entry = handler.deserialize(reader);
			map.put(key, entry);
		}
		reader.readNextEntry();
	}

}
