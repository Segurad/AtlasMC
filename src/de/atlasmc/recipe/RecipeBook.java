package de.atlasmc.recipe;

public interface RecipeBook {
	
	public BookType getCurrentType();
	
	public void setCurrentType(BookType type);
	
	public boolean isOpen();
	
	public void setOpen(boolean open);
	
	public boolean hasFilter();
	
	public void setFilter(boolean filter);
	
	public Recipe getDisplayedRecipe();
	
	public void setDisplayedRecipe(Recipe recipe);

}
