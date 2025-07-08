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
import it.unimi.dsi.fastutil.ints.IntList;

public class IntListField<T> extends AbstractCollectionField<T, IntList> {

	public IntListField(CharSequence key, ToBooleanFunction<T> has, Function<T, IntList> getCollection) {
		super(key, List.of(TagType.INT_ARRAY, TagType.LIST), has, getCollection, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final IntList list = get.apply(value);
		final int size = list.size();
		if (size == 0)
			return true;
		writer.writeListTag(key, TagType.INT, size);
		for (int i = 0; i < size; i++) {
			writer.writeIntTag(null, list.getInt(i));
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		switch(reader.getType()) {
		case LIST: {
			TagType listType = reader.getListType();
			if (listType == TagType.TAG_END || reader.getNextPayload() == 0)
				return;
			if (listType != TagType.INT)
				throw new NBTException("Expected list of type INT but was: " + listType);
			final IntList list = get.apply(value);
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				list.add(reader.readIntTag());
			}
			reader.readNextEntry();
			break;
		}
		case INT_ARRAY:
			int[] array = reader.readIntArrayTag();
			final IntList list =get.apply(value);
			list.addElements(list.size(), array);
			break;
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
	}

}
