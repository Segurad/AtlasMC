package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class StringField<T> extends AbstractObjectField<T, String> {
	
	public StringField(CharSequence key, Function<T, String> get, BiConsumer<T, String> set) {
		super(key, STRING, get, set);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		writer.writeStringTag(key, get.apply(value));
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, reader.readStringTag());
	}

}
