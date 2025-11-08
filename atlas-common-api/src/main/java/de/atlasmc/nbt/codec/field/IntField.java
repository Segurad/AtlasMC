package de.atlasmc.nbt.codec.field;

import java.io.IOException;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;

public class IntField<T> extends AbstractPrimitiveField<T, ToIntFunction<T>, ObjIntConsumer<T>> {

	private final int defaultValue;
	
	public IntField(PrimitiveFieldBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>> builder) {
		super(builder);
		this.defaultValue = NumberConversion.toInt(builder.getDefaultValue());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		int v = getter.applyAsInt(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeIntTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		setter.accept(value, reader.readIntTag());
	}

}
