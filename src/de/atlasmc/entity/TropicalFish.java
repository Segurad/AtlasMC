package de.atlasmc.entity;

public interface TropicalFish extends Fish {
	
	public Pattern getPattern();

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
		

		private int variant;
		private boolean large;
		
		private Pattern(int variant, boolean large) {
			this.variant = variant;
			this.large = large;
		}
		
		public int getDataID() {
			return large?0:1 + variant<<8;
		}
		
		public static Pattern getByDataID(int id) {
			int index = (id & 0xFF) + (id & 0xFF00) >> 8;
			if (index < 0 && index > 12) throw new IllegalArgumentException("Invalid DataID: " + id);
			Pattern[] values = values();
			return values[index];
		}
		
	}
}
