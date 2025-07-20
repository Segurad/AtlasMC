package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class IntNullableField<T> extends AbstractObjectField<T, Integer> {
	
	private final Integer defaultValue;
	
	public IntNullableField(CharSequence key, Function<T, Integer> get, BiConsumer<T, Integer> set, boolean useDefault, Integer defaultValue) {
		super(key, INT, get, set, useDefault);
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Integer v = get.apply(value);
		if (useDefault && v == defaultValue)
			return true;
		if (v == null)
			return true;
		writer.writeIntTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readIntTag());
	}

}
