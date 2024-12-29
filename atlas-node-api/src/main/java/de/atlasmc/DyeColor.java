package de.atlasmc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum DyeColor implements EnumID, EnumName, EnumValueCache {
	
	WHITE(0xF9FFFE),
	ORANGE(0xF9801D),
	MAGENTA(0xC74EBD),
	LIGHT_BLUE(0x3AB3DA),
	YELLOW(0xFED83D),
	LIME(0x80C71F),
	PINK(0xF38BAA),
	GRAY(0x474F52),
	LIGHT_GRAY(0x9D9D97),
	CYAN(0x169C9C),
	PURPLE(0x8932B8),
	BLUE(0x3C44AA),
	BROWN(0x835432),
	GREEN(0x5E7C16),
	RED(0xB02E26),
	BLACK(0x1D1D21);
	
	private static List<DyeColor> VALUES;
	
	private static final Map<NamespacedKey, DyeColor> DYE_BY_BANNER;
	private static final Map<DyeColor, NamespacedKey> BANNER_BY_DYE;
	private static final Map<DyeColor, NamespacedKey> WALL_BANNER_BY_DYE;
	
	static {
		HashMap<DyeColor, NamespacedKey> toBanner = new HashMap<>();
		HashMap<DyeColor, NamespacedKey> toWallBanner = new HashMap<>();
		toBanner.put(WHITE, Material.WHITE_BANNER);
		toWallBanner.put(WHITE, Material.WHITE_WALL_BANNER);
		toBanner.put(ORANGE, Material.ORANGE_BANNER);
		toWallBanner.put(ORANGE, Material.ORANGE_WALL_BANNER);
		toBanner.put(MAGENTA, Material.MAGENTA_BANNER);
		toWallBanner.put(MAGENTA, Material.MAGENTA_WALL_BANNER);
		toBanner.put(LIGHT_BLUE, Material.LIGHT_BLUE_BANNER);
		toWallBanner.put(LIGHT_BLUE, Material.LIGHT_BLUE_WALL_BANNER);
		toBanner.put(YELLOW, Material.YELLOW_BANNER);
		toWallBanner.put(YELLOW, Material.YELLOW_WALL_BANNER);
		toBanner.put(LIME, Material.LIME_BANNER);
		toWallBanner.put(LIME, Material.LIME_WALL_BANNER);
		toBanner.put(PINK, Material.PINK_BANNER);
		toWallBanner.put(PINK, Material.PINK_WALL_BANNER);
		toBanner.put(GRAY, Material.GRAY_BANNER);
		toWallBanner.put(GRAY, Material.GRAY_WALL_BANNER);
		toBanner.put(LIGHT_GRAY, Material.LIGHT_GRAY_BANNER);
		toWallBanner.put(LIGHT_GRAY, Material.LIGHT_GRAY_BANNER);
		toBanner.put(CYAN, Material.CYAN_BANNER);
		toWallBanner.put(CYAN, Material.CYAN_WALL_BANNER);
		toBanner.put(PURPLE, Material.PURPLE_BANNER);
		toWallBanner.put(PURPLE, Material.PURPLE_WALL_BANNER);
		toBanner.put(BLUE, Material.BLUE_BANNER);
		toWallBanner.put(BLUE, Material.BLUE_WALL_BANNER);
		toBanner.put(BROWN, Material.BROWN_BANNER);
		toWallBanner.put(BROWN, Material.BROWN_WALL_BANNER);
		toBanner.put(GREEN, Material.GREEN_BANNER);
		toWallBanner.put(GREEN, Material.GREEN_WALL_BANNER);
		toBanner.put(RED, Material.RED_BANNER);
		toWallBanner.put(RED, Material.RED_WALL_BANNER);
		toBanner.put(BLACK, Material.BLACK_BANNER);
		toWallBanner.put(BLACK, Material.BLACK_WALL_BANNER);
		BANNER_BY_DYE = Map.copyOf(toBanner);
		WALL_BANNER_BY_DYE = Map.copyOf(toWallBanner);
		HashMap<NamespacedKey, DyeColor> byBanner = new HashMap<>();
		toBanner.forEach((k, v) -> {
			byBanner.put(v, k);
		});
		toWallBanner.forEach((k, v) -> {
			byBanner.put(v, k);
		});
		DYE_BY_BANNER = Map.copyOf(byBanner);
	}
	
	private final Color color;
	private final String name;
	
	private DyeColor(int rgb) {
		name = name().toLowerCase().intern();
		this.color = Color.fromRGB(rgb);
	}
	
	/**
	 * Returns the color representation of the dye color
	 * @return color
	 */
	public Color getColor() {
		return color;
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
		if (material == null)
			throw new IllegalArgumentException("Material can not be null!");
		return DYE_BY_BANNER.get(material.getNamespacedKey());
	}

	public Material getBanner() {
		NamespacedKey key = BANNER_BY_DYE.get(this);
		return Material.get(key);
	}

	public Material getWallBanner() {
		NamespacedKey key = WALL_BANNER_BY_DYE.get(this);
		return Material.get(key);
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
