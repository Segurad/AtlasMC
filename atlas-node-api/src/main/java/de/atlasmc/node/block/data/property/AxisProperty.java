package de.atlasmc.node.block.data.property;

import de.atlasmc.node.Axis;
import de.atlasmc.node.block.data.AxisOrientable;
import de.atlasmc.node.block.data.BlockData;

class AxisProperty extends AbstractEnumProperty<Axis> {

	public AxisProperty() {
		super("axis", Axis.class);
	}

	@Override
	public void set(BlockData data, Axis value) {
		if (data instanceof AxisOrientable orientable)
			orientable.setAxis(value);
	}

	@Override
	public Axis get(BlockData data) {
		if (data instanceof AxisOrientable orientable)
			return orientable.getAxis();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof AxisOrientable;
	}

}
