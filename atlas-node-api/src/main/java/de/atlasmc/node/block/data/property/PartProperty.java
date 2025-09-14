package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Bed;
import de.atlasmc.node.block.data.type.Bed.Part;

class PartProperty extends AbstractEnumProperty<Part> {

	public PartProperty() {
		super("part", Part.class);
	}

	@Override
	public void set(BlockData data, Part value) {
		if (data instanceof Bed bed)
			bed.setPart(value);
	}

	@Override
	public Part get(BlockData data) {
		if (data instanceof Bed bed)
			return bed.getPart();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bed;
	}

}
