package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;

import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToShortFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ShortField<T> extends NBTField<T> {
	
	private final ToShortFunction<T> get;
	private final ObjShortConsumer<T> set;
	private final short defaultValue;
	
	public ShortField(CharSequence key, ToShortFunction<T> get, ObjShortConsumer<T> set) {
		super(key, TagType.SHORT, false);
		this.get = get;
		this.set = set;
		this.defaultValue = 0;
	}
	
	public ShortField(CharSequence key, ToShortFunction<T> get, ObjShortConsumer<T> set, short defaultValue) {
		super(key, TagType.SHORT, true);
		this.get = get;
		this.set = set;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		short v = get.applyAsShort(value);
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
