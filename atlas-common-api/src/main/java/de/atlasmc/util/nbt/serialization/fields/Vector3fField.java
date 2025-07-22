package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.joml.Vector3f;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class Vector3fField<T> extends AbstractObjectField<T, Vector3f> {

	public Vector3fField(CharSequence key, Function<T, Vector3f> get, BiConsumer<T, Vector3f> set) {
		super(key, LIST, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final Vector3f vec = get.apply(value);
		if (vec == null)
			return true;
		writer.writeListTag(key, TagType.FLOAT, 3);
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
		Vector3f vec = get.apply(value);
		vec.x = reader.readFloatTag();
		vec.y = reader.readFloatTag();
		vec.z = reader.readFloatTag();
		reader.readNextEntry();
		set.accept(value, vec);
	}

}
