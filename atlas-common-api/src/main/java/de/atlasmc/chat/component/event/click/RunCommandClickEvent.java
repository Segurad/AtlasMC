package de.atlasmc.chat.component.event.click;

public final class RunCommandClickEvent extends AbstractCommandClickEvent {
	
	@Override
	public ClickAction getAction() {
		return ClickAction.RUN_COMMAND;
	}

}
