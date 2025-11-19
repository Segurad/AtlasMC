package de.atlasmc.node.block.tile;

import java.util.List;
import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.Nameable;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface Banner extends TileEntity, Nameable {
	
	public static final NBTCodec<Banner>
	NBT_HANDLER = NBTCodec
					.builder(Banner.class)
					.include(TileEntity.NBT_HANDLER)
					.include(Nameable.NBT_HANDLER)
					.codecList("patterns", Banner::hasPatterns, Banner::getPatterns, Pattern.NBT_CODEC)
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
	
	public static class Pattern implements NBTSerializable, StreamSerializable {
		
		public static final NBTCodec<Pattern>
		NBT_CODEC = NBTCodec
						.builder(Pattern.class)
						.defaultConstructor(Pattern::new)
						.codec("color", Pattern::getColor, Pattern::setColor, EnumUtil.enumStringNBTCodec(DyeColor.class))
						.codec("pattern", Pattern::getType, Pattern::setType, PatternType.NBT_CODEC)
						.build();
		
		public static final StreamCodec<Pattern>
		STREAM_CODEC = StreamCodec
						.builder(Pattern.class)
						.defaultConstructor(Pattern::new)
						.varIntEnumOrCodec(Pattern::getType, Pattern::setType, EnumPatternType.class, ResourcePatternType.STREAM_CODEC)
						.varIntEnum(Pattern::getColor, Pattern::setColor, DyeColor.class)
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
			return NBT_CODEC;
		}
		
		@Override
		public StreamCodec<? extends Pattern> getStreamCodec() {
			return STREAM_CODEC;
		}
		
	}
	
	public static interface PatternType {
		
		public static final NBTCodec<PatternType> NBT_CODEC = NBTCodec.codecOrElse(PatternType.class, EnumPatternType.NBT_CODEC, ResourcePatternType.NBT_CODEC);
		
	}
	
	public static class ResourcePatternType implements PatternType, NBTSerializable, StreamSerializable {
		
		public static final NBTCodec<ResourcePatternType>
		NBT_CODEC = NBTCodec
						.builder(ResourcePatternType.class)
						.defaultConstructor(ResourcePatternType::new)
						.codec("asset_id", ResourcePatternType::getAssetID, ResourcePatternType::setAssetID, NamespacedKey.NBT_CODEC)
						.codec("translation_key", ResourcePatternType::getTranslationKey, ResourcePatternType::setTranslationKey, NBTCodecs.STRING)
						.build();
		
		public static final StreamCodec<ResourcePatternType>
		STREAM_CODEC = StreamCodec
						.builder(ResourcePatternType.class)
						.defaultConstructor(ResourcePatternType::new)
						.namespacedKey(ResourcePatternType::getAssetID, ResourcePatternType::setAssetID)
						.stringValue(ResourcePatternType::getTranslationKey, ResourcePatternType::setTranslationKey)
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
			return NBT_CODEC;
		}
		
		@Override
		public StreamCodec<? extends ResourcePatternType> getStreamCodec() {
			return STREAM_CODEC;
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
		
		public static final NBTCodec<EnumPatternType> NBT_CODEC = EnumUtil.enumStringNBTCodec(EnumPatternType.class);

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
