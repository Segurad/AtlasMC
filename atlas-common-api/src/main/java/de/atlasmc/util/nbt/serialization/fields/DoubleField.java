package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ToDoubleFunction;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class DoubleField<T> extends NBTField<T> {
	
	private final ToDoubleFunction<T> supplier;
	private final ObjDoubleConsumer<T> consumer;
	private final double defaultValue;
	
	public DoubleField(CharSequence key, ToDoubleFunction<T> supplier, ObjDoubleConsumer<T> consumer) {
		super(key, TagType.DOUBLE, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = 0;
	}
	
	public DoubleField(CharSequence key, ToDoubleFunction<T> supplier, ObjDoubleConsumer<T> consumer, double defaultValue) {
		super(key, TagType.DOUBLE, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		double v = supplier.applyAsDouble(value);
		if (useDefault && v == defaultValue)
			return;
		writer.writeDoubleTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readDoubleTag());
	}

}
