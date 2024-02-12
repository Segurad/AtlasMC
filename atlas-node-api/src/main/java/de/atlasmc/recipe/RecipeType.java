package de.atlasmc.recipe;

import java.util.List;

public enum RecipeType {
	
	CRAFTING_SHAPELESS,
	CRAFTING_SHAPED,
	SMELTING,
	BLASTING,
	SMOKING,
	CAMPFIRE_COOKING,
	STONECUTTING,
	SMITHING_TRIM,
	SMITHING_TRANSFORM,
	CRAFTING_SPECIAL_ARMORDYE,
	CRAFTING_SPECIAL_BOOKCLONING,
	CRAFTING_SPECIAL_MAPCLONING,
	CRAFTING_SPECIAL_MAPEXTENDING,
	CRAFTING_SPECIAL_FIREWORK_ROCKET,
	CRAFTING_SPECIAL_FIREWORK_STAR,
	CRAFTING_SPECIAL_FIREWORK_STAR_FADE,
	CRAFTING_SPECIAL_REPAIR_ITEM,
	CRAFTING_SPECIAL_TIPPEDARROW,
	CRAFTING_SPECIAL_BANNERDUPLICATE,
	CRAFTING_SPECIAL_SHIELDDECORATION,
	CRAFTING_SPECIAL_SHULKERBOXCOLORING,
	CRAFTING_SPECIAL_SUSPICIOUSSTEW,
	CRAFTING_DECORATED_POT;
	
	private static List<RecipeType> VALUES;
	
	private String nameID;
	
	private RecipeType() {
		this.nameID = "minecraft:" + name().toLowerCase();
	}
	
	public static RecipeType getByName(String name) {
		for (RecipeType i : getValues()) {
			if (i.name().equalsIgnoreCase(name)) 
				return i;
		}
		return null;
	}
	
	public String getNameID() {
		return nameID;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<RecipeType> getValues() {
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
