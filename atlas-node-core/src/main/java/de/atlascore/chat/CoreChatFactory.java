package de.atlascore.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatFactory;
import de.atlasmc.chat.ChatFormatException;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.BaseComponent;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.ClickEvent;
import de.atlasmc.chat.component.ClickEvent.ClickAction;
import de.atlasmc.chat.component.HoverEntityEvent;
import de.atlasmc.chat.component.HoverEvent;
import de.atlasmc.chat.component.HoverEvent.HoverAction;
import de.atlasmc.chat.component.HoverItemEvent;
import de.atlasmc.chat.component.HoverTextEvent;
import de.atlasmc.chat.component.KeybindComponent;
import de.atlasmc.chat.component.ScoreComponent;
import de.atlasmc.chat.component.TextComponent;
import de.atlasmc.chat.component.TranslationComponent;
import de.atlasmc.util.CharSequenceReader;

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
		JsonReader reader = new JsonReader(new CharSequenceReader(json));
		ChatComponent comp = null;
		try {
			comp = readComponent(reader);
			reader.close();
		} catch (IOException e) {
			throw new JsonParseException("Error while parsing json chat!", e);
		}
		return comp;
	}
	
	private ChatComponent readComponent(JsonReader reader) throws IOException {
		boolean bold = false;
		boolean italic = false;
		boolean underlined = false;
		boolean obfuscated = false;
		boolean strikethrough = false;
		ChatColor color = null;
		int rgb = -1;
		String font = null;
		HoverEvent hoverEvent = null;
		ClickEvent clickEvent = null;
		List<ChatComponent> extra = null;
		List<ChatComponent> with = null;
		String insertion = null;
		ChatComponent comp = null;
		
		reader.beginObject();
		while(reader.hasNext()) {
			switch(reader.nextName()) {
			case BaseComponent.JSON_BOLD:
				bold = reader.nextBoolean();
				break;
			case BaseComponent.JSON_ITALIC:
				italic = reader.nextBoolean();
				break;
			case BaseComponent.JSON_INSERTION:
				insertion = reader.nextString();
				break;
			case BaseComponent.JSON_UNDERLINED:
				underlined = reader.nextBoolean();
				break;
			case BaseComponent.JSON_OBFUSCATED:
				obfuscated = reader.nextBoolean();
				break;
			case BaseComponent.JSON_STRIKETHROUGH:
				strikethrough = reader.nextBoolean();
				break;
			case BaseComponent.JSON_COLOR:
				String rawColor = reader.nextString();
				if (rawColor.charAt(0) == '#') {
					rgb = Integer.parseInt(rawColor, 16);
				} else
					color = ChatColor.getByNameID(rawColor);
				break;
			case BaseComponent.JSON_HOVER_EVENT:
				hoverEvent = readHoverEvent(reader);
				break;
			case BaseComponent.JSON_CLICK_EVENT:
				clickEvent = readClickEvent(reader);
				break;
			case BaseComponent.JSON_EXTRA:
				extra = readComponentArray(reader);
				break;
			case TextComponent.JSON_TEXT:
				comp = new TextComponent(reader.nextString());
				break;
			case KeybindComponent.JSON_KEYBIND:
				comp = new KeybindComponent(reader.nextString());
				break;
			case ScoreComponent.JSON_SCORE:
				String name = null;
				String objective = null;
				String value = null;
				reader.beginObject();
				while (reader.hasNext()) { 
					switch (reader.nextName()) {
					case ScoreComponent.JSON_NAME:
						name = reader.nextString();
						break;
					case ScoreComponent.JSON_OBJECTIVE:
						objective = reader.nextString();
						break;
					case BaseComponent.JSON_VALUE:
						value = reader.nextString();
						break;
					default:
						reader.skipValue();
						break;
					}
				}
				reader.endObject();
				comp = new ScoreComponent(name, objective, value);
				break;
			case TranslationComponent.JSON_TRANSLATE:
				comp = new TranslationComponent(reader.nextString(), null);
				break;
			case TranslationComponent.JSON_WITH:
				with = readComponentArray(reader);
				break;
			default:
				reader.skipValue();
				break;
			}
		}
		reader.endObject();
		if (comp == null)
			comp = new BaseComponent();
		comp.setBold(bold)
			.setItalic(italic)
			.setUnderlined(underlined)
			.setObfuscated(obfuscated)
			.setStrikethrough(strikethrough)
			.setFont(font)
			.setHoverEvent(hoverEvent)
			.setClickEvent(clickEvent)
			.setExtra(extra)
			.setInsertion(insertion);
		if (color != null)
			comp.color(color);
		else if (rgb != -1)
			comp.setColor(rgb);
		if (comp instanceof TranslationComponent trcomp) {
			trcomp.setWith(with);
		}
		return comp;
	}
	
	private List<ChatComponent> readComponentArray(JsonReader reader) throws IOException {
		List<ChatComponent> comps = null;
		reader.beginArray();
		while (reader.hasNext()) {
			ChatComponent comp = readComponent(reader);
			if (comp == null)
				continue;
			if (comps == null)
				comps = new ArrayList<>();
			comps.add(comp);
		}
		reader.endArray();
		return comps;
	}
	
	private HoverEvent readHoverEvent(JsonReader reader) throws IOException {
		reader.beginObject();
		String next = reader.nextName();
		if (!BaseComponent.JSON_ACTION.equals(next))
			throw new ChatFormatException("Expected " + BaseComponent.JSON_ACTION + " but was: " + next);
		HoverAction hoverAction = HoverAction.getByNameID(reader.nextString());
		next = reader.nextName();
		if (!BaseComponent.JSON_CONTENTS.equals(next))
			throw new ChatFormatException("Expected " + BaseComponent.JSON_ACTION + " but was: " + next);
		reader.beginObject();
		HoverEvent event = null;
		switch (hoverAction) {
		case SHOW_ENTITY:
			String type = null;
			String uuid = null;
			ChatComponent name = null;
			while (reader.peek() != JsonToken.END_OBJECT) {
				String key = reader.nextName();
				switch (key) {
				case HoverEntityEvent.JSON_ID:
					uuid = reader.nextString();
					break;
				case HoverEntityEvent.JSON_NAME:
					reader.beginObject();
					name = readComponent(reader);
					reader.endObject();
					break;
				case HoverEntityEvent.JSON_TYPE:
					type = reader.nextString();
					break;
				default:
					reader.skipValue();
				}
			}
			event = new HoverEntityEvent(type, uuid, name);
			break;
		case SHOW_ITEM:
			String id = null;
			int count = 0;
			String tag = null;
			while (reader.peek() != JsonToken.END_OBJECT) {
				String key = reader.nextName();
				switch (key) {
				case HoverItemEvent.JSON_ID:
					id = reader.nextString();
					break;
				case HoverItemEvent.JSON_COUNT:
					count = reader.nextInt();
					break;
				case HoverItemEvent.JSON_TAG:
					tag = reader.nextString();
					break;
				default:
					reader.skipValue();
				}
			}
			event = new HoverItemEvent(id, count, tag);
			break;
		case SHOW_TEXT:
			ChatComponent chat = readComponent(reader);
			event = new HoverTextEvent(chat);
			break;
		}
		reader.endObject();
		reader.endObject();
		return event;
	}
	
	private ClickEvent readClickEvent(JsonReader reader) throws IOException {
		ClickAction clickAction = null;
		String value = null;
		reader.beginObject();
		while (reader.hasNext()) {
			switch (reader.nextName()) {
			case BaseComponent.JSON_ACTION:
				clickAction = ClickAction.getByNameID(reader.nextString());
				break;
			case BaseComponent.JSON_VALUE:
				value = reader.nextName();
				break;
			default:
				reader.skipValue();
				break;
			}
		}
		reader.endObject();
		if (clickAction != null)
			return new ClickEvent(clickAction, value);
		return null;
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
			ChatColor color = component.getColorChat();
			if (color != null) {
				builder.append(formatPrefix);
				builder.append(color.getFormatID());
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
				else if (c == 'x') {
					if (i+6 >= legacy.length())
						return base;
					int rgb = Integer.parseInt(legacy, i+1, i+6, 16);
					i+=6;
					current.setColor(rgb);
				}
				continue;
			} else if (!readText) {
				readText = true;
				textStart = i;
			}
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
		if (!(component instanceof TextComponent) || component.getClass() != BaseComponent.class) {	
			if (component instanceof TextComponent text) {
				builder.append(text.getValue());
			}
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
		return builder.toString();
	}
	
	private void buildConsole(StringBuilder builder, ChatComponent component) {
		if (!(component instanceof TextComponent) || component.getClass() != BaseComponent.class) {	
			ChatColor color = component.getColorChat();
			if (color != null) {
				builder.append(color.getConsoleFormat());
			}
			if (color != ChatColor.RESET) {
				if (component.isBold()) {
					builder.append(ChatColor.BOLD.getConsoleFormat());
				}
				if (component.isItalic()) {
					builder.append(ChatColor.ITALIC.getConsoleFormat());
				}
				if (component.isObfuscated()) {
					builder.append(ChatColor.OBFUSCATED.getConsoleFormat());
				}
				if (component.isStrikethrough()) {
					builder.append(ChatColor.STRIKETHROUGH.getConsoleFormat());
				}
				if (component.isUnderlined()) {
					builder.append(ChatColor.UNDERLINE.getConsoleFormat());
				}
			}
			if (component.hasColor()) {
				builder.append(Color.asConsoleColor(component.getColor()));
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

}
