package de.atlasmc.chat.component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.ClickEvent.ClickAction;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

public interface ChatComponent extends Chat, Cloneable {
	
	public static final String 
		FONT_DEFAULT = "minecraft:default",
		FONT_ALT = "minecraft:alt",
		FONT_UNIFORM = "minecraft:uniform";
	
	
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
	
	void toJson(CharSequence key, NBTWriter writer) throws IOException;
	
	/**
	 * Returns the RGB value of the color used or -1.
	 * If set by {@link #setColor(ChatColor)} it will return the index+1 as negative
	 * @return rgb or enum -(index+1) or -1
	 */
	int getColor();
	
	/**
	 * Returns the ChatColor represented by the color or null
	 * @return ChatColor or null
	 */
	ChatColor getColorChat();
	
	/**
	 * Sets the color as RGB value or -1 to remove or -({@link ChatColor#getID()}+1) for chat color
	 * @param rgb
	 */
	ChatComponent setColor(int rgb);
	
	/**
	 * Sets the color by {@link ChatColor} or null to remove.
	 * Format is allowed but the dedicated methods should be used instead
	 * @param color
	 */
	ChatComponent setColor(ChatColor color);
	
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
	
	/**
	 * Adds the contents of this component to the writer
	 * @param writer
	 */
	void addContents(NBTWriter writer) throws IOException;
	
	List<ChatComponent> getExtra();
	
	boolean hasExtra();
	
	ChatComponent addExtra(ChatComponent component);
	
	ChatComponent extra(ChatComponent... components);

	ChatComponent setExtra(Collection<ChatComponent> extra);
	
	ChatComponent clone();
	
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
		return new KeybindComponent(key);
	}
	
	static ScoreComponent score(String entry, String objective) {
		return new ScoreComponent(entry, objective);
	}
	
	static TranslationComponent translate() {
		return new TranslationComponent();
	}
	
	static TranslationComponent translate(String key) {
		return new TranslationComponent(key);
	}
	
	static ClickEvent click(ClickAction action, String value) {
		return new ClickEvent(action, value);
	}
	
	static HoverTextEvent hoverText(ChatComponent component) {
		return new HoverTextEvent(component);
	}
	
	static HoverEntityEvent hoverEntity(String type, UUID uuid, ChatComponent name) {
		return new HoverEntityEvent(type, uuid, name);
	}
	
	static HoverItemEvent hoverItem(String type, int count, NBT components) {
		return new HoverItemEvent(type, count, components);
	}
	
	static SelectorComponent selector(String selector, ChatComponent separator) {
		return new SelectorComponent(selector, separator);
	}
	
	static NBTComponent nbt() {
		return new NBTComponent();
	}
	
}
