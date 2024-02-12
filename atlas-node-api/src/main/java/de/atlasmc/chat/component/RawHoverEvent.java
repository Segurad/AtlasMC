package de.atlasmc.chat.component;

/**
 * {@link HoverEvent} implementation that uses raw text and has assignable action
 */
public class RawHoverEvent implements HoverEvent {

	private String value;
	private HoverAction action;
	
	public RawHoverEvent(HoverAction action, String value) {
		this.value = value;
		this.action = action;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public HoverAction getAction() {
		return action;
	}

}
