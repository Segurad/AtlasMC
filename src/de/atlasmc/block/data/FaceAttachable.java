package de.atlasmc.block.data;

public interface FaceAttachable extends BlockData {

	public AttachedFace getAttachedFace();
	public void setAttachedFace(AttachedFace face);
	
	public static enum AttachedFace {
		FLOOR,
		WALL,
		CEILING;

		/**
		 * Returns the AttachedFace represented by the name or {@link #WALL} if no matching AttachedFace has been found
		 * @param name the name of the AttachedFace
		 * @return the AttachedFace or {@link #WALL}
		 */
		public static AttachedFace getByName(String name) {
			name = name.toUpperCase();
			for (AttachedFace s : values()) {
				if (s.name().equals(name)) return s;
			}
			return WALL;
		}
	}
}
