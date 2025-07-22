package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.joml.Vector3i;

import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class Vector3iField<T> extends AbstractObjectField<T, Vector3i> {

	public Vector3iField(CharSequence key, Function<T, Vector3i> get, BiConsumer<T, Vector3i> set) {
		super(key, INT_ARRAY, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final Vector3i vec = get.apply(value);
		if (vec == null)
			return true;
		writer.writeIntArrayTag(key, new int[]{ vec.x, vec.y, vec.z });
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		Vector3i vec = get.apply(value);
		int[] data = reader.readIntArrayTag();
		vec.x = data[0];
		vec.y = data[1];
		vec.z = data[2];
		set.accept(value, vec);
	}

}
