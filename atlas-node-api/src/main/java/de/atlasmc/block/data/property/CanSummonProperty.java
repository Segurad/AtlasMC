package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.SculkShrieker;

class CanSummonProperty extends AbstractBooleanProperty {

	public CanSummonProperty() {
		super("can_summon");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof SculkShrieker shrieker)
			shrieker.setCanSummon(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof SculkShrieker shrieker)
			return shrieker.canSummon();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof SculkShrieker;
	}

}
