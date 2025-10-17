package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import org.joml.Quaternionf;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class QuaternionfType extends ObjectType<Quaternionf> {

	private static final QuaternionfType INSTANCE = new QuaternionfType();
	
	public static QuaternionfType getInstance() {
		return INSTANCE;
	}
	
	private QuaternionfType() {
		// singleton
	}

	@Override
	public boolean serialize(CharSequence key, Quaternionf value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeListTag(key, TagType.FLOAT, 4);
		writer.writeFloatTag(null, value.w);
		writer.writeFloatTag(null, value.x);
		writer.writeFloatTag(null, value.y);
		writer.writeFloatTag(null, value.z);
		return true;
	}

	@Override
	public Quaternionf deserialize(Quaternionf value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType != TagType.FLOAT)
			throw new NBTException("Expected list of type FLOAT but was: " + listType);
		final Quaternionf vec = value != null ? value : new Quaternionf();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return vec;
		}
		reader.readNextEntry();
		vec.w = reader.readFloatTag();
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
