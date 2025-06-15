package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class BooleanField<T> extends NBTField<T> {
	
	private final ToBooleanFunction<T> supplier;
	private final ObjBooleanConsumer<T> consumer;
	private final boolean defaultValue;
	
	public BooleanField(CharSequence key, ToBooleanFunction<T> supplier, ObjBooleanConsumer<T> consumer) {
		super(key, TagType.BYTE, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = false;
	}
	
	public BooleanField(CharSequence key, ToBooleanFunction<T> supplier, ObjBooleanConsumer<T> consumer, boolean defaultValue) {
		super(key, TagType.BYTE, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		boolean v = supplier.applyAsBoolean(value);
		if (useDefault &&  v == defaultValue)
			return;
		writer.writeByteTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readBoolean());
	}

}
