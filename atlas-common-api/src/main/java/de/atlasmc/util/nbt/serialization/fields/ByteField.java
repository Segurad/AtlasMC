package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.function.ToByteFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ByteField<T> extends NBTField<T> {
	
	private final ToByteFunction<T> get;
	private final ObjByteConsumer<T> set;
	private final byte defaultValue;
	
	public ByteField(CharSequence key, ToByteFunction<T> get, ObjByteConsumer<T> set) {
		super(key, TagType.BYTE, false);
		this.get = get;
		this.set = set;
		this.defaultValue = 0;
	}
	
	public ByteField(CharSequence key, ToByteFunction<T> get, ObjByteConsumer<T> set, byte defaultValue) {
		super(key, TagType.BYTE, true);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		byte v = get.applyAsByte(value);
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
