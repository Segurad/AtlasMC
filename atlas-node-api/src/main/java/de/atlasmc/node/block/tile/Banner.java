package de.atlasmc.node.block.tile;

import java.util.List;
import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.Nameable;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Banner extends TileEntity, Nameable {
	
	public static final NBTCodec<Banner>
	NBT_HANDLER = NBTCodec
					.builder(Banner.class)
					.include(TileEntity.NBT_HANDLER)
					.include(Nameable.NBT_HANDLER)
					.typeList("patterns", Banner::hasPatterns, Banner::getPatterns, Pattern.NBT_HANDLER)
					.build();
	
	boolean hasPatterns();
	
	void addPattern(Pattern pattern);
	
	DyeColor getBaseColor();
	
	void setBaseColor(DyeColor color, boolean wall);
	
	Pattern getPattern(int index);
	
	List<Pattern> getPatterns();
	
	int numberOfPatterns();
	
	Pattern removePattern(int index);
	
	void setPattern(int index, Pattern pattern);
	
	void setPatterns(List<Pattern> pattern);
	
	Chat getCustomName();
	
	void setCustomName(Chat name);
	
	@Override
	default NBTCodec<? extends Banner> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static class Pattern implements NBTSerializable {
		
		public static final NBTCodec<Pattern>
		NBT_HANDLER = NBTCodec
						.builder(Pattern.class)
						.defaultConstructor(Pattern::new)
						.enumStringField("color", Pattern::getColor, Pattern::setColor, DyeColor.class, null)
						.interfacedEnumStringField("pattern", (Function<Pattern, PatternType>) Pattern::getType, Pattern::setType, EnumPatternType.class, null)
						.typeCompoundField("pattern", (Function<Pattern, PatternType>) Pattern::getType, Pattern::setType, ResourcePatternType.NBT_HANDLER)
						.build();
		
		private DyeColor color;
		private PatternType type;
		
		public Pattern() {}
		
		public Pattern(DyeColor color, PatternType type) {
			this.color = color;
			this.type = type;
		}
		
		public DyeColor getColor() {
			return color;
		}
		
		public void setColor(DyeColor color) {
			this.color = color;
		}
		
		public PatternType getType() {
			return type;
		}
		
		public void setType(PatternType type) {
			this.type = type;
		}
		
		@Override
		public NBTCodec<? extends Pattern> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}
	
	public static interface PatternType {}
	
	public static class ResourcePatternType implements PatternType, NBTSerializable {
		
		public static final NBTCodec<ResourcePatternType>
		NBT_HANDLER = NBTCodec
						.builder(ResourcePatternType.class)
						.defaultConstructor(ResourcePatternType::new)
						.namespacedKey("asset_id", ResourcePatternType::getAssetID, ResourcePatternType::setAssetID)
						.string("translation_key", ResourcePatternType::getTranslationKey, ResourcePatternType::setTranslationKey)
						.build();
		
		private NamespacedKey assetID;
		private String translationKey;
		
		private ResourcePatternType() {}
		
		public ResourcePatternType(NamespacedKey key, String translationKey) {
			this.assetID = key;
			this.translationKey = translationKey;
		}
		
		public NamespacedKey getAssetID() {
			return assetID;
		}
		
		public String getTranslationKey() {
			return translationKey;
		}
		
		public void setAssetID(NamespacedKey assetID) {
			this.assetID = assetID;
		}
		
		public void setTranslationKey(String translationKey) {
			this.translationKey = translationKey;
		}
		
		@Override
		public NBTCodec<? extends ResourcePatternType> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}
	
	public static enum EnumPatternType implements PatternType, IDHolder, EnumName {
		
		FULL_COLOR_FIELD("base"),
		BOTTOM_STRIPE("stripe_bottom"),
		TOP_STRIPE("stripe_top"),
		LEFT_STRIPE("stripe_left"),
		RIGHT_STRIPE("stripe_right"),
		CENTER_STRIPE("stripe_center"),
		MIDDLE_STRIPE("stripe_middle"),
		DOWN_RIGHT_STRIPE("stripe_downright"),
		DOWN_LEFT_STRIPE("stripe_downleft"),
		SMALL_STRIPES("small_stripes"),
		CROSS("cross"),
		SQUARE_CROSS("straight_cross"),
		LEFT_DIAGONAL("diagonal_left"),
		RIGHT_DIAGONAL("diagonal_right"),
		LEFT_UPSIDEDOWN_DIAGONAL("diagonal_up_left"),
		RIGHT_UPSIDEDOWN_DIAGONAL("diagonal_up_right"),
		VERTICAL_HALF("half_vertical"),
		VERTICAL_HALF_RIGHT("half_vertical_right"),
		HORIZONTAL_HALF("half_horizontal"),
		HORIZONTAL_HALF_BOTTOM("half_horizontal_bottom"),
		BOTTOM_LEFT("square_bottom_left"),
		BOTTOM_RIGHT("square_bottom_right"),
		TOP_LEFT("square_top_left"),
		TOP_RIGHT("square_top_right"),
		BOTTOM_TRIANGEL("triangle_bottom"),
		TOP_TRIANGLE("triangle_top"),
		BOTTOM_TRIANGLE_SAWTOOTH("triangles_bottom"),
		TOP_TRIANGLE_SAWTOOTH("triangles_top"),
		MIDDLE_CIRCLE("circle"),
		MIDDLE_RHOMBUS("rhombus"),
		BORDER("border"),
		CURLY_BORDER("curly_border"),
		BRICK("bricks"),
		GRADIENT("gradient"),
		GRADIENT_UPSIDE_DOWN("gradient_up"),
		CREEPER("creeer"),
		SKULL("skull"),
		FLOWER("flower"),
		MOJANG("mojang"),
		GLOBE("globe"),
		SNOUT("snout"),
		FLOW("flow"),
		GUSTER("guster");

		private final String name;
		
		private EnumPatternType(String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
