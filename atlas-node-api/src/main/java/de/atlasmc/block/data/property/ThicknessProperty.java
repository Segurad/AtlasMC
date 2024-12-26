package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.PointedDripstone;
import de.atlasmc.block.data.type.PointedDripstone.Thickness;

class ThicknessProperty extends AbstractEnumProperty<Thickness> {

	public ThicknessProperty() {
		super("thickness", Thickness.class);
	}

	@Override
	public void set(BlockData data, Thickness value) {
		if (data instanceof PointedDripstone dripstone)
			dripstone.setThickness(value);
	}

	@Override
	public Thickness get(BlockData data) {
		if (data instanceof PointedDripstone dripstone)
			return dripstone.getThickness();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof PointedDripstone;
	}

}
