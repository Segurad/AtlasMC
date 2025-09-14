package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Lightable;

class LitProperty extends AbstractBooleanProperty {

	public LitProperty() {
		super("lit");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Lightable lightable)
			lightable.setLit(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Lightable lightable)
			return lightable.isLit();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Lightable;
	}

}
