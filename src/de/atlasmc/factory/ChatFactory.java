package de.atlasmc.factory;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.component.ChatComponent;

public interface ChatFactory {
	
	public Chat asChat(CharSequence json, CharSequence legacy);
	
	/**
	 * {@inheritDoc ChatUtil#jsonFromLegacy(CharSequence, char)}
	 */
	public String jsonFromLegacy(CharSequence text, char formatPrefix);
	
	public String legacyFromJson(CharSequence text, char formatPrefix);
	
	public ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix);
	
	public ChatComponent jsonToComponent(CharSequence json);
	
	public String legacyFromComponent(ChatComponent component, char formatPrefix);

}
