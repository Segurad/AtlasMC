package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ColorField<T> extends AbstractObjectField<T, Color> {

	private final int defaultValue;
	
	public ColorField(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set, boolean useDefault, int defaultValue) {
		super(key, List.of(TagType.INT, TagType.LIST), get, set, useDefault);
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		Color c = get.apply(value);
		if (c == null)
			return true;
		int v = c.asARGB();
		if (useDefault && v == defaultValue)
			return true;
		writer.writeIntTag(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		switch (reader.getType()) {
		case TagType.INT: {
			Color c = Color.fromARGB(reader.readIntTag());
			set.accept(value, c);
			break;
		}
		case TagType.LIST: {
			if (reader.getListType() != TagType.FLOAT)
				throw new NBTException("Expected float tag but was: " + reader.getListType());
			reader.readNextEntry();
			Color c = new Color(reader.readFloatTag(), reader.readFloatTag(), reader.readFloatTag(), reader.readFloatTag());
			reader.readNextEntry();
			set.accept(value, c);
			break;
		}
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
	}

}
