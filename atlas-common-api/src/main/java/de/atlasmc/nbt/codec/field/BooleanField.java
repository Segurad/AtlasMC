package de.atlasmc.nbt.codec.field;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ToBooleanFunction;

public class BooleanField<T> extends AbstractPrimitiveField<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>> {

	private final boolean defaultValue;

	public BooleanField(PrimitiveFieldBuilder<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>> builder) {
		super(builder);
		this.defaultValue = NumberConversion.toInt(builder.getDefaultValue()) > 0;
	}
	
	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		boolean v = getter.applyAsBoolean(value);
		if (useDefault &&  v == defaultValue)
			return true;
		writer.writeByteTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		setter.accept(value, reader.readBoolean());
	}

}
