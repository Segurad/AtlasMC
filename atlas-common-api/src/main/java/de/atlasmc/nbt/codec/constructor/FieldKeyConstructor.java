package de.atlasmc.nbt.codec.constructor;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class FieldKeyConstructor<T> implements Constructor<T> {

	private final Function<String, T> constructor;
	
	public FieldKeyConstructor(Function<String, T> constructor) {
		this.constructor = Objects.requireNonNull(constructor);
	}
	
	@Override
	public T construct(NBTReader reader, CodecContext context) throws IOException {
		return constructor.apply(reader.getFieldName().toString());
	}

	@Override
	public void serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		// not required
	}

}
