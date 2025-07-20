package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class TypeListSearchIntIndexField<T, K> extends AbstractCollectionField<T, List<K>> {

	private final CharKey indexKey;
	private final NBTSerializationHandler<K> handler;
	
	public TypeListSearchIntIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, List<K>> getList, NBTSerializationHandler<K> handler, boolean optional) {
		super(key, LIST, has, getList, optional);
		this.handler = handler;
		this.indexKey = CharKey.literal(indexKey);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value)) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		final List<K> list = get.apply(value);
		final int size = list.size();
		if (size == 0) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
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
			K v = list.get(i);
			handler.serialize(v, writer, context);
			writer.writeEndTag();
		}
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
		final List<K> list = get.apply(value);
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
			K v = handler.deserialize(reader, context);
			list.add(index, v);
		}
		reader.readNextEntry();
	}

}
