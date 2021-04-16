package de.atlasmc.chat.component;

public class HoverEvent {
	
	private final String value;
	private final HoverAction action;
	
	public HoverEvent(String value, HoverAction action) {
		this.value = value;
		this.action = action;
	}
	
	public static enum HoverAction {
		SHOW_TEXT,
		SHOW_ITEM,
		SHOW_ENTITY
	}
	
	public String getValue() {
		return value;
	}
	
	public HoverAction getAction() {
		return action;
	}

}
