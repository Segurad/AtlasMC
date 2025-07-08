package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class FloatField<T> extends NBTField<T> {
	
	private final ToFloatFunction<T> get;
	private final ObjFloatConsumer<T> set;
	private final float defaultValue;
	
	public FloatField(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set, boolean useDefault, float defaultValue) {
		super(key, FLOAT, useDefault);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		float v = get.applyAsFloat(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeFloatTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readFloatTag());
	}

}
