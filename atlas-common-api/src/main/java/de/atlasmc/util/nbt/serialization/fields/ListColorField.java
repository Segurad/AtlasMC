package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ListColorField<T> extends AbstractObjectField<T, Color> {

	public ListColorField(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set) {
		super(key, TagType.LIST, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Color c = get.apply(value);
		if (c == null)
			return true;
		writer.writeListTag(key, TagType.FLOAT, 4);
		writer.writeFloatTag(null, c.r / 255.0f);
		writer.writeFloatTag(null, c.g / 255.0f);
		writer.writeFloatTag(null, c.b / 255.0f);
		writer.writeFloatTag(null, c.a / 255.0f);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		if (reader.getListType() != TagType.FLOAT)
			throw new NBTException("Expected float tag but was: " + reader.getListType());
		reader.readNextEntry();
		Color c = new Color(reader.readFloatTag(), reader.readFloatTag(), reader.readFloatTag(), reader.readFloatTag());
		reader.readNextEntry();
		set.accept(value, c);
	}

}
