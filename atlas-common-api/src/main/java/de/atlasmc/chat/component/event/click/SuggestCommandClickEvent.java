package de.atlasmc.chat.component.event.click;

public final class SuggestCommandClickEvent extends AbstractCommandClickEvent {
	
	@Override
	public ClickAction getAction() {
		return ClickAction.SUGGEST_COMMAND;
	}

}
