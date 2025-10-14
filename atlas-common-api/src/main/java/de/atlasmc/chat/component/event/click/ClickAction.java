package de.atlasmc.chat.component.event.click;

import java.util.function.Supplier;

import de.atlasmc.util.EnumName;

public enum ClickAction implements EnumName {
	
	OPEN_URL("open_url", OpenUrlClickEvent::new),
	OPEN_FILE("open_file", OpenFileClickEvent::new),
	RUN_COMMAND("run_command", RunCommandClickEvent::new),
	SUGGEST_COMMAND("suggest_command", SuggestCommandClickEvent::new),
	CHANGE_PAGE("change_page", ChangePageClickEvent::new),
	COPY_TO_CLIPBOARD("copy_to_clipboard", CopyToClipboardClickEvent::new),
	SHOW_DIALOG("show_dialog", ShowDialogClickEvent::new),
	CUSTOM("custom", CustomClickEvent::new);
	
	private final Supplier<ClickEvent> constructor;
	private final String name;
	
	private ClickAction(String name, Supplier<ClickEvent> constructor) {
		this.name = name;
		this.constructor = constructor;
	}
	
	public ClickEvent createEvent() {
		return constructor.get();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
