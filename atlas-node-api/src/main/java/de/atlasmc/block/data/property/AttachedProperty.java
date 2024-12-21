package de.atlasmc.block.data.property;

import de.atlasmc.block.data.Attachable;
import de.atlasmc.block.data.BlockData;

class AttachedProperty extends AbstractBooleanProperty {

	public AttachedProperty() {
		super("attached");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Attachable attachable)
			attachable.setAttached(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Attachable attachable)
			return attachable.isAttached();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Attachable;
	}

}
