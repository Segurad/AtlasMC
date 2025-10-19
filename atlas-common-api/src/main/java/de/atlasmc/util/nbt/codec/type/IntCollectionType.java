package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;

@Singleton
public class IntCollectionType extends ObjectType<IntCollection> {

	private static final List<TagType> TYPES = List.of(TagType.INT_ARRAY, TagType.LIST);

	private static final IntCollectionType INSTANCE = new IntCollectionType();
	
	public static IntCollectionType getInstance() {
		return INSTANCE;
	}
	
	private IntCollectionType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, IntCollection value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeIntArrayTag(key, value.toIntArray());
		return true;
	}

	@Override
	public IntCollection deserialize(IntCollection value, NBTReader reader, CodecContext context) throws IOException {
		switch(reader.getType()) {
		case INT_ARRAY:
			int[] array = reader.readIntArrayTag();
			if (value instanceof IntList list) {
				list.addElements(list.size(), array);
			} else {
				for (int i : array)
					value.add(i);
			}
			return value;
		case LIST: {
			final TagType listType = reader.getListType();
			if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
				reader.readNextEntry();
				reader.readNextEntry();
				return value;
			}
			switch(listType) {
			case INT:
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					value.add(reader.readIntTag());
				}
				reader.readNextEntry();
				break;
			case LIST:
				reader.readNextEntry(); // enter outer list
				if (reader.getListType() != TagType.FLOAT)
					throw new NBTException("Invalid list type: " + reader.getListType());
				reader.readNextEntry(); // enter float list
				float r = reader.readFloatTag();
				float g = reader.readFloatTag();
				float b = reader.readFloatTag();
				value.add(Color.asRGB(r, g, b));
				reader.readNextEntry(); // leaf inner list
				reader.readNextEntry(); // leaf outer list
				break;
			default:
				throw new NBTException("Unxecpected list type: " + listType);
			}
			return value;
		}
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
	}

	@Override
	public List<TagType> getTypes() {
		return TYPES;
	}

}
