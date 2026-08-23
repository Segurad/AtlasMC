package de.atlasmc.node;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.Color;
import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.util.enums.EnumName;

public enum DyeColor implements IDHolder, EnumName {
	
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
	
	private static final Map<NamespacedKey, DyeColor> DYE_BY_BANNER;
	private static final Map<DyeColor, RegistryValueKey<BlockType>> BANNER_BY_DYE;
	private static final Map<DyeColor, RegistryValueKey<BlockType>> WALL_BANNER_BY_DYE;
	
	static {
		HashMap<DyeColor, RegistryValueKey<BlockType>> toBanner = new HashMap<>();
		HashMap<DyeColor, RegistryValueKey<BlockType>> toWallBanner = new HashMap<>();
		toBanner.put(WHITE, BlockType.WHITE_BANNER);
		toWallBanner.put(WHITE, BlockType.WHITE_WALL_BANNER);
		toBanner.put(ORANGE, BlockType.ORANGE_BANNER);
		toWallBanner.put(ORANGE, BlockType.ORANGE_WALL_BANNER);
		toBanner.put(MAGENTA, BlockType.MAGENTA_BANNER);
		toWallBanner.put(MAGENTA, BlockType.MAGENTA_WALL_BANNER);
		toBanner.put(LIGHT_BLUE, BlockType.LIGHT_BLUE_BANNER);
		toWallBanner.put(LIGHT_BLUE, BlockType.LIGHT_BLUE_WALL_BANNER);
		toBanner.put(YELLOW, BlockType.YELLOW_BANNER);
		toWallBanner.put(YELLOW, BlockType.YELLOW_WALL_BANNER);
		toBanner.put(LIME, BlockType.LIME_BANNER);
		toWallBanner.put(LIME, BlockType.LIME_WALL_BANNER);
		toBanner.put(PINK, BlockType.PINK_BANNER);
		toWallBanner.put(PINK, BlockType.PINK_WALL_BANNER);
		toBanner.put(GRAY, BlockType.GRAY_BANNER);
		toWallBanner.put(GRAY, BlockType.GRAY_WALL_BANNER);
		toBanner.put(LIGHT_GRAY, BlockType.LIGHT_GRAY_BANNER);
		toWallBanner.put(LIGHT_GRAY, BlockType.LIGHT_GRAY_BANNER);
		toBanner.put(CYAN, BlockType.CYAN_BANNER);
		toWallBanner.put(CYAN, BlockType.CYAN_WALL_BANNER);
		toBanner.put(PURPLE, BlockType.PURPLE_BANNER);
		toWallBanner.put(PURPLE, BlockType.PURPLE_WALL_BANNER);
		toBanner.put(BLUE, BlockType.BLUE_BANNER);
		toWallBanner.put(BLUE, BlockType.BLUE_WALL_BANNER);
		toBanner.put(BROWN, BlockType.BROWN_BANNER);
		toWallBanner.put(BROWN, BlockType.BROWN_WALL_BANNER);
		toBanner.put(GREEN, BlockType.GREEN_BANNER);
		toWallBanner.put(GREEN, BlockType.GREEN_WALL_BANNER);
		toBanner.put(RED, BlockType.RED_BANNER);
		toWallBanner.put(RED, BlockType.RED_WALL_BANNER);
		toBanner.put(BLACK, BlockType.BLACK_BANNER);
		toWallBanner.put(BLACK, BlockType.BLACK_WALL_BANNER);
		BANNER_BY_DYE = Map.copyOf(toBanner);
		WALL_BANNER_BY_DYE = Map.copyOf(toWallBanner);
		HashMap<NamespacedKey, DyeColor> byBanner = new HashMap<>();
		toBanner.forEach((k, v) -> {
			byBanner.put(v.getNamespacedKey(), k);
		});
		toWallBanner.forEach((k, v) -> {
			byBanner.put(v.getNamespacedKey(), k);
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
	
	public static DyeColor getByBanner(ItemType type) {
		return DYE_BY_BANNER.get(type.getNamespacedKey());
	}
	
	public static DyeColor getByBanner(BlockType type) {
		return DYE_BY_BANNER.get(type.getNamespacedKey());
	}
	
	public ItemType getBannerItem() {
		return ItemType.REGISTRY_KEY.getValue(BANNER_BY_DYE.get(this).getNamespacedKey());
	}

	public BlockType getBanner() {
		return BANNER_BY_DYE.get(this).get();
	}

	public BlockType getWallBanner() {
		return WALL_BANNER_BY_DYE.get(this).get();
	}

	@Override
	public int getID() {
		return ordinal();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
