package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.function.ToIntFunction;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ShortField<T> extends AbstractPrimitiveField<T, ToIntFunction<T>, ObjShortConsumer<T>> {

	private final short defaultValue;
	
	public ShortField(PrimitiveFieldBuilder<T, ToIntFunction<T>, ObjShortConsumer<T>> builder) {
		super(builder);
		this.defaultValue = NumberConversion.toShort(builder.getDefaultValue());
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		short v = (short) getter.applyAsInt(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeShortTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		setter.accept(value, reader.readShortTag());
	}

}
