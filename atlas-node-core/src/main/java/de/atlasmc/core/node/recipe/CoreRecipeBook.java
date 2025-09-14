package de.atlasmc.core.node.recipe;

import de.atlasmc.node.recipe.BookType;
import de.atlasmc.node.recipe.RecipeBook;

public class CoreRecipeBook implements RecipeBook {
	
	private final BookType type;
	private boolean open;
	private boolean filter;
	private boolean changed;
	
	public CoreRecipeBook(BookType type) {
		this.type = type;
	}
	
	public BookType getType() {
		return type;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
		changed = true;
	}
	
	public boolean hasFilter() {
		return filter;
	}
	
	public void setFilter(boolean filter) {
		this.filter = filter;
		changed = true;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
