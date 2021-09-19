package de.atlasmc;

public enum DyeColor {
	
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
	
	public static DyeColor getByID(int id) {
		return values()[id];
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
		throw new IllegalArgumentException("Invalid Material: " + material.getNamespacedName());
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

	public int getID() {
		return ordinal();
	}

}
