package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToShortFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ShortField<T> extends NBTField<T> {
	
	private final ToShortFunction<T> supplier;
	private final ObjShortConsumer<T> consumer;
	private final short defaultValue;
	
	public ShortField(CharSequence key, ToShortFunction<T> supplier, ObjShortConsumer<T> consumer) {
		super(key, TagType.SHORT, false);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = 0;
	}
	
	public ShortField(CharSequence key, ToShortFunction<T> supplier, ObjShortConsumer<T> consumer, short defaultValue) {
		super(key, TagType.SHORT, true);
		this.supplier = supplier;
		this.consumer = consumer;
		this.defaultValue = defaultValue;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		short v = supplier.applyAsShort(value);
		if (useDefault && v == defaultValue)
			return;
		writer.writeShortTag(key, v);
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readShortTag());
	}

}
