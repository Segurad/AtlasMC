package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ByteArrayField<T> extends AbstractObjectField<T, byte[]> {

	public ByteArrayField(CharSequence key, Function<T, byte[]> get, BiConsumer<T, byte[]> set) {
		super(key, BYTE_ARRAY, get, set);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		byte[] v = get.apply(value);
		if (v == null || v.length == 0)
			return true;
		writer.writeByteArrayTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		byte[] v = reader.readByteArrayTag();
		set.accept(value, v);
	}

}
