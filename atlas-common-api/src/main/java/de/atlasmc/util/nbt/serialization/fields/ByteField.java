package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.function.ToByteFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ByteField<T> extends NBTField<T> {
	
	private final ToByteFunction<T> supplier;
	private final ObjByteConsumer<T> consumer;
	private final byte defaultValue;
	
	public ByteField(CharSequence key, ToByteFunction<T> supplier, ObjByteConsumer<T> consumer) {
		super(key, TagType.BYTE, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = 0;
	}
	
	public ByteField(CharSequence key, ToByteFunction<T> supplier, ObjByteConsumer<T> consumer, byte defaultValue) {
		super(key, TagType.BYTE, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		byte v = supplier.applyAsByte(value);
		if (useDefault && v == defaultValue)
			return;
		writer.writeByteTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readByteTag());
	}

}
