package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.Color;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class ChatColorColorField<T> extends AbstractObjectField<T, ChatColor> {

	protected final Function<T, Color> getColor;
	protected final BiConsumer<T, Color> setColor;
	
	public ChatColorColorField(CharSequence key, Function<T, ChatColor> getChat, BiConsumer<T, ChatColor> setChat, Function<T, Color> getColor, BiConsumer<T, Color> setColor) {
		super(key, TagType.STRING, getChat, setChat, true);
		this.getColor = getColor;
		this.setColor = setColor;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		ChatColor chat = get.apply(value);
		if (chat != null) {
			writer.writeStringTag(key, chat.getName());
			return true;
		}
		Color color = getColor.apply(value);
		if (color != null)
			writer.writeStringTag(key, "#" + Integer.toHexString(color.asRGB()));
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		String raw = reader.readStringTag();
		if (raw.charAt(0) == '#') {
			setColor.accept(value, Color.fromARGB(Integer.parseInt(raw, 1, raw.length(), 16)));
		} else {
			ChatColor color = ChatColor.getByName(raw);
			set.accept(value, color);
		}
	}

}
