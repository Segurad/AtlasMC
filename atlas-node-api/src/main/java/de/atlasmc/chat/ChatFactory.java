package de.atlasmc.chat;

import de.atlasmc.chat.component.ChatComponent;

public interface ChatFactory {
	
	Chat asChat(CharSequence json, CharSequence legacy);
	
	/**
	 * {@inheritDoc ChatUtil#jsonFromLegacy(CharSequence, char)}
	 */
	String legacyToJson(CharSequence text, char formatPrefix);
	
	String jsonToLegacy(CharSequence text, char formatPrefix);
	
	ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix);
	
	ChatComponent jsonToComponent(CharSequence json);
	
	String componentToRawText(ChatComponent component);
	
	String componentToLegacy(ChatComponent component, char formatPrefix);
	
	String componentToConsole(ChatComponent component);

	String jsonToRawText(String json);

	String legacyToRawText(String legacy, char formatPrefix);

	ChatComponent toComponent(Chat chat);

}
