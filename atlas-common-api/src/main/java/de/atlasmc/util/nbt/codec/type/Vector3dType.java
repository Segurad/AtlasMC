package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import org.joml.Vector3d;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class Vector3dType extends ObjectType<Vector3d> {

	private static final Vector3dType INSTANCE = new Vector3dType();
	
	public static Vector3dType getInstance() {
		return INSTANCE;
	}
	
	private Vector3dType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, Vector3d value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeListTag(key, TagType.DOUBLE, 3);
		writer.writeDoubleTag(null, value.x);
		writer.writeDoubleTag(null, value.y);
		writer.writeDoubleTag(null, value.z);
		return true;
	}

	@Override
	public Vector3d deserialize(Vector3d value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType != TagType.DOUBLE)
			throw new NBTException("Expected list of type DOUBLE but was: " + listType);
		Vector3d vec = value != null ? value : new Vector3d();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return vec;
		}
		reader.readNextEntry();
		vec.x = reader.readDoubleTag();
		vec.y = reader.readDoubleTag();
		vec.z = reader.readDoubleTag();
		reader.readNextEntry();
		return vec;
	}

	@Override
	public List<TagType> getTypes() {
		return LIST;
	}

}
