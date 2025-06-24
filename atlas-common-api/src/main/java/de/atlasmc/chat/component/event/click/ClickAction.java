package de.atlasmc.chat.component.event.click;

import java.util.List;
import java.util.function.Supplier;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.annotation.Nullable;

public enum ClickAction implements EnumName, EnumValueCache {
	
	OPEN_URL("open_url", OpenUrlClickEvent::new),
	OPEN_FILE("open_file", OpenFileClickEvent::new),
	RUN_COMMAND("run_command", RunCommandClickEvent::new),
	SUGGEST_COMMAND("suggest_command", SuggestCommandClickEvent::new),
	CHANGE_PAGE("change_page", ChangePageClickEvent::new),
	COPY_TO_CLIPBOARD("copy_to_clipboard", CopyToClipboardClickEvent::new),
	SHOW_DIALOG("show_dialog", ShowDialogClickEvent::new),
	CUSTOM("custom", CustomClickEvent::new);
	
	private static List<ClickAction> VALUES;
	
	private final Supplier<ClickEvent> constructor;
	private final String name;
	
	private ClickAction(String name, Supplier<ClickEvent> constructor) {
		this.name = name;
		this.constructor = constructor;
	}
	
	public ClickEvent createEvent() {
		return constructor.get();
	}
	
	/**
	 * Returns the lower case name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	@Nullable
	public static ClickAction getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		final List<ClickAction> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			ClickAction action = values.get(i);
			if (action.getName().equals(name))
				return action;
		}
		return null;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ClickAction> getValues() {
		if (VALUES == null) {
			synchronized (ClickAction.class) {
				if (VALUES == null)
					VALUES = List.of(values());
			}
		}
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
}
