package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.CommandBlock;

class ConditionalProperty extends AbstractBooleanProperty {

	public ConditionalProperty() {
		super("conditional");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof CommandBlock cmdBlock)
			cmdBlock.setConditional(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof CommandBlock cmdBlock)
			return cmdBlock.isConditional();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof CommandBlock;
	}

}
