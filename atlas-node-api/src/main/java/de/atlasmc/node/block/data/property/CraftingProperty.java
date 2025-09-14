package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Crafter;

class CraftingProperty extends AbstractBooleanProperty {

	public CraftingProperty() {
		super("crafting");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Crafter crafter)
			crafter.setCrafting(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Crafter crafter)
			return crafter.isCrafting();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Crafter;
	}

}
