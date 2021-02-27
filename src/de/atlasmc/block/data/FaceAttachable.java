package de.atlasmc.block.data;

public interface FaceAttachable extends BlockData {

	public AttachedFace getAttachedFace();
	public void setAttachedFace(AttachedFace face);
	
	public static enum AttachedFace {
		FLOOR,
		WALL,
		CEILING
	}
}
