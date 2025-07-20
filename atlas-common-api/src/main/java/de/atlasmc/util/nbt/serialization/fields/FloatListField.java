package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import it.unimi.dsi.fastutil.floats.FloatList;

public class FloatListField<T> extends AbstractCollectionField<T, FloatList> {

	public FloatListField(CharSequence key, ToBooleanFunction<T> has, Function<T, FloatList> getCollection) {
		super(key, LIST, has, getCollection, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final FloatList list = get.apply(value);
		final int size = list.size();
		if (size == 0)
			return true;
		writer.writeListTag(key, TagType.FLOAT, size);
		for (int i = 0; i < size; i++) {
			writer.writeFloatTag(null, list.getFloat(i));
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
		if (listType != TagType.FLOAT)
			throw new NBTException("Expected list of type FLOAT but was: " + listType);
		final FloatList list = get.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			list.add(reader.readFloatTag());
		}
		reader.readNextEntry();
	}

}
