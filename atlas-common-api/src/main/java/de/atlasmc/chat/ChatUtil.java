package de.atlasmc.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public final class ChatUtil {
	
	/**
	 * Represents a 'space' as component
	 */
	public static Chat ONE_SPACE;
	public static Chat EMPTY;
	private static ChatFactory FACTORY;
	
	public static final char DEFAULT_CHAT_FORMAT_PREFIX;
	
	static {
		DEFAULT_CHAT_FORMAT_PREFIX = System.getProperty("de.atlasmc.chat.defaultLegacyPrefix", "§").charAt(0);
	}
	
	private ChatUtil() {}
	
	public static Chat toChat(CharSequence chat) {
		if (chat == null)
			throw new IllegalArgumentException("Chat can not be null!");
		if (chat.length() == 0)
			return EMPTY;
		if (chat.charAt(0) == ' ')
			return ONE_SPACE;
		if (chat.charAt(0) == '{')
			return toChat(chat, null);
		return toChat(null, chat);
	}
	
	public static Chat toChat(CharSequence json, CharSequence legacy) {
		return FACTORY.asChat(json, legacy);
	}
	
	public static List<Chat> toChat(List<String> text) {
		if (text == null)
			throw new IllegalArgumentException("Text can not be null!");
		List<Chat> chat = new ArrayList<>(text.size());
		for (CharSequence s : text)
			chat.add(toChat(s));
		return chat;
	}
	
	public static String legacyToJson(CharSequence msg) {
		return legacyToJson(msg, DEFAULT_CHAT_FORMAT_PREFIX);
	}
	
	public static String jsonToLegacy(CharSequence json) {
		return jsonToLegacy(json, DEFAULT_CHAT_FORMAT_PREFIX);
	}
	
	public static String jsonToLegacy(CharSequence json, char formatPrefix) {
		return FACTORY.jsonToLegacy(json, formatPrefix);
	}
	
	/**
	 * Converts from Legacy text<br><br>
	 * <b>Supported format options</b> (§) used as format char:<br>
	 * <ul>
	 * <li>§0 = {@link ChatColor#BLACK}</li>
	 * <li>§1 = {@link ChatColor#DARK_BLUE}</li>
	 * <li>§2 = {@link ChatColor#DARK_GREEN}</li>
	 * <li>§3 = {@link ChatColor#DARK_AQUA}</li>
	 * <li>§4 = {@link ChatColor#DARK_RED}</li>
	 * <li>§5 = {@link ChatColor#DARK_PURPLE}</li>
	 * <li>§6 = {@link ChatColor#GOLD}</li>
	 * <li>§7 = {@link ChatColor#GRAY}</li>
	 * <li>§8 = {@link ChatColor#DARK_GRAY}</li>
	 * <li>§9 = {@link ChatColor#BLUE}</li>
	 * <li>§a = {@link ChatColor#GREEN}</li>
	 * <li>§b = {@link ChatColor#AQUA}</li>
	 * <li>§c = {@link ChatColor#RED}</li>
	 * <li>§d = {@link ChatColor#LIGHT_PURPLE}</li>
	 * <li>§e = {@link ChatColor#YELLOW}</li>
	 * <li>§f = {@link ChatColor#WHITE}</li>
	 * <li>§k = obfuscated</li>
	 * <li>§l = <b>bold</b></li>
	 * <li>§m = <del>strike through</del></li>
	 * <li>§n = <u>underline</u></li>
	 * <li>§o = <i>italic</i></li>
	 * <li>§r = reset formating</li>
	 * <li>§x = rgb input (&x000000)</li>
	 * </ul>
	 * @param text
	 * @param formatPrefix the char used to identify a format input
	 * @return the text as json
	 */
	public static String legacyToJson(CharSequence text, char formatPrefix) {
		return FACTORY.legacyToJson(text, formatPrefix);
	}
	
	/**
	 * Converts a json {@link CharSequence} to a {@link ChatComponent}
	 * @param json
	 * @return
	 */
	public static ChatComponent jsonToComponent(CharSequence json) {
		return FACTORY.jsonToComponent(json);
	}
	
	public static ChatComponent legacyToComponent(CharSequence legacy) {
		return legacyToComponent(legacy, DEFAULT_CHAT_FORMAT_PREFIX);
	}
	
	public static ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix) {
		return FACTORY.legacyToComponent(legacy, formatPrefix);
	}
	
	public static String componentToLegacy(ChatComponent component) {
		return componentToLegacy(component, DEFAULT_CHAT_FORMAT_PREFIX);
	}
	
	/**
	 * Converts a {@link ChatComponent} to legacy chat formated String
	 * @param component
	 * @param formatPrefix
	 * @return
	 */
	public static String componentToLegacy(ChatComponent component, char formatPrefix) {
		return FACTORY.componentToLegacy(component, formatPrefix);
	}
	
	public static String componentToConsole(ChatComponent component) {
		return FACTORY.componentToConsole(component);
	}
	
	public static void init(ChatFactory factory) {
		if (FACTORY != null)
			throw new IllegalStateException("ChatUtil already initialized!");
		FACTORY = factory;
		ONE_SPACE = factory.asChat("{\"text\":\" \"}", " ");
		EMPTY = factory.asChat("{\"text\":\"\"}", "");
	}

	public static String jsonToRawText(String json) {
		return FACTORY.jsonToRawText(json);
	}

	public static String legacyToRawText(String legacy) {
		return FACTORY.legacyToRawText(legacy, DEFAULT_CHAT_FORMAT_PREFIX);
	}
	
	public static String legacyToRawText(String legacy, char fomatPrefix) {
		return FACTORY.legacyToRawText(legacy, fomatPrefix);
	}
	
	public static String componentToRawText(ChatComponent component) {
		return FACTORY.componentToRawText(component);
	}

	public static ChatComponent toComponent(Chat chat) {
		return FACTORY.toComponent(chat);
	}
	
	public static Chat fromNBT(NBTReader reader) throws IOException {
		return FACTORY.fromNBT(reader);
	}
	
	public static void toNBT(CharSequence key, Chat chat, NBTWriter writer) throws IOException {
		FACTORY.toNBT(key, chat, writer);
	}

	
}
