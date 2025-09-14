package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.PointedDripstone;
import de.atlasmc.node.block.data.type.PointedDripstone.VerticalDirection;

class VerticalDirectionProperty extends AbstractEnumProperty<VerticalDirection> {

	public VerticalDirectionProperty() {
		super("vertical_direction", VerticalDirection.class);
	}

	@Override
	public void set(BlockData data, VerticalDirection value) {
		if (data instanceof PointedDripstone dripstone)
			dripstone.setDirection(value);
	}

	@Override
	public VerticalDirection get(BlockData data) {
		if (data instanceof PointedDripstone dripstone)
			return dripstone.getDirection();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof PointedDripstone;
	}

}
