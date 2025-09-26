package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.joml.Quaternionf;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class QuaternionfField<T> extends AbstractObjectField<T, Quaternionf> {

	public QuaternionfField(CharSequence key, Function<T, Quaternionf> get, BiConsumer<T, Quaternionf> set) {
		super(key, LIST, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final Quaternionf vec = get.apply(value);
		if (vec == null)
			return true;
		writer.writeListTag(key, TagType.FLOAT, 4);
		writer.writeFloatTag(null, vec.w);
		writer.writeFloatTag(null, vec.x);
		writer.writeFloatTag(null, vec.y);
		writer.writeFloatTag(null, vec.z);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.FLOAT)
			throw new NBTException("Expected list of type FLOAT but was: " + listType);
		reader.readNextEntry();
		Quaternionf vec = get.apply(value);
		if (vec == null)
			vec = new Quaternionf();
		vec.w = reader.readFloatTag();
		vec.x = reader.readFloatTag();
		vec.y = reader.readFloatTag();
		vec.z = reader.readFloatTag();
		reader.readNextEntry();
		set.accept(value, vec);
	}

}
