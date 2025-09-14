package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Snowable;

class SnowyProperty extends AbstractBooleanProperty {

	public SnowyProperty() {
		super("snowy");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Snowable snowable)
			snowable.setSnowy(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Snowable snowable)
			return snowable.isSnowy();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Snowable;
	}

}
