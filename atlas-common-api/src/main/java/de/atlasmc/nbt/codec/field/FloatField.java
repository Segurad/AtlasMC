package de.atlasmc.nbt.codec.field;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToFloatFunction;

public class FloatField<T> extends AbstractPrimitiveField<T, ToFloatFunction<T>, ObjFloatConsumer<T>> {

	private final float defaultValue;
	
	public FloatField(PrimitiveFieldBuilder<T, ToFloatFunction<T>, ObjFloatConsumer<T>> builder) {
		super(builder);
		this.defaultValue = NumberConversion.toFloat(builder.getDefaultValue());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		float v = getter.applyAsFloat(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeFloatTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		setter.accept(value, reader.readFloatTag());
	}

}
