package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ObjLongConsumer;
import java.util.function.ToLongFunction;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class LongField<T> extends NBTField<T> {
	
	private final ToLongFunction<T> get;
	private final ObjLongConsumer<T> set;
	private final long defaultValue;
	
	public LongField(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set, boolean useDefault, long defaultValue) {
		super(key, LONG, useDefault);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		long v = get.applyAsLong(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeLongTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readLongTag());
	}

}
