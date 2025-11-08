package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Snow;

class LayersProperty extends AbstractIntProperty {

	public LayersProperty() {
		super("layers");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Snow snow)
			snow.setLayers(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Snow snow)
			return snow.getLayers();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Snow;
	}

}
