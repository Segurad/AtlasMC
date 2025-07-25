package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class UUIDListField<T> extends AbstractCollectionField<T, List<UUID>> {
	
	public UUIDListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<UUID>> getList, boolean optional) {
		super(key, LIST, has, getList, optional);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value)) {
			if (!useDefault)
				writer.writeListTag(key, TagType.INT_ARRAY, 0);
			return true;
		}
		final List<UUID> list = get.apply(value);
		final int size = list.size();
		if (size == 0) {
			if (!useDefault)
				writer.writeListTag(key, TagType.INT_ARRAY, 0);
			return true;
		}
		writer.writeListTag(key, TagType.INT_ARRAY, size);
		for (int i = 0; i < size; i++) {
			UUID v = list.get(i);
			writer.writeUUID(null, v);
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
		if (listType != TagType.INT_ARRAY)
			throw new NBTException("Expected list of type INT_ARRAY but was: " + listType);
		final List<UUID> list = get.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			UUID v = reader.readUUID();
			list.add(v);
		}
		reader.readNextEntry();
	}

}
