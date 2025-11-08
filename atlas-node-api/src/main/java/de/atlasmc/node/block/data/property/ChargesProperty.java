package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.RespawnAnchor;

class ChargesProperty extends AbstractIntProperty {

	public ChargesProperty() {
		super("charges");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof RespawnAnchor anchor)
			anchor.setCharges(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof RespawnAnchor anchor)
			return anchor.getCharges();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof RespawnAnchor;
	}

}
