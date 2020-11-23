package de.atlasmc.chat;

public class MessageUtil {
	
	
	
	public static String buildString(Object... values) {
		final StringBuilder b = new StringBuilder();
		for (final Object o : values) {
			b.append(o);
		}
		return b.toString();
	}
}
