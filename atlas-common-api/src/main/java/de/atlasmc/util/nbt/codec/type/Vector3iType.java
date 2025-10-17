package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import org.joml.Vector3i;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class Vector3iType extends ObjectType<Vector3i> {

	private static final Vector3iType INSTANCE = new Vector3iType();
	
	public static Vector3iType getInstance() {
		return INSTANCE;
	}
	
	private Vector3iType() {
		// singleton
	}

	@Override
	public boolean serialize(CharSequence key, Vector3i value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeIntArrayTag(key, new int[]{ value.x, value.y, value.z });
		return true;
	}

	@Override
	public Vector3i deserialize(Vector3i value, NBTReader reader, CodecContext context) throws IOException {
		final Vector3i vec = value != null ? value : new Vector3i();
		int[] data = reader.readIntArrayTag();
		vec.x = data[0];
		vec.y = data[1];
		vec.z = data[2];
		return vec;
	}

	@Override
	public List<TagType> getTypes() {
		return INT_ARRAY;
	}

}
