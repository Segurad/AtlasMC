package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class TypeCollectionInnerSearchKeyField<T, K extends NBTSerializable, C extends Namespaced> extends AbstractCollectionField<T, Collection<K>> {

	private final CharKey keyField;
	private final BiFunction<T, C, K> constructor;
	private final Function<K, C> keyReverse;
	private final Function<NamespacedKey, C> keySupplier;
	
	public TypeCollectionInnerSearchKeyField(CharSequence key, ToBooleanFunction<T> has, Function<T, Collection<K>> get, CharSequence keyField, Function<NamespacedKey, C> keySupplier, BiFunction<T, C, K> constructor, Function<K, C> keyReverse, boolean optional) {
		super(key, LIST, has, get, optional);
		this.keyField = CharKey.literal(keyField);
		this.constructor = constructor;
		this.keyReverse = keyReverse;
		this.keySupplier = keySupplier;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value)) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		final Collection<K> list = get.apply(value);
		final int size = list.size();
		if (size == 0) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (K v : list) {
			writer.writeCompoundTag();
			C valueKey = keyReverse.apply(v);
			NamespacedKey nKey = context.clientSide ? valueKey.getClientKey() : valueKey.getNamespacedKey();
			writer.writeNamespacedKey(keyField, nKey);
			@SuppressWarnings("unchecked")
			NBTCodec<K> handler = (NBTCodec<K>) v.getNBTCodec();
			handler.serialize(v, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected list of type COMPOUND but was: " + listType);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			NamespacedKey key;
			if (!keyField.equals(reader.getFieldName())) {
				reader.mark();
				if (!reader.search(keyField, TagType.STRING))
					throw new NBTException("Key field not found: " + keyField);
				key = reader.readNamespacedKey();
				reader.reset();
			} else {
				key = reader.readNamespacedKey();
			}
			C valueKey = keySupplier.apply(key);
			K v = constructor.apply(value, valueKey);
			@SuppressWarnings("unchecked")
			NBTCodec<K> handler = (NBTCodec<K>) v.getNBTCodec();
			handler.deserialize(v, reader, context);
		}
		reader.readNextEntry();
	}

}
