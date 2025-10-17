package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.ints.IntList;

@Singleton
public class IntListType extends ObjectType<IntList> {

	private static final List<TagType> TYPES = List.of(TagType.INT_ARRAY, TagType.LIST);

	private static final IntListType INSTANCE = new IntListType();
	
	public static IntListType getInstance() {
		return INSTANCE;
	}
	
	private IntListType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, IntList value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.size();
		writer.writeListTag(key, TagType.INT, size);
		for (int i = 0; i < size; i++) {
			writer.writeIntTag(null, value.getInt(i));
		}
		return true;
	}

	@Override
	public IntList deserialize(IntList value, NBTReader reader, CodecContext context) throws IOException {
		switch(reader.getType()) {
		case LIST: {
			TagType listType = reader.getListType();
			if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
				reader.readNextEntry();
				reader.readNextEntry();
				return value;
			}
			if (listType != TagType.INT)
				throw new NBTException("Expected list of type INT but was: " + listType);
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				value.add(reader.readIntTag());
			}
			reader.readNextEntry();
			return value;
		}
		case INT_ARRAY:
			int[] array = reader.readIntArrayTag();
			value.addElements(value.size(), array);
			return value;
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
	}

	@Override
	public List<TagType> getTypes() {
		return TYPES;
	}

}
