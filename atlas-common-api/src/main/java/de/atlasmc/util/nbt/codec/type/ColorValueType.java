package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.Color;
import de.atlasmc.ColorValue;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class ColorValueType extends ObjectType<ColorValue> {

	private static final ColorValueType INSTANCE = new ColorValueType();
	
	public static ColorValueType getInstance() {
		return INSTANCE;
	}
	
	private ColorValueType() {
		// singleton
	}

	@Override
	public boolean serialize(CharSequence key, ColorValue value, NBTWriter writer, CodecContext context) throws IOException {
		if (value instanceof ChatColor chat) {
			writer.writeStringTag(key, chat.getName());
		} else if (value instanceof Color color) {
			writer.writeStringTag(key, "#" + Integer.toHexString(color.asRGB()));
		}
		return true;
	}

	@Override
	public ColorValue deserialize(ColorValue value, NBTReader reader, CodecContext context) throws IOException {
		String raw = reader.readStringTag();
		if (raw.charAt(0) == '#') {
			return Color.fromARGB(Integer.parseInt(raw, 1, raw.length(), 16));
		} else {
			return EnumUtil.getByName(ChatColor.class, raw);
		}
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
