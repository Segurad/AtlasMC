package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;

public class ChatUtil {
	
	public static ChatComponent ONE_SPACE = null; // TODO

	public static String buildString(Object... values) {
		final StringBuilder b = new StringBuilder();
		for (final Object o : values) {
			b.append(o);
		}
		return b.toString();
	}
	
	/**
	 * 
	 * @param msg
	 * @return the msg formated as json String
	 */
	public static ChatComponent toChat(String msg) {
		return null; // TODO chat
	}
}
