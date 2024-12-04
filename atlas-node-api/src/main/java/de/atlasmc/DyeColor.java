package de.atlasmc;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum DyeColor implements EnumID, EnumName, EnumValueCache {
	
	WHITE,
	ORANGE,
	MAGENTA,
	LIGHT_BLUE,
	YELLOW,
	LIME,
	PINK,
	GRAY,
	LIGHT_GRAY,
	CYAN,
	PURPLE,
	BLUE,
	BROWN,
	GREEN,
	RED,
	BLACK;
	
	private static List<DyeColor> VALUES;
	
	private String name;
	
	private DyeColor() {
		name = name().toLowerCase().intern();
	}
	
	public static DyeColor getByID(int id) {
		return getValues().get(id);
	}
	
	public static DyeColor getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<DyeColor> colors = getValues();
		final int size = colors.size();
		for (int i = 0; i < size; i++) {
			DyeColor color = colors.get(i);
			if (color.name.equals(name))
				return color;
		}
		return null;
	}
	
	public static DyeColor getByBanner(Material material) {
		if (material == Material.WHITE_BANNER || material == Material.WHITE_WALL_BANNER)
			return WHITE;
		if (material == Material.ORANGE_BANNER || material == Material.ORANGE_WALL_BANNER)
			return ORANGE;
		if (material == Material.MAGENTA_BANNER || material == Material.MAGENTA_WALL_BANNER)
			return MAGENTA;
		if (material == Material.LIGHT_BLUE_BANNER || material == Material.LIGHT_BLUE_WALL_BANNER)
			return LIGHT_BLUE;
		if (material == Material.YELLOW_BANNER || material == Material.YELLOW_WALL_BANNER)
			return YELLOW;
		if (material == Material.LIME_BANNER || material == Material.LIME_WALL_BANNER)
			return LIME;
		if (material == Material.PINK_BANNER || material == Material.PINK_WALL_BANNER)
			return PINK;
		if (material == Material.GRAY_BANNER || material == Material.GRAY_WALL_BANNER)
			return GRAY;
		if (material == Material.LIGHT_GRAY_BANNER || material == Material.LIGHT_GRAY_WALL_BANNER)
			return LIGHT_GRAY;
		if (material == Material.CYAN_BANNER || material == Material.CYAN_WALL_BANNER)
			return CYAN;
		if (material == Material.PURPLE_BANNER || material == Material.PURPLE_WALL_BANNER)
			return PURPLE;
		if (material == Material.BLUE_BANNER || material == Material.BLUE_WALL_BANNER)
			return BLUE;
		if (material == Material.BROWN_BANNER || material == Material.BROWN_WALL_BANNER)
			return BROWN;
		if (material == Material.GREEN_BANNER || material == Material.GREEN_WALL_BANNER)
			return GREEN;
		if (material == Material.RED_BANNER || material == Material.RED_WALL_BANNER)
			return RED;
		if (material == Material.BLACK_BANNER || material == Material.BLACK_WALL_BANNER)
			return BLACK;
		throw new IllegalArgumentException("Invalid Material: " + material.getNamespacedKeyRaw());
	}

	public Material getBanner() {
		switch (this) {
		case BLACK:
			return Material.BLACK_BANNER;
		case BLUE:
			return Material.BLUE_BANNER;
		case BROWN:
			return Material.BROWN_BANNER;
		case CYAN:
			return Material.CYAN_BANNER;
		case GRAY:
			return Material.GRAY_BANNER;
		case GREEN:
			return Material.GREEN_BANNER;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_BANNER;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_BANNER;
		case LIME:
			return Material.LIME_BANNER;
		case MAGENTA:
			return Material.MAGENTA_BANNER;
		case ORANGE:
			return Material.ORANGE_BANNER;
		case PINK:
			return Material.PINK_BANNER;
		case PURPLE:
			return Material.PURPLE_BANNER;
		case RED:
			return Material.RED_BANNER;
		case WHITE:
			return Material.WHITE_BANNER;
		case YELLOW:
			return Material.YELLOW_BANNER;
		default:
			return Material.BLACK_BANNER;
		}
	}

	public Material getWallBanner() {
		switch (this) {
		case BLACK:
			return Material.BLACK_WALL_BANNER;
		case BLUE:
			return Material.BLUE_WALL_BANNER;
		case BROWN:
			return Material.BROWN_WALL_BANNER;
		case CYAN:
			return Material.CYAN_WALL_BANNER;
		case GRAY:
			return Material.GRAY_WALL_BANNER;
		case GREEN:
			return Material.GREEN_WALL_BANNER;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_WALL_BANNER;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_WALL_BANNER;
		case LIME:
			return Material.LIME_WALL_BANNER;
		case MAGENTA:
			return Material.MAGENTA_WALL_BANNER;
		case ORANGE:
			return Material.ORANGE_WALL_BANNER;
		case PINK:
			return Material.PINK_WALL_BANNER;
		case PURPLE:
			return Material.PURPLE_WALL_BANNER;
		case RED:
			return Material.RED_WALL_BANNER;
		case WHITE:
			return Material.WHITE_WALL_BANNER;
		case YELLOW:
			return Material.YELLOW_WALL_BANNER;
		default:
			return Material.BLACK_WALL_BANNER;
		}
	}

	@Override
	public int getID() {
		return ordinal();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<DyeColor> getValues() {
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
