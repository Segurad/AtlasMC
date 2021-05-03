package de.atlasmc.block.data;

public interface Bisected extends BlockData {
	
	public Half getHalf();
	public void setHalf(Half half);
	
	public static enum Half {
		TOP,
		BOTTOM;

		public static Half getByName(String name) {
			if (TOP.name().equalsIgnoreCase(name)) {
				return TOP;
			} else if (BOTTOM.name().equalsIgnoreCase(name)) {
				return BOTTOM;
			}
			return null;
		}
	}

}
