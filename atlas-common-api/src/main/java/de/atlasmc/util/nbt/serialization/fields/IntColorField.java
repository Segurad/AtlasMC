package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class IntColorField<T> extends AbstractObjectField<T, Color> {

	public IntColorField(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set) {
		super(key, TagType.INT, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Color c = get.apply(value);
		if (c == null)
			return true;
		writer.writeIntTag(key, c.asARGB());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		Color c = Color.fromARGB(reader.readIntTag());
		set.accept(value, c);
	}

}
