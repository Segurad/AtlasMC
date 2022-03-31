package de.atlascore.factory;

import de.atlascore.CoreChat;
import de.atlasmc.chat.Chat;
import de.atlasmc.factory.ChatFactory;

public class CoreChatFactory implements ChatFactory {

	@Override
	public Chat asChat(CharSequence json, CharSequence legacy) {
		return new CoreChat(json.toString(), legacy.toString());
	}

	@Override
	public String jsonFromLegacy(CharSequence text, char formatPrefix) {
		return null;
	}

	@Override
	public String legacyFromJson(CharSequence json, char formatPrefix) {
		if (json.charAt(0) != '{')
			return json.toString();
		return null;
	}

}
