package de.atlasmc.chat.component;

import java.util.Collection;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.ClickEvent.ClickAction;
import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.JsonBuffer;

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
	
	/**
	 * Returns the RGB value of the color used or -1.
	 * If set by {@link #color(ChatColor)} it will return the index+1 as negative
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
	ChatComponent color(ChatColor color);
	
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
	 * Adds the contents of this component to the buffer
	 * @param buff
	 */
	void addContents(JsonBuffer buff);
	
	List<ChatComponent> getExtra();
	
	boolean hasExtra();
	
	ChatComponent addExtra(ChatComponent component);
	
	ChatComponent extra(ChatComponent... components);

	ChatComponent setExtra(Collection<ChatComponent> extra);
	
	ChatComponent clone();
	
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
	
	static ScoreComponent score(String entry, String objective, String score) {
		return new ScoreComponent(entry, objective, score);
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
	
	static HoverEntityEvent hoverEntity(Entity entity) {
		return new HoverEntityEvent(entity);
	}
	
	static HoverEntityEvent hoverEntity(String type, String uuid, ChatComponent name) {
		return new HoverEntityEvent(type, uuid, name);
	}
	
	static HoverItemEvent hoverItem(ItemStack item) {
		return new HoverItemEvent(item);
	}
	
	static HoverItemEvent hoverItem(String type, int count, String tag) {
		return new HoverItemEvent(type, count, tag);
	}
	
}
