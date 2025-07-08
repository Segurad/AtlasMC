package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ToDoubleFunction;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class DoubleField<T> extends NBTField<T> {
	
	private final ToDoubleFunction<T> get;
	private final ObjDoubleConsumer<T> set;
	private final double defaultValue;
	
	public DoubleField(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set, boolean useDefault, double defaultValue) {
		super(key, DOUBLE, useDefault);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		double v = get.applyAsDouble(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeDoubleTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readDoubleTag());
	}

}
