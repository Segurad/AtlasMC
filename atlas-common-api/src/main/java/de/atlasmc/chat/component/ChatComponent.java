package de.atlasmc.chat.component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.ColorValue;
import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.event.click.ClickEvent;
import de.atlasmc.chat.component.event.hover.HoverEntityEvent;
import de.atlasmc.chat.component.event.hover.HoverEvent;
import de.atlasmc.chat.component.event.hover.HoverItemEvent;
import de.atlasmc.chat.component.event.hover.HoverTextEvent;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.tag.NBT;

public interface ChatComponent extends Chat, NBTSerializable {
	
	public static final String 
		FONT_DEFAULT = "minecraft:default",
		FONT_ALT = "minecraft:alt",
		FONT_UNIFORM = "minecraft:uniform";
	
	public static final NBTCodec<ChatComponent> 
	NBT_HANDLER = NBTCodec
					.builder(ChatComponent.class)
					.searchKeyEnumConstructor("type", ComponentType.class, ComponentType::createComponent, ChatComponent::getType)
					.defaultConstructor(BaseComponent::new)
					.boolField("bold", ChatComponent::isBold, ChatComponent::setBold, false)
					.boolField("italic", ChatComponent::isItalic, ChatComponent::setItalic, false)
					.boolField("underlined", ChatComponent::isUnderlined, ChatComponent::setUnderlined, false)
					.boolField("obfuscated", ChatComponent::isObfuscated, ChatComponent::setObfuscated, false)
					.boolField("strikethrough", ChatComponent::isStrikethrough, ChatComponent::setStrikethrough, false)
					.color("shadow_color", ChatComponent::getShadowColor, ChatComponent::setShadowColor, null)
					.colorValue("color", ChatComponent::getColor, ChatComponent::setColor)
					.string("font", ChatComponent::getFont, ChatComponent::setFont)
					.typeList("extra", ChatComponent::hasExtra, ChatComponent::getExtra, ChatComponent.NBT_HANDLER)
					.string("insertion", ChatComponent::getInsertion, ChatComponent::setInsertion)
					.typeCompoundField("click_event", ChatComponent::getClickEvent, ChatComponent::setClickEvent, ClickEvent.NBT_HANDLER)
					.typeCompoundField("hover_event", ChatComponent::getHoverEvent, ChatComponent::setHoverEvent, HoverEvent.NBT_HANDLER)
					.build();
	
	@Nullable
	ComponentType getType();
	
	String getInsertion();
	
	/**
	 * Sets a insertion text. If player shift-clicks the insertion text will be inserted into the chat input of the player.
	 * @param insertion
	 * @return this component
	 */
	ChatComponent setInsertion(String insertion);
	
	boolean isBold();
	
	ChatComponent setBold(boolean bold);
	
	boolean isItalic();
	
	ChatComponent setItalic(boolean italic);
	
	boolean isUnderlined();
	
	ChatComponent setUnderlined(boolean underlined);
	
	boolean isObfuscated();
	
	ChatComponent setObfuscated(boolean obfuscated);
	
	boolean isStrikethrough();
	
	ChatComponent setStrikethrough(boolean strikethrough);
	
	String getFont();
	
	ChatComponent setFont(String font);
	
	/**
	 * Returns the color value
	 * @return color
	 */
	ColorValue getColor();
	
	/**
	 * Sets the color or null to remove.
	 * {@link ChatColor} format color is allowed but the dedicated methods should be used instead
	 * @param color
	 */
	ChatComponent setColor(ColorValue value);
	
	/**
	 * Returns whether or not this Component has a color
	 * @return true if has color
	 */
	boolean hasColor();
	
	/**
	 * Returns whether or not this Component has a {@link ChatColor}
	 * @return true if has {@link ChatColor}
	 */
	boolean hasChatColor();
	
	ClickEvent getClickEvent();
	
	ChatComponent setClickEvent(ClickEvent event);
	
	HoverEvent getHoverEvent();
	
	ChatComponent setHoverEvent(HoverEvent event);
	
	List<ChatComponent> getExtra();
	
	boolean hasExtra();
	
	Color getShadowColor();
	
	void setShadowColor(Color color);
	
	boolean hasShadowColor();
	
	ChatComponent addExtra(ChatComponent component);
	
	ChatComponent extra(ChatComponent... components);

	ChatComponent setExtra(Collection<ChatComponent> extra);
	
	ChatComponent clone();
	
	@Override
	default NBTCodec<? extends ChatComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	static BaseComponent chat(ChatComponent... components) {
		BaseComponent comp = new BaseComponent();
		comp.extra(components);
		return comp;
	}
	
	static BaseComponent base() {
		return new BaseComponent();
	}
	
	static TextComponent text() {
		return new TextComponent();
	}
	
	static TextComponent text(String text) {
		return new TextComponent(text);
	}
	
	static TextComponent empty() {
		return new TextComponent("");
	}
	
	static TextComponent space() {
		return new TextComponent(" ");
	}
	
	static KeybindComponent key(String key) {
		KeybindComponent comp = new KeybindComponent();
		comp.setKey(key);
		return comp;
	}
	
	static ScoreComponent score(String name, String objective) {
		ScoreComponent comp = new ScoreComponent();
		comp.setName(name);
		comp.setObjective(objective);
		return comp;
	}
	
	static TranslationComponent translate() {
		return new TranslationComponent();
	}
	
	static TranslationComponent translate(String key) {
		TranslationComponent comp = new TranslationComponent();
		comp.setKey(key);
		return comp;
	}
	
	static HoverTextEvent hoverText(Chat value) {
		HoverTextEvent event = new HoverTextEvent();
		event.setValue(value);
		return event;
	}
	
	static HoverEntityEvent hoverEntity(NamespacedKey type, UUID uuid, ChatComponent name) {
		HoverEntityEvent event = new HoverEntityEvent();
		event.setType(type);
		event.setUUUID(uuid);
		event.setName(name);
		return event;
	}
	
	static HoverItemEvent hoverItem(NamespacedKey type, int count, NBT components) {
		HoverItemEvent event = new HoverItemEvent();
		event.setID(type);
		event.setCount(count);
		event.setComponents(components);
		return event;
	}
	
	static SelectorComponent selector(String selector, ChatComponent separator) {
		return new SelectorComponent(selector, separator);
	}
	
	static NBTComponent nbt() {
		return new NBTComponent();
	}
	
}
