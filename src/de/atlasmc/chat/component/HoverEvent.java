package de.atlasmc.chat.component;

public interface HoverEvent {
	
	public static enum HoverAction {
		SHOW_TEXT("show_text"),
		SHOW_ITEM("show_item"),
		SHOW_ENTITY("show_entity");

		private String name;
		
		private HoverAction(String name) {
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
	
	public String getValue();
	
	public HoverAction getAction();

}
