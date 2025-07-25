package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TropicalFish extends Fish {
	
	public static final NBTSerializationHandler<TropicalFish>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TropicalFish.class)
					.include(Fish.NBT_HANDLER)
					.intField("Variant", TropicalFish::getVariantID, TropicalFish::setVariantID, 0)
					.build();
	
	Pattern getPattern();
	
	void setPattern(Pattern pattern);
	
	DyeColor getBaseColor();
	
	void setBaseColor(DyeColor color);
	
	DyeColor getPatternColor();
	
	void setPatternColor(DyeColor color);

	int getVariantID();
	
	void setVariantID(int id);
	
	@Override
	default NBTSerializationHandler<? extends TropicalFish> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Pattern {
		KOB(0, false),
		FLOPPER(0, true),
		SUNSTREAK(1, false),
		STRIPEY(1, true),
		SNOPPER(2, false),
		GLITTER(2, true),
		DASHER(3, false),
		BLOCKFISH(3, true),
		BRINELY(4, false),
		BETTY(4, true),
		SPOTTY(5, false),
		CLAYFISH(5, true);
		
		private static List<Pattern> VALUES;

		private int variant;
		private boolean large;
		
		private Pattern(int variant, boolean large) {
			this.variant = variant;
			this.large = large;
		}
		
		public int getDataID() {
			return (large?0:1) | variant<<8;
		}
		
		public static Pattern getByDataID(int id) {
			int index = (id & 0xFF) | (id & 0xFF00) >> 7;
			if (index < 0 && index > 12) throw new IllegalArgumentException("Invalid DataID: " + id);
			return getValues().get(index);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Pattern> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
	}
}
