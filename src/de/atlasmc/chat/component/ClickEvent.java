package de.atlasmc.chat.component;

public class ClickEvent {
	
	private final Object value;
	private final ClickAction action;
	
	public ClickEvent(Object value, ClickAction action) {
		this.value = value;
		this.action = action;
	}
	
	public static enum ClickAction {
		RUN_COMMAND("run_command"),
		SUGGEST_COMMAND("suggest_command"),
		OPEN_URL("open_url"),
		CHANGE_PAGE("change_page"),
		COPY_TO_CLIPBOARD("copy_to_clipboard");
		
		private String name;
		
		private ClickAction(String name) {
			this.name = name;
		}
		
		/**
		 * Returns the lower case name
		 * @return name
		 */
		public String getName() {
			return name;
		}
		
	}
	
	public Object getValue() {
		return value;
	}
	
	public ClickAction getAction() {
		return action;
	}

}
