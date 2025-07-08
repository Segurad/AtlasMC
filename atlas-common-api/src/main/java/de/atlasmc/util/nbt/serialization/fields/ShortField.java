package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ToIntFunction;

import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ShortField<T> extends NBTField<T> {
	
	private final ToIntFunction<T> get;
	private final ObjShortConsumer<T> set;
	private final short defaultValue;
	
	public ShortField(CharSequence key, ToIntFunction<T> get, ObjShortConsumer<T> set, boolean useDefualt, short defaultValue) {
		super(key, SHORT, useDefualt);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		short v = (short) get.applyAsInt(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeShortTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readShortTag());
	}

}
