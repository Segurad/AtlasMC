package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.RespawnAnchor;

class ChargesProperty extends AbstractIntProperty {

	public ChargesProperty() {
		super("charges");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof RespawnAnchor anchor)
			anchor.setCharges(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof RespawnAnchor anchor)
			return anchor.getCharges();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof RespawnAnchor;
	}

}
