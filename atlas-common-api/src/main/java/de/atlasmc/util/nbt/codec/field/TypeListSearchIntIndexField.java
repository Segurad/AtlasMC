package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.List;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class TypeListSearchIntIndexField<T, V> extends AbstractCollectionField<T, List<V>, NBTCodec<V>> {

	private final CharKey indexKey;
	
	public TypeListSearchIntIndexField(TypeListSearchIntIndexFieldBuilder<T, V> builder) {
		super(builder);
		this.indexKey = CharKey.literal(builder.getIndexKey());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (hasData != null && !hasData.applyAsBoolean(value)) {
			return true;
		}
		final List<V> list = getter.apply(value);
		final int size = list.size();
		if (size == 0) {
			return true;
		}
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (list.get(i) != null)
				count++;
		}
		writer.writeListTag(key, TagType.COMPOUND, count);
		for (int i = 0; i < size; i++) {
			writer.writeCompoundTag();
			writer.writeIntTag(indexKey, i);
			V v = list.get(i);
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
		final List<V> list = getter.apply(value);
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
			list.add(index, v);
		}
		reader.readNextEntry();
	}

}
