package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Snow;

class LayersProperty extends AbstractIntProperty {

	public LayersProperty() {
		super("layers");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Snow snow)
			snow.setLayers(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Snow snow)
			return snow.getLayers();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Snow;
	}

}
