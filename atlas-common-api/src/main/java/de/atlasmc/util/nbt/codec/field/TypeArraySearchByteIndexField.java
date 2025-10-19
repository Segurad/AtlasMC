package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class TypeArraySearchByteIndexField<T, V> extends AbstractCollectionField<T, V[], NBTCodec<V>> {

	private final CharKey indexKey;
	
	public TypeArraySearchByteIndexField(TypeArraySearchByteIndexFieldBuilder<T, V> builder) {
		super(builder);
		this.indexKey = CharKey.literal(builder.getIndexKey());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (hasData != null && !hasData.applyAsBoolean(value))
			return true;
		final V[] array = getter.apply(value);
		final int size = array.length;
		if (size == 0)
			return true;
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (array[i] != null)
				count++;
		}
		writer.writeListTag(key, TagType.COMPOUND, count);
		for (int i = 0; i < size; i++) {
			final V entry = array[i];
			if (entry == null)
				continue;
			writer.writeCompoundTag();
			writer.writeByteTag(indexKey, i);
			fieldType.serialize(entry, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected float tag but was: " + listType);
		final V[] array = getter.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry();
			int index;
			if (!indexKey.equals(reader.getFieldName())) {
				reader.mark();
				if (!reader.search(indexKey, TagType.BYTE))
					throw new NBTException("Key field not found: " + indexKey);
				index = reader.readByteTag() & 0xFF; // index is unsigned
				reader.reset();
			} else {
				index = reader.readByteTag() & 0xFF; // index is unsigned
			}
			V entry = fieldType.deserialize(reader, context);
			array[index] = entry;
			reader.readNextEntry();
		}
		reader.readNextEntry();
	}
	
	
	
	

}
