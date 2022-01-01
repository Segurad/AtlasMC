package de.atlasmc.chat.component;

public class HoverTextEvent implements HoverEvent {

	private ChatComponent text;
	
	public HoverTextEvent(ChatComponent text) {
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
