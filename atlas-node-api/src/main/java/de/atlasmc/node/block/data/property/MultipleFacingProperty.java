package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.MultipleFacing;
import de.atlasmc.node.block.data.type.Wall;

class MultipleFacingProperty extends AbstractBooleanProperty {

	private BlockFace face;
	
	public MultipleFacingProperty(BlockFace face) {
		super(face.name());
		this.face = face;
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof MultipleFacing facing) {
			facing.setFace(face, value);
		} else if (face == BlockFace.UP && data instanceof Wall wall) {
			wall.setUp(value);
		}
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof MultipleFacing facing) {
			return facing.hasFace(face);
		} else if (face == BlockFace.UP && data instanceof Wall wall) {
			return wall.isUp();
		}
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof MultipleFacing || (face == BlockFace.UP && data instanceof Wall);
	}

}
