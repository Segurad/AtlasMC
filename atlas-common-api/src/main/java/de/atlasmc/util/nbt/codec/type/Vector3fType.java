package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import org.joml.Vector3f;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class Vector3fType extends ObjectType<Vector3f> {

	private static final Vector3fType INSTANCE = new Vector3fType();
	
	public static Vector3fType getInstance() {
		return INSTANCE;
	}
	
	private Vector3fType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, Vector3f value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeListTag(key, TagType.FLOAT, 3);
		writer.writeFloatTag(null, value.x);
		writer.writeFloatTag(null, value.y);
		writer.writeFloatTag(null, value.z);
		return true;
	}

	@Override
	public Vector3f deserialize(Vector3f value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType != TagType.FLOAT)
			throw new NBTException("Expected list of type FLOAT but was: " + listType);
		Vector3f vec = value != null ? value : new Vector3f();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return vec;
		}
		reader.readNextEntry();
		vec.x = reader.readFloatTag();
		vec.y = reader.readFloatTag();
		vec.z = reader.readFloatTag();
		reader.readNextEntry();
		return vec;
	}

	@Override
	public List<TagType> getTypes() {
		return LIST;
	}

}
