package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class FloatField<T> extends NBTField<T> {
	
	private final ToFloatFunction<T> supplier;
	private final ObjFloatConsumer<T> consumer;
	private final float defaultValue;
	
	public FloatField(CharSequence key, ToFloatFunction<T> supplier, ObjFloatConsumer<T> consumer) {
		super(key, TagType.FLOAT, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = 0;
	}
	
	public FloatField(CharSequence key, ToFloatFunction<T> supplier, ObjFloatConsumer<T> consumer, float defaultValue) {
		super(key, TagType.FLOAT, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		float v = supplier.applyAsFloat(value);
		if (useDefault && v == defaultValue)
			return;
		writer.writeFloatTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readFloatTag());
	}

}
