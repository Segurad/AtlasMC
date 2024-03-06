package de.atlasmc.factory;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.component.ChatComponent;

public interface ChatFactory {
	
	Chat asChat(CharSequence json, CharSequence legacy);
	
	/**
	 * {@inheritDoc ChatUtil#jsonFromLegacy(CharSequence, char)}
	 */
	String jsonFromLegacy(CharSequence text, char formatPrefix);
	
	String legacyFromJson(CharSequence text, char formatPrefix);
	
	ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix);
	
	ChatComponent jsonToComponent(CharSequence json);
	
	String rawTextFromComponent(ChatComponent component, char formatPrefix);
	
	String legacyFromComponent(ChatComponent component, char formatPrefix);

	String jsonToRawText(String json);

	String legacyToRawText(String legacy, char formatPrefix);

}
