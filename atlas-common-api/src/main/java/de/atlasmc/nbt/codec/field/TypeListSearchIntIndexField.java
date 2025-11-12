package de.atlasmc.nbt.codec.field;

import java.io.IOException;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;

public class TypeListSearchIntIndexField<T, V> extends AbstractCollectionField<T, Int2ObjectMap<V>, NBTCodec<V>> {

	private final CharKey indexKey;
	
	public TypeListSearchIntIndexField(TypeListSearchIntIndexFieldBuilder<T, V> builder) {
		super(builder);
		if (fieldType.isField())
			throw new IllegalArgumentException("NBTCodec must not be a field!");
		this.indexKey = CharKey.literal(builder.getIndexKey());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (hasData != null && !hasData.applyAsBoolean(value)) {
			return true;
		}
		final Int2ObjectMap<V> list = getter.apply(value);
		final int size = list.size();
		if (size == 0) {
			return true;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		for (Entry<V> entry : list.int2ObjectEntrySet()) {
			writer.writeCompoundTag();
			writer.writeIntTag(indexKey, entry.getIntKey());
			V v = entry.getValue();
			fieldType.serialize(v, writer, context);
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
		final Int2ObjectMap<V> list = getter.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			int index;
			if (!indexKey.equals(reader.getFieldName())) {
				reader.mark();
				if (!reader.search(indexKey, TagType.INT))
					throw new NBTException("Key field not found: " + indexKey);
				index = reader.readIntTag();
				reader.reset();
			} else {
				index = reader.readIntTag();
			}
			V v = fieldType.deserialize(reader, context);
			list.put(index, v);
		}
		reader.readNextEntry();
	}

}
