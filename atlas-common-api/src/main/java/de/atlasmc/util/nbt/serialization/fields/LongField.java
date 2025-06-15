package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ObjLongConsumer;
import java.util.function.ToLongFunction;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class LongField<T> extends NBTField<T> {
	
	private final ToLongFunction<T> supplier;
	private final ObjLongConsumer<T> consumer;
	private final long defaultValue;
	
	public LongField(CharSequence key, ToLongFunction<T> supplier, ObjLongConsumer<T> consumer) {
		super(key, TagType.LONG, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = 0;
	}
	
	public LongField(CharSequence key, ToLongFunction<T> supplier, ObjLongConsumer<T> consumer, long defaultValue) {
		super(key, TagType.LONG, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		long v = supplier.applyAsLong(value);
		if (useDefault && v == defaultValue)
			return;
		writer.writeLongTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readLongTag());
	}

}
