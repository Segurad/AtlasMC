package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Waterlogged;

class WaterloggedProperty extends AbstractBooleanProperty {

	public WaterloggedProperty() {
		super("waterlogged");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Waterlogged waterlogged)
			waterlogged.setWaterlogged(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Waterlogged waterlogged)
			return waterlogged.isWaterlogged();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Waterlogged;
	}

}
