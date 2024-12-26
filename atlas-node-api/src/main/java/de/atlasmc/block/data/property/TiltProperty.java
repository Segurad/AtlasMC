package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.BigDripleaf;
import de.atlasmc.block.data.type.BigDripleaf.Tilt;

class TiltProperty extends AbstractEnumProperty<Tilt> {

	public TiltProperty() {
		super("tilt", Tilt.class);
	}

	@Override
	public void set(BlockData data, Tilt value) {
		if (data instanceof BigDripleaf leaf)
			leaf.setTilt(value);
	}

	@Override
	public Tilt get(BlockData data) {
		if (data instanceof BigDripleaf leaf)
			return leaf.getTilt();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof BigDripleaf;
	}

}
