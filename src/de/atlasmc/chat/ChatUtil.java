package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.factory.ChatFactory;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public final class ChatUtil {
	
	private static ChatFactory FACTORY;
	
	private ChatUtil() {}
	
	/**
	 * Represents a 'space' as component
	 */
	public static Chat ONE_SPACE;
	public static Chat EMPTY;
	
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
	
	public static String jsonFromLegacy(CharSequence msg) {
		return jsonFromLegacy(msg, '§');
	}

	public static String legacyFromJson(CharSequence text) {
		return legacyFromJson(text, '§');
	}
	
	public static String legacyFromJson(CharSequence json, char formatPrefix) {
		return null;
	}
	
	/**
	 * Converts from Legacy text<br><br>
	 * <b>Supported format options</b> (§) used as format char:<br>
	 * <ul>
	 * <li>§0 = {@link ChatColor#BLACK}
	 * <li>§1 = {@link ChatColor#DARK_BLUE}
	 * <li>§2 = {@link ChatColor#DARK_GREEN}
	 * <li>§3 = {@link ChatColor#DARK_AQUA}
	 * <li>§4 = {@link ChatColor#DARK_RED}
	 * <li>§5 = {@link ChatColor#DARK_PURPLE}
	 * <li>§6 = {@link ChatColor#GOLD}
	 * <li>§7 = {@link ChatColor#GRAY}
	 * <li>§8 = {@link ChatColor#DARK_GRAY}
	 * <li>§9 = {@link ChatColor#BLUE}
	 * <li>§a = {@link ChatColor#GREEN}
	 * <li>§b = {@link ChatColor#AQUA}
	 * <li>§c = {@link ChatColor#RED}
	 * <li>§d = {@link ChatColor#LIGHT_PURPLE}
	 * <li>§e = {@link ChatColor#YELLOW}
	 * <li>§f = {@link ChatColor#WHITE}
	 * <li>§k = obfuscated
	 * <li>§l = <b>bold</b>
	 * <li>§m = <strike>strike through</strike>
	 * <li>§n = <u>underline</u>
	 * <li>§o = <i>italic</i>
	 * <li>§r = reset formating
	 * <li>§x = rgb input (&x000000)
	 * </ul>
	 * @param text
	 * @param formatPrefix the char used to identify a format input
	 * @return the text as json
	 */
	public static String jsonFromLegacy(CharSequence text, char formatPrefix) {
		return FACTORY.jsonFromLegacy(text, formatPrefix);
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
		return legacyToComponent(legacy, '§');
	}
	
	public static ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix) {
		return FACTORY.legacyToComponent(legacy, formatPrefix);
	}
	
	public static String legacyFromComponent(ChatComponent component) {
		return legacyFromComponent(component, '§');
	}
	
	/**
	 * Converts a {@link ChatComponent} to legacy chat formated String
	 * @param component
	 * @param formatPrefix
	 * @return
	 */
	public static String legacyFromComponent(ChatComponent component, char formatPrefix) {
		return FACTORY.legacyFromComponent(component, formatPrefix);
	}
	
	public static void init(ChatFactory factory) {
		if (FACTORY != null)
			throw new IllegalStateException("ChatUtil already initialized!");
		FACTORY = factory;
		ONE_SPACE = factory.asChat("{\"text\":\" \"}", " ");
		EMPTY = factory.asChat("{\"text\":\"\"}", "");
	}
	
}
