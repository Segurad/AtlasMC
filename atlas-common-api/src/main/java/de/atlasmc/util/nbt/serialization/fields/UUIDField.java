package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class UUIDField<T> extends AbstractObjectField<T, UUID> {

	public UUIDField(CharSequence key, Function<T, UUID> get, BiConsumer<T, UUID> set) {
		super(key, List.of(TagType.INT_ARRAY, TagType.STRING), get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		UUID v = get.apply(value);
		if (v == null)
			return true;
		writer.writeUUID(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		switch (reader.getType()) {
		case INT_ARRAY:
			set.accept(value, reader.readUUID());
		case STRING:
			set.accept(value, UUID.fromString(reader.readStringTag()));
		// list of int will be ignored
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
		
	}

}
