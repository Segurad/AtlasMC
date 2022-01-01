package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.TextComponent;

public final class ChatUtil {
	
	private ChatUtil() {}
	
	/**
	 * Represents a 'space' as component
	 */
	public static ChatComponent ONE_SPACE = null; // TODO

	public static String buildString(Object... values) {
		final StringBuilder b = new StringBuilder();
		for (final Object o : values) {
			b.append(o);
		}
		return b.toString();
	}
	
	/**
	 * Converts Legacy or Json Text to a ChatComponent
	 * @param msg accepts a Legacy and Json text
	 * @return the msg as ChatComponent
	 */
	public static ChatComponent toChat(String msg) {
		return msg.charAt(0) == '{' ? fromJson(msg) : fromLegacy(msg);
	}
	
	/**
	 * Converts from Json text
	 * @param json
	 * @return the json as ChatComponent
	 */
	public static ChatComponent fromJson(String json) {
		return null; // TODO chat format from legacy
	}

	public static ChatComponent fromLegacy(String text) {
		return fromLegacy(text, '§');
	}
	
	/**
	 * Converts from Legacy text<br><br>
	 * <b>Supported format options</b> (&) used as format char:<br>
	 * <ul>
	 * <li>&& = types the format char
	 * <li>&0 = {@link ChatColor#BLACK}
	 * <li>&1 = {@link ChatColor#DARK_BLUE}
	 * <li>&2 = {@link ChatColor#DARK_GREEN}
	 * <li>&3 = {@link ChatColor#DARK_AQUA}
	 * <li>&4 = {@link ChatColor#DARK_RED}
	 * <li>&5 = {@link ChatColor#DARK_PURPLE}
	 * <li>&6 = {@link ChatColor#GOLD}
	 * <li>&7 = {@link ChatColor#GRAY}
	 * <li>&8 = {@link ChatColor#DARK_GRAY}
	 * <li>&9 = {@link ChatColor#BLUE}
	 * <li>&a = {@link ChatColor#GREEN}
	 * <li>&b = {@link ChatColor#AQUA}
	 * <li>&c = {@link ChatColor#RED}
	 * <li>&d = {@link ChatColor#LIGHT_PURPLE}
	 * <li>&e = {@link ChatColor#YELLOW}
	 * <li>&f = {@link ChatColor#WHITE}
	 * <li>&k = obfuscated
	 * <li>&l = <b>bold</b>
	 * <li>&m = <strike>strikethrough</strike>
	 * <li>&n = <u>underline</u>
	 * <li>&o = <i>italic</i>
	 * <li>&r = reset formating
	 * <li>&x = rgb input (&x000000)
	 * </ul>
	 * @param text
	 * @param formatPrefix the char used to identify a format input
	 * @return the text as ChatComponent
	 */
	public static ChatComponent fromLegacy(String text, char formatPrefix) {
		final int textLength = text.length();
		final TextComponent base = new TextComponent();
		TextComponent current = base;
		boolean textReached = false;
		String textPart = null;
		int offSet = 0;
		for (int i = 0; i < textLength; i++) {
			char c = text.charAt(i);
			if (c == formatPrefix) {
				c = text.charAt(i+1);
				if (textReached && c == formatPrefix) {
					if (textPart == null) textPart = text.substring(offSet, i);
					else textPart += text.substring(offSet, i);
					offSet = ++i+1;
					continue;
				}
				if (textReached) {
					current.setText(textPart);
					current = new TextComponent();
					textReached = false;
					textPart = null;
				}
				switch (c) {
				case 'k':
					current.setObfuscated(true);
				case 'l':
					current.setBold(true);
				case 'm':
					current.setStrikethrough(true);
				case 'n':
					current.setUnderlined(true);
				case 'o':
					current.setItalic(true);
				case 'x':
					current.setColor(Integer.parseInt(text.substring(i+1, i+7), 16));
					i+=6;
				default:
					current.setColor(ChatColor.getByFormatID(c));
				}
				offSet = ++i+1;
				continue;
			}
			textReached = true;
		}
		if (textPart == null) textPart = text.substring(offSet, text.length()-1);
		else textPart += text.substring(offSet, text.length()-1);
		current.setText(textPart);
		return base;
	}
}
