package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class StringListField<T> extends AbstractCollectionField<T, List<String>> {

	public StringListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<String>> getCollection) {
		super(key, LIST, has, getCollection, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final List<String> list = get.apply(value);
		final int size = list.size();
		if (size == 0)
			return true;
		writer.writeListTag(key, TagType.STRING, size);
		for (int i = 0; i < size; i++) {
			writer.writeStringTag(null, list.get(i));
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.STRING)
			throw new NBTException("Expected list of type STRING but was: " + listType);
		final List<String> list = get.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			list.add(reader.readStringTag());
		}
		reader.readNextEntry();
	}

}
