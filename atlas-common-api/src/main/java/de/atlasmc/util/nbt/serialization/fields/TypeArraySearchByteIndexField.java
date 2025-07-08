package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class TypeArraySearchByteIndexField<T, K> extends AbstractCollectionField<T, K[]> {

	private final CharKey indexKey;
	private final NBTSerializationHandler<K> handler;
	
	public TypeArraySearchByteIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, K[]> getArray, NBTSerializationHandler<K> handler) {
		super(key, LIST, has, getArray, true);
		this.handler = handler;
		this.indexKey = CharKey.literal(indexKey);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final K[] array = get.apply(value);
		final int size = array.length;
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (array[i] != null)
				count++;
		}
		writer.writeListTag(key, TagType.COMPOUND, count);
		for (int i = 0; i < size; i++) {
			final K entry = array[i];
			if (entry == null)
				continue;
			writer.writeCompoundTag();
			writer.writeByteTag(indexKey, i);
			handler.serialize(entry, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		if (reader.getListType() != TagType.COMPOUND)
			throw new NBTException("Expected float tag but was: " + reader.getListType());
		final K[] array = get.apply(value);
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
			K entry = handler.deserialize(reader, context);
			array[index] = entry;
			reader.readNextEntry();
		}
		reader.readNextEntry();
	}
	
	
	
	

}
