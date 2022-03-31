package de.atlasmc.factory;

import de.atlasmc.chat.Chat;

public interface ChatFactory {
	
	public Chat asChat(CharSequence json, CharSequence legacy);
	
	/**
	 * {@inheritDoc ChatUtil#jsonFromLegacy(CharSequence, char)}
	 */
	public String jsonFromLegacy(CharSequence text, char formatPrefix);
	
	public String legacyFromJson(CharSequence text, char formatPrefix);

}
