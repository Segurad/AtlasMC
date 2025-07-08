package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ToIntFunction;

import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ByteField<T> extends NBTField<T> {
	
	private final ToIntFunction<T> get;
	private final ObjByteConsumer<T> set;
	private final byte defaultValue;
	
	public ByteField(CharSequence key, ToIntFunction<T> get, ObjByteConsumer<T> set, boolean useDefault, byte defaultValue) {
		super(key, BYTE, useDefault);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		byte v = (byte) get.applyAsInt(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeByteTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readByteTag());
	}

}
