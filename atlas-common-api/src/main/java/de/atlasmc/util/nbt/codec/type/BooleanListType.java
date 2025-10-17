package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.booleans.BooleanList;

@Singleton
public class BooleanListType extends ObjectType<BooleanList> {

	private static BooleanListType INSTANCE = new BooleanListType();
	
	public static BooleanListType getInstance() {
		return INSTANCE;
	}
	
	private BooleanListType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, BooleanList value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.size();
		writer.writeListTag(key, TagType.BYTE, size);
		for (int i = 0; i < size; i++) {
			writer.writeByteTag(null, value.getBoolean(i));
		}
		return true;
	}

	@Override
	public BooleanList deserialize(BooleanList value, NBTReader reader, CodecContext context) throws IOException {
		TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return value;
		}
		if (listType != TagType.BYTE)
			throw new NBTException("Expected list of type BYTE but was: " + listType);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			value.add(reader.readBoolean());
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return LIST;
	}

}
