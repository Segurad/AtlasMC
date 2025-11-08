package de.atlasmc;

import java.io.IOException;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;

public interface ColorValue {
	
	public static final NBTCodec<ColorValue> NBT_CODEC = new NBTCodec<ColorValue>() {
		
		@Override
		public Class<?> getType() {
			return ColorValue.class;
		}
		
		@Override
		public ColorValue deserialize(ColorValue value, NBTReader input, CodecContext context) throws IOException {
			String raw = input.readStringTag();
			if (raw.charAt(0) == '#') {
				return Color.fromARGB(Integer.parseInt(raw, 1, raw.length(), 16));
			} else {
				return EnumUtil.getByName(ChatColor.class, raw);
			}
		}
		
		@Override
		public boolean serialize(CharSequence key, ColorValue value, NBTWriter output, CodecContext context) throws IOException {
			if (value instanceof ChatColor chat) {
				output.writeStringTag(key, chat.getName());
			} else if (value instanceof Color color) {
				output.writeStringTag(key, "#" + Integer.toHexString(color.asRGB()));
			} else {
				return false;
			}
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.STRING;
		}
	};
	
	/**
	 * Returns the color value as {@link Color}
	 * @return color
	 */
	@NotNull
	Color asColor();
	
	/**
	 * Returns the color value as {@link ChatColor} or null if no match
	 * @return color
	 */
	@Nullable
	ChatColor asChatColor();
	
	/**
	 * Returns the console format string
	 * @return value
	 */
	@NotNull
	String asConsoleColor();
	
	/**
	 * Returns this color value in RGB format
	 * @return value
	 */
	int asRGB();
	
	/**
	 * Returns this color value in ARGB format
	 * @return value
	 */
	int asARGB();

}
