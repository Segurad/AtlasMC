package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Scaffolding;

class BottomProperty extends AbstractBooleanProperty {

	public BottomProperty() {
		super("bottom");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Scaffolding scaffolding)
			scaffolding.setBottom(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Scaffolding scaffolding)
			return scaffolding.isBottom();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Scaffolding;
	}

}
