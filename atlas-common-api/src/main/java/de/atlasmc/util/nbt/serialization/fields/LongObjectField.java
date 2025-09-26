package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class LongObjectField<T> extends AbstractObjectField<T, Long> {
	
	public LongObjectField(CharSequence key, Function<T, Long> get, BiConsumer<T, Long> set) {
		super(key, LONG, get, set, false);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Long v = get.apply(value);
		if (v == null)
			return true;
		writer.writeLongTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readLongTag());
	}

}
