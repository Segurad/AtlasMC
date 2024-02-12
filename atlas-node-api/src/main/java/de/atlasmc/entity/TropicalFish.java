package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.DyeColor;

public interface TropicalFish extends Fish {
	
	public Pattern getPattern();
	
	public void setPattern(Pattern pattern);
	
	public DyeColor getBaseColor();
	
	public void setBaseColor(DyeColor color);
	
	public DyeColor getPatternColor();
	
	public void setPatternColor(DyeColor color);

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
