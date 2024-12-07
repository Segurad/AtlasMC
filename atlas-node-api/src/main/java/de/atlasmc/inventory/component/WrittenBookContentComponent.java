package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

public interface WrittenBookContentComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:written_book_content");
	
	List<Chat> getPages();
	
	boolean hasPages();
	
	void addPage(Chat page);
	
	void removePage(Chat page);
	
	String getAuthor();
	
	void setAuthor(String author);
	
	Generation getGeneration();
	
	void setGeneration(Generation generation);
	
	boolean isResolved();
	
	void setResolved(boolean resolved);
	
	public static enum Generation implements EnumID, EnumValueCache {
		
		ORGINAL,
		COPY_OF_ORGINAL,
		COPY_OF_COPY,
		TATTERED;

		private static List<Generation> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Generation getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Generation> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
