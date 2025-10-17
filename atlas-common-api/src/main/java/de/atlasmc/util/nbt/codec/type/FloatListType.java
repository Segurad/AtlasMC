package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.floats.FloatList;

@Singleton
public class FloatListType extends ObjectType<FloatList> {

	private static final FloatListType INSTANCE = new FloatListType();
	
	public static FloatListType getInstance() {
		return INSTANCE;
	}
	
	private FloatListType() {
		// singleton
	}

	@Override
	public boolean serialize(CharSequence key, FloatList value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.size();
		writer.writeListTag(key, TagType.FLOAT, size);
		for (int i = 0; i < size; i++) {
			writer.writeFloatTag(null, value.getFloat(i));
		}
		return true;
	}

	@Override
	public FloatList deserialize(final FloatList value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return value;
		}
		if (listType != TagType.FLOAT)
			throw new NBTException("Expected list of type FLOAT but was: " + listType);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			value.add(reader.readFloatTag());
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return LIST;
	}

}
