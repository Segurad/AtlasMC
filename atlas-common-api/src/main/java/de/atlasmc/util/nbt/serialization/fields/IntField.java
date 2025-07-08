package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class IntField<T> extends NBTField<T> {
	
	private final ToIntFunction<T> get;
	private final ObjIntConsumer<T> set;
	private final int defaultValue;
	
	public IntField(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set, boolean useDefault, int defaultValue) {
		super(key, INT, useDefault);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		int v = get.applyAsInt(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeIntTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readIntTag());
	}

}
