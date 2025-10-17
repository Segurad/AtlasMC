package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

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
