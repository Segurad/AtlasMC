package de.atlascore.chat;

import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_BOLD;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_CLICK_EVENT;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_COLOR;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_EXTRA;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_FONT;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_HOVER_EVENT;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_INSERTION;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_ITALIC;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_OBFUSCATED;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_STRIKETHROUGH;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_TYPE;
import static de.atlasmc.chat.component.AbstractBaseComponent.JSON_UNDERLINED;
import static de.atlasmc.chat.component.ScoreComponent.JSON_SCORE;
import static de.atlasmc.chat.component.SelectorComponent.JSON_SELECTOR;
import static de.atlasmc.chat.component.SelectorComponent.JSON_SEPARATOR;
import static de.atlasmc.chat.component.TextComponent.JSON_TEXT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatFormatException;
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
import de.atlasmc.chat.component.NBTComponent;
import de.atlasmc.chat.component.ScoreComponent;
import de.atlasmc.chat.component.SelectorComponent;
import de.atlasmc.chat.component.TextComponent;
import de.atlasmc.chat.component.TranslationComponent;
import de.atlasmc.util.Builder;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.tag.NBT;

class CoreChatComponentBuilder implements Builder<ChatComponent> {
	
	public static final NBTFieldSet<CoreChatComponentBuilder> NBT_FIELDS;
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(JSON_TYPE, (holder, reader) -> {
			holder.type = reader.readStringTag();
		});
		NBT_FIELDS.setField(JSON_EXTRA, (holder, reader) -> {
			ArrayList<ChatComponent> comps = null;
			CoreChatComponentBuilder builder = new CoreChatComponentBuilder();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				NBTUtil.readNBT(NBT_FIELDS, builder, reader);
				ChatComponent comp = builder.build();
				builder.clear();
				if (comp == null)
					continue;
				if (comps == null)
					comps = new ArrayList<>();
				comps.add(comp);
			}
			if (comps != null)
				comps.trimToSize();
			holder.extra = comps;
		});
		NBT_FIELDS.setField(JSON_COLOR, (holder, reader) -> {
			String rawColor = reader.readStringTag();
			if (rawColor.charAt(0) == '#') {
				holder.rgb = Integer.parseInt(rawColor.substring(1), 16);
			} else {
				ChatColor color = ChatColor.getByName(rawColor);
				if (color != null)
					holder.rgb = color.getColor().asRGB();
			}
		});
		NBT_FIELDS.setField(JSON_FONT, (holder, reader) -> {
			holder.font = reader.readStringTag();
		});
		NBT_FIELDS.setField(JSON_BOLD, (holder, reader) -> {
			holder.bold = reader.readBoolean();
		});
		NBT_FIELDS.setField(JSON_ITALIC, (holder, reader) -> {
			holder.italic = reader.readBoolean();
		});
		NBT_FIELDS.setField(JSON_UNDERLINED, (holder, reader) -> {
			holder.underlined = reader.readBoolean();
		});
		NBT_FIELDS.setField(JSON_STRIKETHROUGH, (holder, reader) -> {
			holder.strikethrough = reader.readBoolean();
		});
		NBT_FIELDS.setField(JSON_OBFUSCATED, (holder, reader) -> {
			holder.obfuscated = reader.readBoolean();
		});
		NBT_FIELDS.setField(JSON_INSERTION, (holder, reader) -> {
			holder.insertion = reader.readStringTag();
		});
		NBT_FIELDS.setField(JSON_CLICK_EVENT, (holder, reader) -> {
			holder.clickEvent = readClickEvent(reader);
		});
		NBT_FIELDS.setField(JSON_HOVER_EVENT, (holder, reader) -> {
			holder.hoverEvent = readHoverEvent(reader);
		});
		NBT_FIELDS.setField(JSON_TEXT, (holder, reader) -> {
			holder.text = reader.readStringTag();
		});
		NBT_FIELDS.setField(JSON_SCORE, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) { 
				CharSequence fild = reader.getFieldName();
				if (ScoreComponent.JSON_NAME.equals(fild)) {
					holder.scoreName = reader.readStringTag();
				} else if (ScoreComponent.JSON_OBJECTIVE.equals(fild)) {
					holder.scoreObjective = reader.readStringTag();
				} else {
					reader.skipTag();
				}
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(JSON_SELECTOR, (holder, reader) -> {
			holder.selector = reader.readStringTag();
		});
		NBT_FIELDS.setField(JSON_SEPARATOR, (holder, reader) -> {
			CoreChatComponentBuilder builder = new CoreChatComponentBuilder();
			NBTUtil.readNBT(NBT_FIELDS, builder, reader);
			holder.separator = builder.build();
			builder.clear();
		});
		NBT_FIELDS.setField(KeybindComponent.JSON_KEYBIND, (holder, reader) -> {
			holder.keybind = reader.readStringTag();
		});
		NBT_FIELDS.setField(NBTComponent.JSON_SOURCE, (holder, reader) -> {
			holder.source = reader.readStringTag();
		});
		NBT_FIELDS.setField(NBTComponent.JSON_NBT, (holder, reader) -> {
			holder.nbtPath = reader.readStringTag();
		});
		NBT_FIELDS.setField(NBTComponent.JSON_INTERPRET, (holder, reader) -> {
			holder.interpret = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBTComponent.JSON_BLOCK, (holder, reader) -> {
			holder.block = reader.readStringTag();
		});
		NBT_FIELDS.setField(NBTComponent.JSON_ENTITY, (holder, reader) -> {
			holder.entity = reader.readStringTag();
		});
		NBT_FIELDS.setField(NBTComponent.JSON_STORAGE, (holder, reader) -> {
			holder.storage = reader.readStringTag();
		});
	}
	
	public String type = null;
	public List<ChatComponent> extra = null;
	public int rgb = -1;
	public String font = null;
	public boolean bold = false;
	public boolean italic = false;
	public boolean underlined = false;
	public boolean obfuscated = false;
	public boolean strikethrough = false;
	public String insertion = null;
	public ClickEvent clickEvent = null;
	public HoverEvent hoverEvent = null;
	// TextComponent
	public String text = null;
	// === KeybindComponent
	public String keybind = null;
	// === ScoreComponent
	public String scoreName = null;
	public String scoreObjective = null;
	// === TranslationComponent
	public String translate = null;
	public String fallback = null;
	public List<ChatComponent> with = null;
	// === SelectorComponent
	public String selector = null;
	public ChatComponent separator = null;
	// === NBTComponent
	public String source = null;
	public String nbtPath = null;
	public boolean interpret = false;
	// private ChatComponent separator;
	public String block = null;
	public String entity = null;
	public String storage = null;
	
	public CoreChatComponentBuilder() {}
	
	@Override
	public ChatComponent build() {
		ChatComponent comp = null;
		if (type != null) { // init by type
			switch (type) {
			case "text":
				comp = new TextComponent(text);
				break;
			case "translation":
				comp = new TranslationComponent(translate, with).setFallback(fallback);
				break;
			case "score":
				comp = new ScoreComponent(scoreName, scoreObjective);
				break;
			case "selector":
				comp = new SelectorComponent(selector, separator);
				break;
			case "keybind":
				comp = new KeybindComponent(keybind);
				break;
			case "nbt":
				NBTComponent nbt = new NBTComponent();
				comp = nbt;
				nbt.setSource(source)
					.setNbtPath(nbtPath)
					.setInterpret(interpret)
					.setSeparator(separator)
					.setBlock(block)
					.setEntity(entity)
					.setStorage(storage);
				break;
			default:
				break;
			}
		}
		if (comp == null) { // init detect type
			if (text != null) {
				comp = new TextComponent(text);
			} else if (translate != null) {
				comp = new TranslationComponent(translate, with).setFallback(fallback);
			} else if (scoreName != null) {
				comp = new ScoreComponent(scoreName, scoreObjective);
			} else if (selector != null) {
				comp = new SelectorComponent(selector, separator);
			} else if (keybind != null) {
				comp = new KeybindComponent(keybind);
			} else if (nbtPath != null) {
				NBTComponent nbt = new NBTComponent();
				comp = nbt;
				nbt.setSource(source)
					.setNbtPath(nbtPath)
					.setInterpret(interpret)
					.setSeparator(separator)
					.setBlock(block)
					.setEntity(entity)
					.setStorage(storage);
			}
		}
		if (comp == null) {
			comp = new BaseComponent();
		}
		comp.setBold(bold)
			.setItalic(italic)
			.setUnderlined(underlined)
			.setObfuscated(obfuscated)
			.setStrikethrough(strikethrough)
			.setFont(font)
			.setHoverEvent(hoverEvent)
			.setClickEvent(clickEvent)
			.setExtra(extra)
			.setInsertion(insertion)
			.setColor(rgb);
		return comp;
	}
	@Override
	public void clear() {
		type = null;
		extra = null;
		rgb = -1;
		font = null;
		bold = false;
		italic = false;
		underlined = false;
		obfuscated = false;
		strikethrough = false;
		hoverEvent = null;
		clickEvent = null;
		insertion = null;
		// TextComponent
		text = null;
		// === KeybindComponent
		keybind = null;
		// === ScoreComponent
		scoreName = null;
		scoreObjective = null;
		// === TranslationComponent
		translate = null;
		fallback = null;
		with = null;
		// === SelectorComponent
		selector = null;
		separator = null;
		// === NBTComponent
		source = null;
		nbtPath = null;
		interpret = false;
		// private ChatComponent separator;
		block = null;
		entity = null;
		storage = null;
	}
	
	private static HoverEvent readHoverEvent(NBTReader reader) throws IOException {
		reader.readNextEntry();
		CharSequence next = reader.getFieldName();
		if (!BaseComponent.JSON_ACTION.equals(next))
			throw new ChatFormatException("Expected " + BaseComponent.JSON_ACTION + " but was: " + next);
		HoverAction hoverAction = HoverAction.getByNameID(reader.readStringTag());
		next = reader.getFieldName();
		if (!BaseComponent.JSON_CONTENTS.equals(next))
			throw new ChatFormatException("Expected " + BaseComponent.JSON_ACTION + " but was: " + next);
		reader.readNextEntry();
		HoverEvent event = null;
		switch (hoverAction) {
		case SHOW_ENTITY:
			String type = null;
			UUID uuid = null;
			ChatComponent name = null;
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (HoverEntityEvent.JSON_ID.equals(key)) {
					if (reader.getType() == TagType.STRING)
						uuid = UUID.fromString(reader.readStringTag());
					else
						uuid = reader.readUUID();
				} else if (HoverEntityEvent.JSON_NAME.equals(key)) {
					reader.readNextEntry();
					CoreChatComponentBuilder builder = new CoreChatComponentBuilder();
					NBTUtil.readNBT(NBT_FIELDS, builder, reader);
					name = builder.build();
				} else if (HoverEntityEvent.JSON_TYPE.equals(key)) {
					type = reader.readStringTag();
				} else {
					reader.skipTag();
				}
			}
			event = new HoverEntityEvent(type, uuid, name);
			break;
		case SHOW_ITEM:
			String id = null;
			int count = 0;
			NBT components = null;
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (HoverItemEvent.JSON_ID.equals(key)) {
					id = reader.readStringTag();
				} else if (HoverItemEvent.JSON_COUNT.equals(key)) {
					count = reader.readIntTag();
				} else if (HoverItemEvent.JSON_COMPONENT.equals(key)) {
					components = reader.readNBT();
				} else {
					reader.skipTag();
				}
			}
			event = new HoverItemEvent(id, count, components);
			break;
		case SHOW_TEXT:
			ChatComponent chat = null;
			reader.readNextEntry();
			CoreChatComponentBuilder builder = new CoreChatComponentBuilder();
			NBTUtil.readNBT(NBT_FIELDS, builder, reader);
			chat = builder.build();
			event = new HoverTextEvent(chat);
			break;
		}
		reader.skipToEnd();
		reader.skipToEnd();
		return event;
	}
	
	private static ClickEvent readClickEvent(NBTReader reader) throws IOException {
		ClickAction clickAction = null;
		String value = null;
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			CharSequence field = reader.getFieldName();
			if (BaseComponent.JSON_ACTION.equals(field)) {
				clickAction = ClickAction.getByNameID(reader.readStringTag());
			} else if (BaseComponent.JSON_VALUE.equals(field)) {
				value = reader.readStringTag();
			} else {
				reader.skipTag();
			}
		}
		reader.readNextEntry();
		if (clickAction != null)
			return new ClickEvent(clickAction, value);
		return null;
	}

}
