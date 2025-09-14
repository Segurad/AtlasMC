package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.PistonHead;

class ShortProperty extends AbstractBooleanProperty {

	public ShortProperty() {
		super("short");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof PistonHead head)
			head.setShort(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof PistonHead head)
			return head.isShort();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof PistonHead;
	}

}
