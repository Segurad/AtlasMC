package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.EndPortalFrame;

class EyeProperty extends AbstractBooleanProperty {

	public EyeProperty() {
		super("eye");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof EndPortalFrame frame)
			frame.setEye(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof EndPortalFrame frame)
			return frame.hasEye();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof EndPortalFrame;
	}

}
