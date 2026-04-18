package de.atlasmc.node.recipe.display;

import java.util.function.Supplier;

import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumName;

public enum DisplayType implements IDHolder, EnumName {
	
	CRAFTING_SHAPELESS(null),
	CRAFTING_SHAPED(null),
	FURNACCE(null),
	STONECUTTER(null),
	SMITHING(null);
	
	private final Supplier<RecipeDisplay> constructor;
	private String name;
	
	private DisplayType(Supplier<RecipeDisplay> constructor) {
		this.constructor = constructor;
		this.name = "minecraft:" + name().toLowerCase();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public RecipeDisplay createDisplay() {
		return constructor.get();
	}
	
	

}
