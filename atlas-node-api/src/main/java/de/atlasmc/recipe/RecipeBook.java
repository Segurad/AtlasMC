package de.atlasmc.recipe;

public interface RecipeBook {
	
	public BookType getType();
	
	public boolean isOpen();
	
	public void setOpen(boolean open);
	
	public boolean hasFilter();
	
	public void setFilter(boolean filter);

}
