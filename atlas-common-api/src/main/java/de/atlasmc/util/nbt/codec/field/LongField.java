package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.function.ObjLongConsumer;
import java.util.function.ToLongFunction;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class LongField<T> extends AbstractPrimitiveField<T, ToLongFunction<T>, ObjLongConsumer<T>> {

	private final long defaultValue;
	
	public LongField(PrimitiveFieldBuilder<T, ToLongFunction<T>, ObjLongConsumer<T>> builder) {
		super(builder);
		this.defaultValue = NumberConversion.toLong(builder.getDefaultValue());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		long v = getter.applyAsLong(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeLongTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		setter.accept(value, reader.readLongTag());
	}

}
