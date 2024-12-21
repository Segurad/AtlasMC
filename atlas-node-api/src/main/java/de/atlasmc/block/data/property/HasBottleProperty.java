package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.BrewingStand;

class HasBottleProperty extends AbstractBooleanProperty {

	private int slot;
	
	public HasBottleProperty(String key, int slot) {
		super(key);
		this.slot = slot;
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof BrewingStand stand)
			stand.setBottle(slot, value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof BrewingStand stand)
			return stand.hasBottle(slot);
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof BrewingStand;
	}

}
