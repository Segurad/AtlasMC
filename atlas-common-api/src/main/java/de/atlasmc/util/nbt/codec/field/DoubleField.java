package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ToDoubleFunction;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class DoubleField<T> extends AbstractPrimitiveField<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>> {

	private final double defaultValue;
	
	public DoubleField(PrimitiveFieldBuilder<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>> builder) {
		super(builder);
		this.defaultValue = NumberConversion.toDouble(builder.getDefaultValue());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		double v = getter.applyAsDouble(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeDoubleTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		setter.accept(value, reader.readDoubleTag());
	}

}
