package de.atlasmc.chat.component;

import de.atlasmc.chat.Chat;

public class HoverTextEvent implements HoverEvent {

	private Chat text;
	
	public HoverTextEvent(Chat text) {
		this.text = text;
	}
	
	@Override
	public String getValue() {
		return text.getJsonText();
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_TEXT;
	}

}
