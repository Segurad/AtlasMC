package de.atlasmc.inventory.meta;

import java.util.List;

public interface BookMeta extends ItemMeta {
	
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

		public static Generation getByID(int id) {
			switch (id) {
			case 0: return ORGINAL;
			case 1: return COPY_OF_ORGINAL;
			case 2: return COPY_OF_COPY;
			case 3: return TATTERED;
			default:
				return ORGINAL;
			}
		}
	}

}
