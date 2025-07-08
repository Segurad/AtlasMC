package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class BooleanField<T> extends NBTField<T> {
	
	private final ToBooleanFunction<T> get;
	private final ObjBooleanConsumer<T> set;
	private final boolean defaultValue;
	
	public BooleanField(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set, boolean useDefault, boolean defaultValue) {
		super(key, BYTE, useDefault);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		boolean v = get.applyAsBoolean(value);
		if (useDefault &&  v == defaultValue)
			return true;
		writer.writeByteTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readBoolean());
	}

}
