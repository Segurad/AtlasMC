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

@Singleton
public class ColorType extends ObjectType<Color> {

	private static final ColorType INSTANCE = new ColorType();
	
	public static ColorType getInstance() {
		return INSTANCE;
	}
	
	private ColorType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, Color value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeIntTag(key, value.asARGB());
		return true;
	}

	@Override
	public Color deserialize(Color color, NBTReader reader, CodecContext context) throws IOException {
		switch (reader.getType()) {
		case TagType.INT: {
			return Color.fromARGB(reader.readIntTag());
		}
		case TagType.LIST: {
			if (reader.getListType() != TagType.FLOAT)
				throw new NBTException("Expected float tag but was: " + reader.getListType());
			reader.readNextEntry();
			float r = reader.readFloatTag();
			float g = reader.readFloatTag();
			float b = reader.readFloatTag();
			float a = reader.getRestPayload() > 0 ? reader.readFloatTag() : 0;
			Color c = Color.fromRGB(Color.asARGB(r, g, b, a));
			reader.readNextEntry();
			return c;
		}
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
	}

	@Override
	public List<TagType> getTypes() {
		return INT_LIST;
	}

}
