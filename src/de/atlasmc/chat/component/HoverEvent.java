package de.atlasmc.chat.component;

public interface HoverEvent {
	
	public static enum HoverAction {
		SHOW_TEXT,
		SHOW_ITEM,
		SHOW_ENTITY
	}
	
	public String getValue();
	
	public HoverAction getAction();

}
