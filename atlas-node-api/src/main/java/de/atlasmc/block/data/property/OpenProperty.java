package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Openable;

class OpenProperty extends AbstractBooleanProperty {

	public OpenProperty() {
		super("open");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Openable openable)
			openable.setOpen(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Openable openable)
			return openable.isOpen();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Openable;
	}

}
