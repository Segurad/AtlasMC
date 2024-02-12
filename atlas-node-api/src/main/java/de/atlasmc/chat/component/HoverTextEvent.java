package de.atlasmc.chat.component;

public class HoverTextEvent implements HoverEvent {

	private ChatComponent component;
	
	public HoverTextEvent(ChatComponent component) {
		this.component = component;
	}
	
	@Override
	public String getValue() {
		return component.getJsonText();
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_TEXT;
	}

}
