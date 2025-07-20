package de.atlascore.entity;

import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Hanging;

public class CoreHanging extends CoreEntity implements Hanging {
	
	private BlockFace face;
	
	public CoreHanging(EntityType type) {
		super(type);
		face = BlockFace.SOUTH;
	}

	@Override
	public BlockFace getAttachedFace() {
		return face;
	}

	@Override
	public void setFacingDirection(BlockFace face) {
		if (face == null)
			throw new IllegalArgumentException("Face can not be null!");
		if (face.ordinal() > 5)
			throw new IllegalArgumentException("Face not compatible with Painting: " + face.name());
		if (this.face == face)
			return;
		this.face = face;
		loc.set(face.getYaw());
		loc.set(face.getPitch());
	}

}
