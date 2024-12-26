package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Bamboo;
import de.atlasmc.block.data.type.Bamboo.Leaves;

public class LeavesProperty extends AbstractEnumProperty<Leaves> {

	public LeavesProperty() {
		super("leaves", Leaves.class);
	}

	@Override
	public void set(BlockData data, Leaves value) {
		if (data instanceof Bamboo bamboo)
			bamboo.setLeaves(value);
	}

	@Override
	public Leaves get(BlockData data) {
		if (data instanceof Bamboo bamboo)
			return bamboo.getLeaves();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bamboo;
	}

}
