package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.HangingMoss;

class TipProperty extends AbstractBooleanProperty {

	public TipProperty() {
		super("tip");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof HangingMoss moss)
			moss.setTip(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof HangingMoss moss)
			return moss.isTip();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof HangingMoss;
	}

}
