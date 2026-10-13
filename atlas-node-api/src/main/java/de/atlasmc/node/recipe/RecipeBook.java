package de.atlasmc.node.recipe;

import de.atlasmc.util.annotation.NotNull;

public interface RecipeBook {
	
	@NotNull
	BookType getType();
	
	boolean isOpen();
	
	void setOpen(boolean open);
	
	boolean hasFilter();
	
	void setFilter(boolean filter);

}
