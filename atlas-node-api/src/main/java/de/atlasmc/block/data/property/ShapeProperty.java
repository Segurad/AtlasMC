package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Rail;
import de.atlasmc.block.data.type.Stairs;

class ShapeProperty extends AbstractMultiEnumProperty {

	public ShapeProperty() {
		super("shape", 
				de.atlasmc.block.data.Rail.Shape.class, 
				de.atlasmc.block.data.type.Stairs.Shape.class);
	}

	@Override
	public void set(BlockData data, Enum<?> value) {
		if (data instanceof Rail rail) {
			rail.setShape((de.atlasmc.block.data.Rail.Shape) value);
		} else if (data instanceof Stairs stairs) {
			stairs.setShape((de.atlasmc.block.data.type.Stairs.Shape) value);
		}
	}

	@Override
	public Enum<?> get(BlockData data) {
		if (data instanceof Rail rail) {
			return rail.getShape();
		} else if (data instanceof Stairs stairs) {
			return stairs.getShape();
		}
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Rail || data instanceof Stairs;
	}

}
