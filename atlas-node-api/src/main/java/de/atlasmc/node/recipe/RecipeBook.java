package de.atlasmc.node.recipe;

public interface RecipeBook {
	
	BookType getType();
	
	boolean isOpen();
	
	void setOpen(boolean open);
	
	boolean hasFilter();
	
	void setFilter(boolean filter);

}
