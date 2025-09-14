package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Directional;

class FacingProperty extends AbstractEnumProperty<BlockFace> {

	public FacingProperty() {
		super("facing", BlockFace.class);
	}

	@Override
	public void set(BlockData data, BlockFace value) {
		if (data instanceof Directional directional)
			directional.setFacing(value);
	}

	@Override
	public BlockFace get(BlockData data) {
		if (data instanceof Directional directional)
			directional.getFacing();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Directional;
	}

}
