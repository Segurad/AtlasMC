package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.joml.Vector3d;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class Vector3dField<T> extends AbstractObjectField<T, Vector3d> {

	public Vector3dField(CharSequence key, Function<T, Vector3d> get, BiConsumer<T, Vector3d> set) {
		super(key, LIST, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final Vector3d vec = get.apply(value);
		if (vec == null)
			return true;
		writer.writeListTag(key, TagType.DOUBLE, 3);
		writer.writeDoubleTag(null, vec.x);
		writer.writeDoubleTag(null, vec.y);
		writer.writeDoubleTag(null, vec.z);
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
		if (listType != TagType.DOUBLE)
			throw new NBTException("Expected list of type DOUBLE but was: " + listType);
		reader.readNextEntry();
		Vector3d vec = get.apply(value);
		vec.x = reader.readDoubleTag();
		vec.y = reader.readDoubleTag();
		vec.z = reader.readDoubleTag();
		reader.readNextEntry();
		set.accept(value, vec);
	}

}
