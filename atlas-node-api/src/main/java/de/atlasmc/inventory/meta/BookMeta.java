package de.atlasmc.inventory.meta;

import java.util.List;

public interface BookMeta extends ItemMeta {
	
	public BookMeta clone();
	
	public Generation getGeneration();
	
	public boolean hasGeneration();
	
	public void setGeneration(Generation generation);
	
	public String getAuthor();
	
	public boolean hasAuthor();
	
	public void setAuthor(String author);
	
	public String getTitle();
	
	public boolean hasTitle();
	
	public void setTitle(String title);
	
	public List<String> getPages();
	
	public void addPage(String... pages);
	
	public void setPage(int page, String data);
	
	public void setPage(List<String> pages);
	
	public boolean hasPages();
	
	public String getPage(int page);
	
	public int getPageCount();
	
	public boolean isResolved();
	
	public void setResolved(boolean resolved);
	
	public static enum Generation {
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
