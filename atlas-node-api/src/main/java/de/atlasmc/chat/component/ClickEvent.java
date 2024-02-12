package de.atlasmc.chat.component;

import java.util.List;

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
		
		private static List<ClickAction> VALUES;
		
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
		
		public static ClickAction getByNameID(String name) {
			for (ClickAction c : getValues()) {
				if (c.getName().equals(name))
					return c;
			}
			return null;
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static ClickAction getByID(int id) {
			return getValues().get(id);
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
	
	public Object getValue() {
		return value;
	}
	
	public ClickAction getAction() {
		return action;
	}

}
