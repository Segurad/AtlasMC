package de.atlasmc.core.chat;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.ColorValue;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatFactory;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.BaseComponent;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.TextComponent;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.io.SNBTReader;

public class CoreChatFactory implements ChatFactory {

	@Override
	public Chat asChat(CharSequence json, CharSequence legacy) {
		return new CoreChat(json != null ? json.toString() : null, legacy != null ? legacy.toString() : null);
	}

	@Override
	public String legacyToJson(CharSequence text, char formatPrefix) {
		return legacyToComponent(text, formatPrefix).toJsonText();
	}

	@Override
	public String jsonToLegacy(CharSequence json, char formatPrefix) {
		if (json.charAt(0) != '{')
			return json.toString();
		return componentToLegacy(jsonToComponent(json), formatPrefix);
	}

	@Override
	public ChatComponent jsonToComponent(CharSequence json) {
		if (json == null)
			throw new IllegalArgumentException("Json can not be null!");
		SNBTReader reader = new SNBTReader(json);
		ChatComponent comp;
		try {
			comp = ChatComponent.NBT_HANDLER.deserialize(reader);
		} catch (IOException e) {
			throw new NBTException("Error while parsing chat!", e);
		} finally {
			reader.close();
		}
		return comp;
	}

	@Override
	public String componentToLegacy(ChatComponent component, char formatPrefix) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		StringBuilder builder = new StringBuilder();
		buildLegacy(builder, component, formatPrefix);
		return builder.toString();
	}
	
	private void buildLegacy(StringBuilder builder, ChatComponent component, char formatPrefix) {
		if (!(component instanceof TextComponent) || component.getClass() != BaseComponent.class) {	
			ColorValue color = component.getColor();
			if (color != null && color instanceof ChatColor chat) {
				builder.append(formatPrefix);
				builder.append(chat.getFormatID());
			}
			if (color != ChatColor.RESET) {
				if (component.isBold()) {
					builder.append(formatPrefix);
					builder.append(ChatColor.BOLD.getFormatID());
				}
				if (component.isItalic()) {
					builder.append(formatPrefix);
					builder.append(ChatColor.ITALIC.getFormatID());
				}
				if (component.isObfuscated()) {
					builder.append(formatPrefix);
					builder.append(ChatColor.OBFUSCATED.getFormatID());
				}
				if (component.isStrikethrough()) {
					builder.append(formatPrefix);
					builder.append(ChatColor.STRIKETHROUGH.getFormatID());
				}
				if (component.isUnderlined()) {
					builder.append(formatPrefix);
					builder.append(ChatColor.UNDERLINE.getFormatID());
				}
			}
			if (component instanceof TextComponent text) {
				builder.append(text.getValue());
			}
		}
		if (component.hasExtra()) {
			for (ChatComponent child : component.getExtra()) {
				buildLegacy(builder, child, formatPrefix);
			}
		}
	}

	@Override
	public ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix) {
		TextComponent base = new TextComponent();
		TextComponent current = base;
		int textStart = 0;
		boolean readText = false;
		for (int i = 0; i < legacy.length(); i++) {
			char c = legacy.charAt(i);
			if (c == formatPrefix) {
				if (readText) {
					current.setValue(legacy.subSequence(textStart, i-1).toString());
					readText = false;
					current = new TextComponent();
					base.addExtra(current);
				}
				if (i+1 >= legacy.length()) // Return because end is reached
					return base;
				c = legacy.charAt(++i);
				ChatColor color = ChatColor.getByFormatID(c);
				if (color != null)
					color.modify(current);
				else if (c == '#') {
					if (i+6 >= legacy.length())
						return base;
					int rgb = Integer.parseInt(legacy, i+1, i+6, 16);
					i+=6;
					current.setColor(Color.fromRGB(rgb));
				}
				continue;
			} else if (!readText) {
				readText = true;
				textStart = i;
			}
		}
		if (readText) {
			current.setValue(legacy.subSequence(textStart, legacy.length()).toString());
		}
		return base;
	}

	@Override
	public String jsonToRawText(String json) {
		ChatComponent comp = jsonToComponent(json);
		return componentToRawText(comp);
	}

	@Override
	public String legacyToRawText(String legacy, char formatPrefix) {
		if (legacy.length() == 0)
			return legacy;
		StringBuilder builder = new StringBuilder(legacy.length());
		for (int i = 0; i < legacy.length(); i++) {
			char c = legacy.charAt(i);
			if (c == formatPrefix) {
				if (i+1 >= legacy.length()) // Return because end is reached
					continue;
				c = legacy.charAt(++i);
				if (c == 'x') {
					i+=6;
				}
				continue;
			}
			builder.append(c);
		}
		return builder.toString();
	}

	@Override
	public String componentToRawText(ChatComponent component) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		StringBuilder builder = new StringBuilder();
		buildRawText(builder, component);
		return builder.toString();
	}
	
	private void buildRawText(StringBuilder builder, ChatComponent component) {	
		if (component instanceof TextComponent text) {
			builder.append(text.getValue());
		}
		if (component.hasExtra()) {
			for (ChatComponent child : component.getExtra()) {
				buildRawText(builder, child);
			}
		}
	}

	@Override
	public ChatComponent toComponent(Chat chat) {
		if (chat == null)
			return ChatComponent.empty();
		if (chat instanceof ChatComponent comp)
			return comp;
		if (chat.hasJson())
			return jsonToComponent(chat.toJsonText());
		if (chat.hasLegacy())
			return legacyToComponent(chat.toLegacyText(), ChatUtil.DEFAULT_CHAT_FORMAT_PREFIX);
		return ChatComponent.empty();
	}

	@Override
	public String componentToConsole(ChatComponent component) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		StringBuilder builder = new StringBuilder();
		buildConsole(builder, component);
		builder.append(ChatColor.RESET.asConsoleColor());
		return builder.toString();
	}
	
	private void buildConsole(StringBuilder builder, ChatComponent component) {
		if (!(component instanceof TextComponent) || component.getClass() != BaseComponent.class) {	
			ColorValue color = component.getColor();
			if (color != ChatColor.RESET) {
				if (component.isBold()) {
					builder.append(ChatColor.BOLD.asConsoleColor());
				}
				if (component.isItalic()) {
					builder.append(ChatColor.ITALIC.asConsoleColor());
				}
				if (component.isObfuscated()) {
					builder.append(ChatColor.OBFUSCATED.asConsoleColor());
				}
				if (component.isStrikethrough()) {
					builder.append(ChatColor.STRIKETHROUGH.asConsoleColor());
				}
				if (component.isUnderlined()) {
					builder.append(ChatColor.UNDERLINE.asConsoleColor());
				}
			} else {
				builder.append(component.getColor().asConsoleColor());
			}
			if (component instanceof TextComponent text) {
				builder.append(text.getValue());
			}
		}
		if (component.hasExtra()) {
			for (ChatComponent child : component.getExtra()) {
				buildConsole(builder, child);
			}
		}
	}

	@Override
	public Chat fromNBT(NBTReader reader) throws IOException {
		TagType type = reader.getType();
		if (type == TagType.STRING) {
			String text = reader.readStringTag();
			return ChatUtil.toChat(text);
		} else if (type == TagType.LIST) {
			type = reader.getListType();
			if (type != TagType.STRING)
				throw new NBTException("List was not of STRING type: " + type.name());
			reader.readNextEntry();
			ChatComponent comp = new BaseComponent();
			while (reader.getRestPayload() > 0) {
				String raw = reader.readStringTag();
				comp.addExtra(jsonToComponent(raw));
			}
			return comp;
		} else if (type == TagType.COMPOUND) {
			reader.readNextEntry();
			return ChatComponent.NBT_HANDLER.deserialize(reader);
		} else {
			throw new NBTException("Unexpected element type: " + type.name());
		}
	}

	@Override
	public void toNBT(CharSequence key, Chat chat, NBTWriter writer) throws IOException {
		if (chat instanceof ChatComponent comp) {
			writer.writeCompoundTag(key);
			ChatComponent.NBT_HANDLER.serialize(comp, writer);
			writer.writeEndTag();
		} else {
			writer.writeStringTag(key, chat.toText());
		}
	}

}
