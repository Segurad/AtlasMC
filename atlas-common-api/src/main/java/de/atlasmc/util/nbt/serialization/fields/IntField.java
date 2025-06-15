package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class IntField<T> extends NBTField<T> {
	
	private final ToIntFunction<T> supplier;
	private final ObjIntConsumer<T> consumer;
	private final int defaultValue;
	
	public IntField(CharSequence key, ToIntFunction<T> supplier, ObjIntConsumer<T> consumer) {
		super(key, TagType.INT, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = 0;
	}
	
	public IntField(CharSequence key, ToIntFunction<T> supplier, ObjIntConsumer<T> consumer, int defaultValue) {
		super(key, TagType.INT, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		int v = supplier.applyAsInt(value);
		if (useDefault && v == defaultValue)
			return;
		writer.writeIntTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readIntTag());
	}

}
