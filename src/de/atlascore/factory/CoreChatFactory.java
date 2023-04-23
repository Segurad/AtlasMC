package de.atlascore.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import de.atlascore.CoreChat;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.BaseComponent;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.ClickEvent;
import de.atlasmc.chat.component.ClickEvent.ClickAction;
import de.atlasmc.chat.component.HoverEvent;
import de.atlasmc.chat.component.KeybindComponent;
import de.atlasmc.chat.component.RawHoverEvent;
import de.atlasmc.chat.component.ScoreComponent;
import de.atlasmc.chat.component.TextComponent;
import de.atlasmc.chat.component.TranslationComponent;
import de.atlasmc.chat.component.HoverEvent.HoverAction;
import de.atlasmc.factory.ChatFactory;
import de.atlasmc.util.CharSequenceReader;

public class CoreChatFactory implements ChatFactory {

	@Override
	public Chat asChat(CharSequence json, CharSequence legacy) {
		return new CoreChat(json.toString(), legacy.toString());
	}

	@Override
	public String jsonFromLegacy(CharSequence text, char formatPrefix) {
		return legacyToComponent(text, formatPrefix).getJsonText();
	}

	@Override
	public String legacyFromJson(CharSequence json, char formatPrefix) {
		if (json.charAt(0) != '{')
			return json.toString();
		return legacyFromComponent(jsonToComponent(json), formatPrefix);
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
				new TranslationComponent(reader.nextString(), null);
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
		
		if (comp != null) {
			comp.setBold(bold);
			comp.setItalic(italic);
			comp.setUnderlined(underlined);
			comp.setObfuscated(obfuscated);
			comp.setStrikethrough(strikethrough);
			if (color != null)
				comp.setColor(color);
			else if (rgb != -1)
				comp.setColor(rgb);
			comp.setFont(font);
			comp.setHoverEvent(hoverEvent);
			comp.setClickEvent(clickEvent);
			comp.setExtra(extra);
			if (comp instanceof TranslationComponent) {
				TranslationComponent trsl = (TranslationComponent) comp;
				trsl.setWith(with);
			}
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
		HoverAction hoverAction = null;
		String contents = null;
		reader.beginObject();
		while (reader.hasNext()) {
			switch (reader.nextName()) {
			case BaseComponent.JSON_ACTION:
				hoverAction = HoverAction.getByNameID(reader.nextString());
				break;
			case BaseComponent.JSON_CONTENTS:
				contents = reader.nextString();
				break;
			default:
				reader.skipValue();
				break;
			}
		}
		reader.endObject();
		if (hoverAction != null)
			return new RawHoverEvent(hoverAction, contents);
		return null;
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
			return new ClickEvent(value, clickAction);
		return null;
	}

	@Override
	public String legacyFromComponent(ChatComponent component, char formatPrefix) {
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
			if (component instanceof TextComponent) {
				TextComponent text = (TextComponent) component;
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

}
