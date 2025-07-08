package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class IntArrayField<T> extends AbstractObjectField<T, int[]> {

	public IntArrayField(CharSequence key, Function<T, int[]> get, BiConsumer<T, int[]> set) {
		super(key, INT_ARRAY, get, set);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		int[] v = get.apply(value);
		if (v == null || v.length == 0)
			return true;
		writer.writeIntArrayTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		int[] v = reader.readIntArrayTag();
		set.accept(value, v);
	}

}
