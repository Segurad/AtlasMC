package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;

public class TypeCollectionInnerSearchKeyField<T, V extends NBTSerializable, C extends Namespaced> extends AbstractCollectionField<T, Collection<V>, BiFunction<T, C, V>> {

	private final CharKey keyField;
	private final Function<V, C> keyReverse;
	private final Function<NamespacedKey, C> keySupplier;
	
	public TypeCollectionInnerSearchKeyField(TypeCollectionInnerSearchKeyFieldBuilder<T, V, C> builder) {
		super(builder);
		this.keyField = CharKey.literal(builder.getKeyField());
		this.keyReverse = Objects.requireNonNull(builder.getKeyReverse());
		this.keySupplier = Objects.requireNonNull(builder.getKeySupplier());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (hasData != null && !hasData.applyAsBoolean(value)) {
			return true;
		}
		final Collection<V> list = getter.apply(value);
		final int size = list.size();
		if (size == 0) {
			return true;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (V v : list) {
			writer.writeCompoundTag();
			C valueKey = keyReverse.apply(v);
			NamespacedKey nKey = context.clientSide ? valueKey.getClientKey() : valueKey.getNamespacedKey();
			writer.writeNamespacedKey(keyField, nKey);
			@SuppressWarnings("unchecked")
			NBTCodec<V> handler = (NBTCodec<V>) v.getNBTCodec();
			handler.serializePartial(v, writer, context);
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
			V v = fieldType.apply(value, valueKey);
			@SuppressWarnings("unchecked")
			NBTCodec<V> handler = (NBTCodec<V>) v.getNBTCodec();
			handler.deserializePartial(v, reader, context);
		}
		reader.readNextEntry();
	}

}
