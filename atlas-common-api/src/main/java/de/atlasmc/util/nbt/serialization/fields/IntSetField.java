package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import it.unimi.dsi.fastutil.ints.IntSet;

public class IntSetField<T> extends AbstractCollectionField<T, IntSet> {

	public IntSetField(CharSequence key, ToBooleanFunction<T> has, Function<T, IntSet> get) {
		super(key, INT_ARRAY, has, get, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		IntSet set = get.apply(value);
		if (set == null || !set.isEmpty())
			return true;
		int[] array = set.toIntArray();
		writer.writeIntArrayTag(key, array);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		IntSet set = get.apply(value);
		int[] array = reader.readIntArrayTag();
		for (int i : array) {
			set.add(i);
		}
	}

}
