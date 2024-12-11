package de.atlasmc.chat.component;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.nbt.io.NBTWriter;

public interface HoverEvent {
	
	public static enum HoverAction {
		SHOW_TEXT("show_text"),
		SHOW_ITEM("show_item"),
		SHOW_ENTITY("show_entity");

		private static List<HoverAction> VALUES;
		
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
		
		public static HoverAction getByNameID(String name) {
			for (HoverAction c : getValues()) {
				if (c.getName().equals(name))
					return c;
			}
			return null;
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static HoverAction getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<HoverAction> getValues() {
			if (VALUES == null) {
				synchronized (HoverAction.class) {
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
	
	HoverAction getAction();

	void addContents(NBTWriter writer) throws IOException;

}
