package de.atlasmc.chat.component;

public class ClickEvent {
	
	private final String value;
	private final ClickAction action;
	
	public ClickEvent(String value) {
		this(value, ClickAction.RUN_COMMAND);
	}
	
	public ClickEvent(String value, ClickAction action) {
		this.value = value;
		this.action = action;
	}
	
	public static enum ClickAction {
		RUN_COMMAND,
		SUGGEST_COMMAND,
		OPEN_URL,
		CHANGE_PAGE
	}
	
	public String getValue() {
		return value;
	}
	
	public ClickAction getAction() {
		return action;
	}

}
